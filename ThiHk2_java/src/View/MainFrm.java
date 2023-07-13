package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import Client.Client;

public class MainFrm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	JTextArea textArea = new JTextArea();

	/**
	 * Launch the application.
	 */
	public MainFrm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea.setBounds(10, 10, 571, 224);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(10, 261, 448, 32);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btn_Gui = new JButton("Gửi");
		btn_Gui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		btn_Gui.setBounds(496, 260, 85, 32);
		contentPane.add(btn_Gui);
		
		JButton btn_Gui_1 = new JButton("Chơi nhanh");
		btn_Gui_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.openView(Client.View.FINDROOM);
			}
		});
		btn_Gui_1.setBounds(27, 339, 95, 32);
		contentPane.add(btn_Gui_1);
		
		JButton btn_Gui_2 = new JButton("Vào phòng");
		btn_Gui_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.openView(Client.View.ROOMNAME);
			}
		});
		btn_Gui_2.setBounds(167, 339, 95, 32);
		contentPane.add(btn_Gui_2);
		
		JButton btn_Gui_3 = new JButton("Tạo phòng");
		btn_Gui_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
			            Client.socketHandle.write("create-room,");
			        } catch (IOException ex) {
			            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
			        }
			}
		});
		btn_Gui_3.setBounds(313, 339, 95, 32);
		contentPane.add(btn_Gui_3);
		
		JButton btn_Gui_4 = new JButton("Tìm phòng");
		btn_Gui_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
			            Client.openView(Client.View.ROOMLIST);
			            Client.socketHandle.write("view-room-list,");
			        } catch (IOException ex) {
			            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
			        }
			}
		});
		btn_Gui_4.setBounds(455, 339, 95, 32);
		contentPane.add(btn_Gui_4);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 313, 571, 2);
		contentPane.add(separator);
	}
	 private void sendMessage(){
	        try {
	            if (textField.getText().isEmpty()) {
	                throw new Exception("Vui lòng nhập nội dung tin nhắn");
	            }
	            String temp = textArea.getText();
	            temp += "Tôi: " + textField.getText() + "\n";
	            textArea.setText(temp);
	            Client.socketHandle.write("chat-server," + textField.getText());
	            textField.setText("");
	            textArea.setCaretPosition(textArea.getDocument().getLength());
	        } catch (IOException ex) {
	            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
	        }
	    }
	 
	 public void addMessage(String message){
	        String temp = textArea.getText();
	        temp+=message+"\n";
	        textArea.setText(temp);
	        textArea.setCaretPosition(textArea.getDocument().getLength());
	    }
}
