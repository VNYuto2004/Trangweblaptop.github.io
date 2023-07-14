package Server;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Admin
 */
public class ServerThread implements Runnable {
    
    private Socket socketOfServer;
    private int clientNumber;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;
    private Room room;
    private String clientIP;
    
    public BufferedReader getIs() {
        return is;
    }
    
    public BufferedWriter getOs() {
        return os;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    
    public int getClientNumber() {
        return clientNumber;
    }

    public Room getRoom() {
        return room;
    }

    public String getClientIP() {
        return clientIP;
    }
    
    public ServerThread(Socket socketOfServer, int clientNumber) {
        this.socketOfServer = socketOfServer;
        this.clientNumber = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started");
        isClosed = false;
        room = null;
        //Trường hợp test máy ở server sẽ lỗi do hostaddress là localhost
        if(this.socketOfServer.getInetAddress().getHostAddress().equals("127.0.0.1")){
            clientIP = "127.0.0.1";
        }
        else{
            clientIP = this.socketOfServer.getInetAddress().getHostAddress();
        }
        
    }
    
    public void goToOwnRoom() throws IOException{
        write("go-to-room," + room.getID()+","+room.getCompetitor(this.getClientNumber()).getClientIP()+",1,");
        room.getCompetitor(this.clientNumber).write("go-to-room," + room.getID()+","+this.clientIP+",0,");
    }
    
    public void goToPartnerRoom() throws IOException{
        write("go-to-room," + room.getID()+","+room.getCompetitor(this.getClientNumber()).getClientIP()+",0,");
         room.getCompetitor(this.clientNumber).write("go-to-room,"+ room.getID()+","+this.clientIP+",1,");
    }
    
    @Override
    public void run() {
        try {
            // Mở luồng vào ra trên Socket tại Server.
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            System.out.println("Khời động luông mới thành công, ID là: " + clientNumber);
            write("server-send-id" + "," + this.clientNumber);
            Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.clientNumber+" đã đăng nhập---");
            String message;
            while (!isClosed) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                String[] messageSplit = message.split(",");         
                //Xử lý người chơi đăng xuất
                if(messageSplit[0].equals("offline")){
                    Server.serverThreadBus.boardCast(clientNumber, "chat-server,"+this.clientNumber +" đã offline");
                }
               
                //Xử lý chat toàn server
                if(messageSplit[0].equals("chat-server")){
                    Server.serverThreadBus.boardCast(clientNumber,messageSplit[0]+","+ clientNumber+" : "+ messageSplit[1]);
                }
                
                if (messageSplit[0].equals("view-room-list")) {
                    String res = "room-list,";
                    int number = 1;
                    for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                        if(number>8) break;
                        if (serverThread.room != null && serverThread.room.getNumberOfUser() == 1) {
                            res += serverThread.room.getID() + ",";
                        }
                        number++;
                    }
                    write(res);
                }
               
                //Xử lý vào phòng trong chức năng tìm kiếm phòng
                if(messageSplit[0].equals("go-to-room")){
                    int roomName = Integer.parseInt(messageSplit[1]);
                    boolean isFinded = false;
                    for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                        if(serverThread.getRoom()!=null&&serverThread.getRoom().getID()==roomName){
                            isFinded = true;
                            if(serverThread.getRoom().getNumberOfUser()==2){
                                write("room-fully,");
                            }else {
                                    this.room = serverThread.getRoom();
                                    goToPartnerRoom();
                            }
                            break;
                        }
                    }
                    if(!isFinded){
                        write("room-not-found,");
                    }
                }
                //Xử lý tạo phòng
                if (messageSplit[0].equals("create-room")) {
                    room = new Room(this);
                    write("your-created-room," + room.getID());
                    System.out.println("Tạo phòng mới thành công");   
                }
                //Xử lý xem danh sách phòng trống
                if (messageSplit[0].equals("view-room-list")) {
                    String res = "room-list,";
                    int number = 1;
                    for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                        if(number>8) break;
                        if (serverThread.room != null && serverThread.room.getNumberOfUser() == 1) {
                            res += serverThread.room.getID() + "," ;
                        }
                        number++;
                    }
                    write(res);
                }
         
                //Xử lý tìm phòng nhanh
                if (messageSplit[0].equals("quick-room")) {
                    boolean isFinded = false;
                    for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                        if (serverThread.room != null && serverThread.room.getNumberOfUser() == 1 ) {
                            serverThread.room.setUser2(this);
                            this.room = serverThread.room;
                            System.out.println("Đã vào phòng " + room.getID());
                            goToPartnerRoom();
                            isFinded = true;
                            //Xử lý phần mời cả 2 người chơi vào phòng
                            break;
                        }
                    }
                    
                    if (!isFinded) {
                        this.room = new Room(this);
                        System.out.println("Không tìm thấy phòng, tạo phòng mới");
                    }
                }
                //Xử lý không tìm được phòng
                if (messageSplit[0].equals("cancel-room")) {
                    System.out.println("Đã hủy phòng");
                    this.room = null;
                }
                //Xử lý khi có người chơi thứ 2 vào phòng
                if (messageSplit[0].equals("join-room")) {
                    int ID_room = Integer.parseInt(messageSplit[1]);
                    for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                        if (serverThread.room != null && serverThread.room.getID() == ID_room) {
                            serverThread.room.setUser2(this);
                            this.room = serverThread.room;
                            System.out.println("Đã vào phòng " + room.getID());
                            goToPartnerRoom();
                            break;
                        }
                    }
                }
                //Xử lý khi đối thủ đồng ý thách đấu
                if(messageSplit[0].equals("agree-duel")){
                    this.room = new Room(this);
                    int ID_User2 = Integer.parseInt(messageSplit[1]);
                    ServerThread user2 = Server.serverThreadBus.getServerThreadByUserID(ID_User2);
                    room.setUser2(user2);
                    user2.setRoom(room);
                    goToOwnRoom();
                }

                //Xử lý khi người chơi đánh 1 nước
                if(messageSplit[0].equals("caro")){
                    room.getCompetitor(clientNumber).write(message);
                }
                if(messageSplit[0].equals("chat")){
                    room.getCompetitor(clientNumber).write(message);
                }
                if(messageSplit[0].equals("win")){
                    room.getCompetitor(clientNumber).write("caro,"+messageSplit[1]+","+messageSplit[2]);
                    room.boardCast("new-game,");
                }
                if(messageSplit[0].equals("lose")){
                    room.getCompetitor(clientNumber).write("competitor-time-out");
                    write("new-game,");
                }
                if(messageSplit[0].equals("draw-request")){
                    room.getCompetitor(clientNumber).write(message);
                }
                if(messageSplit[0].equals("draw-confirm")){
                    room.boardCast("draw-game,");
                }
                if(messageSplit[0].equals("draw-refuse")){
                    room.getCompetitor(clientNumber).write("draw-refuse,");
                }
                if(messageSplit[0].equals("left-room")){
                    if (room != null) {
                        room.getCompetitor(clientNumber).write("left-room,");
                        room.getCompetitor(clientNumber).room = null;
                        this.room = null;
                    }
                }
            }
        } catch (IOException e) {
            //Thay đổi giá trị cờ để thoát luồng
            isClosed = true;
            //Cập nhật trạng thái của user
            Server.serverThreadBus.boardCast(clientNumber, "chat-server,"+ this.clientNumber +" đã offline");
            
            //remove thread khỏi bus
            Server.serverThreadBus.remove(clientNumber);
            System.out.println(this.clientNumber + " đã thoát");
            if (room != null) {
                try {
                    if (room.getCompetitor(clientNumber) != null) {
                        room.getCompetitor(clientNumber).write("left-room,");
                        room.getCompetitor(clientNumber).room = null;
                    }
                    this.room = null;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    public void write(String message) throws IOException {
        os.write(message);
        os.newLine();
        os.flush();
    }
}
