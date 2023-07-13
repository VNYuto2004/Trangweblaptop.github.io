package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JScrollPane;
import Client.Client;
public class RoomList extends JFrame {

	private JPanel contentPane;
	private Vector<String> listRoom;
	private Thread thread;
	private boolean isPlayThread;
	private boolean isFiltered;
	DefaultTableModel defaultTableModel;
	private JTable jTable1 = new JTable();

	/**
	 * Launch the application.
	 */
	public RoomList() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Danh sách phòng");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 416, 34);
		contentPane.add(lblNewLabel);
		
		Object[][] rows = {
        };
        String[] columns = {"Tên phòng"};
        DefaultTableModel model = new DefaultTableModel(rows, columns){
            @Override
            public Class<?> getColumnClass(int column){
                switch(column){
                    case 0: return String.class;
                    default: return Object.class;
                }
            }
        };
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.setModel(model);
        jTable1.setRowHeight(50);
        jTable1.setFont(new java.awt.Font("Tekton Pro", 1, 30)); 
        
        JScrollPane scrollPane = new JScrollPane(jTable1);
        scrollPane.setBounds(10, 54, 416, 450);
        contentPane.add(scrollPane);
        this.setResizable(false);
        
		defaultTableModel = (DefaultTableModel) jTable1.getModel();
        isPlayThread = true;
        isFiltered = false;
        
        thread = new Thread(){
            @Override
            public void run(){
                while (isPlayThread&&!isFiltered) {                    
                    try {
                        Client.socketHandle.write("view-room-list,");
                        Thread.sleep(1000);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    } catch (InterruptedException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    }
                }
            }
        };
        thread.start();
        
	}
	public void updateRoomList(Vector<String> listData){
        this.listRoom = listData;
        defaultTableModel.setRowCount(0);
        for(int i=0; i<listRoom.size(); i++){
            defaultTableModel.addRow(new Object[]{
                listRoom.get(i)
            });
        }
    }
	private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if(jTable1.getSelectedRow()!=-1){      
        	try {
        		int index = jTable1.getSelectedRow();
        		int room = Integer.parseInt(listRoom.get(index).split(" ")[1]);
        		Client.socketHandle.write("join-room,"+room);
        		isPlayThread = false;
        		Client.closeView(Client.View.ROOMLIST);
        	} catch (IOException ex) {
        		JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        	}
        }
    }
	public void stopThread() {
		thread.stop();
		System.out.println("đã đóng luồng");
	}
}
