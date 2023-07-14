package View;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import Client.Client;
import javax.swing.JLabel;
import java.awt.Font;

public class MainFrm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	JTextArea textArea = new JTextArea();
	JButton btn_Gui_4 = new JButton("DS phòng");
	JButton btn_Gui_2 = new JButton("Vào phòng");
	JButton btn_Gui_1 = new JButton("Chơi nhanh");

	/**
	 * Launch the application.
	 */
	public MainFrm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setResizable(false);
		URL url = Carogame_view.class.getResource("caro_game.png");
		Image logo = Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage(logo);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10, 29, 571, 205);
		contentPane.add(scroll);
		scroll.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setBounds(10, 261, 448, 32);
		textField.requestFocus();
		contentPane.add(textField);
		textField.setColumns(10);
		textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
            	if (evt.getKeyCode() == 10) {
                    try {
                    	if (textField.getText().isEmpty()) {
        	                throw new Exception("Vui lòng nhập nội dung tin nhắn");
        	            }
        	            String temp = textArea.getText();
        	            temp += "Tôi: " + textField.getText() + "\n";
        	            textArea.setText(temp);
        	            Client.socketHandle.write("chat-server," + textField.getText());
        	            textField.setText("");
        	            textField.requestFocus();
        	            textArea.setCaretPosition(textArea.getDocument().getLength());
        	        } catch (IOException ex) {
        	            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        	        } catch (Exception ex) {
        	            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        	        }
                }
            }
        });
		
		JButton btn_Gui = new JButton("Gửi");
		btn_Gui.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_Gui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		btn_Gui.setBounds(496, 260, 85, 32);
		contentPane.add(btn_Gui);
		
		
		btn_Gui_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_Gui_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.openView(Client.View.FINDROOM);
			}
		});
		btn_Gui_1.setBounds(27, 339, 95, 32);
		contentPane.add(btn_Gui_1);
		
		
		btn_Gui_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		btn_Gui_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.openView(Client.View.ROOMNAME);
			}
		});
		btn_Gui_2.setBounds(167, 339, 95, 32);
		contentPane.add(btn_Gui_2);
		
		JButton btn_Gui_3 = new JButton("Tạo phòng");
		btn_Gui_3.setFont(new Font("Tahoma", Font.BOLD, 10));
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
		
		
		btn_Gui_4.setFont(new Font("Tahoma", Font.BOLD, 10));
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
		
		JLabel lblNewLabel = new JLabel("Global chat");
		lblNewLabel.setBounds(10, 10, 74, 19);
		contentPane.add(lblNewLabel);
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
	            textField.requestFocus();
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
	 public void ennableButton(boolean b) {
		 btn_Gui_1.setEnabled(b);
		 btn_Gui_2.setEnabled(b);
		 btn_Gui_4.setEnabled(b);
	 }
}
