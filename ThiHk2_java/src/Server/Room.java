package Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room {
    private int ID;
    private ServerThread user1;
    private ServerThread user2;

    public int getID() {
        return ID;
    }

    public ServerThread getUser2() {
        return user2;
    }

    public ServerThread getUser1() {
        return user1;
    }

    
    public Room(ServerThread user1) {
        System.out.println("Tạo phòng thành công, ID là: "+Server.ID_room);
        this.ID = Server.ID_room++;
        this.user1 = user1;
        this.user2 = null;
    }
    
    public int getNumberOfUser(){
        return user2==null?1:2;
    }
    
    public void setUser2(ServerThread user2){
        this.user2 = user2;
    }
    
    public void boardCast(String message){
        try {
            user1.write(message);
            user2.write(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public int getCompetitorID(int ID_ClientNumber){
        if(user1.getClientNumber()==ID_ClientNumber)
            return user2.getClientNumber();
        return user1.getClientNumber();
    }
    public ServerThread getCompetitor(int ID_ClientNumber){
        if(user1.getClientNumber()==ID_ClientNumber)
            return user2;
        return user1;
    }
    
}