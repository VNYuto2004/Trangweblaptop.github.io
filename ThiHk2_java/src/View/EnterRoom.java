package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import Client.Client;

public class EnterRoom extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	
	public EnterRoom() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 327, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setResizable(false);
		URL url = Carogame_view.class.getResource("caro_game.png");
		Image logo = Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage(logo);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Vào phòng");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 10, 300, 31);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(136, 63, 134, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String roomName = textField.getText();
		        if(roomName.equals("")){
		            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập mã phòng");
		            textField.requestFocus();
		            return;
		        }
		        try {
		            Client.socketHandle.write("go-to-room,"+roomName+",");
		        } catch (IOException ex) {
		            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
		        }
            }
        });
		
		JButton btnNewButton = new JButton("Vào phòng");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String roomName = textField.getText();
		        if(roomName.equals("")){
		            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập mã phòng");
		            textField.requestFocus();
		            return;
		        }
		        try {
		            Client.socketHandle.write("go-to-room,"+roomName+",");
		        } catch (IOException ex) {
		            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
		        }


			}
		});
		btnNewButton.setBounds(105, 130, 99, 21);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Nhập mã phòng : ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(27, 67, 99, 13);
		contentPane.add(lblNewLabel_1);
	}
}
