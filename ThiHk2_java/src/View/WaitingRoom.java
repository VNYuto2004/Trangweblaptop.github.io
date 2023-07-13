package View;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Client.Client;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class WaitingRoom extends JFrame {

	private JPanel contentPane;
	private boolean isOpenning;
	private JLabel lbl_NameRoom = new JLabel();
	private JLabel lbl_TrangThai = new JLabel("Đang tìm đối thủ");
	private JButton btnNewButton = new JButton("Thoát");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterRoom frame = new EnterRoom();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public WaitingRoom() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 296);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		isOpenning = false;

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbl_NameRoom.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_NameRoom.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_NameRoom.setBounds(10, 10, 417, 52);
		contentPane.add(lbl_NameRoom);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(151, 72, 136, 91);
		lblNewLabel.setIcon(new ImageIcon("D:/Gif/load.gif"));
		contentPane.add(lblNewLabel);
		
		lbl_TrangThai.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lbl_TrangThai.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_TrangThai.setBounds(10, 173, 417, 30);
		contentPane.add(lbl_TrangThai);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isOpenning) return;
		        try {
		            Client.closeView(Client.View.WAITINGROOM);
		            Client.openView(Client.View.HOMEPAGE);
		            Client.socketHandle.write("cancel-room,");
		        } catch (IOException ex) {
		            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
		        }
			}
		});
		
		btnNewButton.setBounds(173, 228, 85, 21);
		contentPane.add(btnNewButton);
	}
	public void setRoomName(String roomName){
		lbl_NameRoom.setText("Phòng "+roomName);
    }
	public void showFindedCompetitor(){
	        isOpenning = true;
	        lbl_TrangThai.setText("Đã tìm thấy đối thủ, đang vào phòng");
	        lbl_TrangThai.setForeground(Color.BLUE);
	        btnNewButton.setVisible(false);
	}
}
