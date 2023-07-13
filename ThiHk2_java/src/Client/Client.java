package Client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.UIManager;

import View.Carogame_view;
import View.EnterRoom;
import View.FindRoom;
import View.MainFrm;
import View.RoomList;
import View.WaitingRoom;

public class Client {
	public enum View{
		HOMEPAGE,
		ROOMLIST,
		FINDROOM,
		WAITINGROOM,
		GAMECLIENT,
		ROOMNAME
	}
	public static MainFrm homePageFrm;
	public static RoomList roomListFrm;
	public static FindRoom findRoomFrm;
	public static WaitingRoom waitingRoomFrm;
	public static Carogame_view gameClientFrm;
	public static EnterRoom roomNameFrm;
	
	public static SocketHandle socketHandle;
	
	public Client() {
	}
	public static JFrame getVisibleJFrame(){
        if(roomListFrm!=null&&roomListFrm.isVisible()) {
        	return roomListFrm;
        }
        return homePageFrm;
    }
	public void initView(){
		homePageFrm = new MainFrm();
        homePageFrm.setVisible(true);
        socketHandle = new SocketHandle();
        socketHandle.run();
    }
	public static void openView(View viewName){
        if(viewName != null){
            switch(viewName){              
                case HOMEPAGE:
                    homePageFrm = new MainFrm();
                    homePageFrm.setVisible(true);
                    break;
                case ROOMLIST:
                    roomListFrm = new RoomList();
                    roomListFrm.setVisible(true);
                    break;
                case FINDROOM:
                    findRoomFrm = new FindRoom();
                    findRoomFrm.setVisible(true);
                    break;
                case WAITINGROOM:
                    waitingRoomFrm = new WaitingRoom();
                    waitingRoomFrm.setVisible(true);
                    break;
                case ROOMNAME:
                    roomNameFrm = new EnterRoom();
                    roomNameFrm.setVisible(true);
                    break;
            }
        }
    }
	public static void openView(View viewName, int room_ID, int isStart, String competitorIP){
        if(viewName != null){
            switch(viewName){
                case GAMECLIENT:
                    gameClientFrm = new Carogame_view( room_ID, isStart, competitorIP);
                    gameClientFrm.setVisible(true);
                    break;
            }
        }
    }
	public static void closeView(View viewName){
        if(viewName != null){
            switch(viewName){
                case HOMEPAGE:
                    homePageFrm.dispose();
                    break;
                case ROOMLIST:
                    roomListFrm.dispose();
                    roomListFrm.stopThread();
                    break;
                case FINDROOM:
                    findRoomFrm.stopAllThread();
                    findRoomFrm.dispose();
                    break;
                case WAITINGROOM:
                    waitingRoomFrm.dispose();
                    break;
                case GAMECLIENT:
                    gameClientFrm.stopAllThread();
                    gameClientFrm.dispose();
                    break;
                case ROOMNAME:
                    roomNameFrm.dispose();
                    break;
            }
            
        }
    }
	public static void closeAllViews(){
        if(homePageFrm!=null) homePageFrm.dispose();
        if(roomListFrm!=null) {
        	roomListFrm.dispose();
        	roomListFrm.stopThread();
        }
        if(findRoomFrm!=null){
            findRoomFrm.stopAllThread();
            findRoomFrm.dispose();
        } 
        if(waitingRoomFrm!=null) waitingRoomFrm.dispose();
        if(gameClientFrm!=null){
            gameClientFrm.stopAllThread();
            gameClientFrm.dispose();
        } 
        if(roomNameFrm!=null) roomNameFrm.dispose();
    }
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Client().initView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
