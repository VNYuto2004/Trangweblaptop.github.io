package View;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import Client.Client;

public class FindRoom extends JFrame {

	private JPanel contentPane;
	private Timer timer;
    private boolean isFinded;
    JLabel lbl_Time = new JLabel();
    JLabel lbl_ThongTin = new JLabel("Đang tìm đối thủ");

	/**
	 * Launch the application.
	 */
	
	public FindRoom() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 494, 321);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setResizable(false);
		URL url = Carogame_view.class.getResource("caro_game.png");
		Image logo = Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage(logo);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tìm phòng nhanh");
		lblNewLabel.setBackground(new Color(192, 192, 192));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 10, 460, 56);
		contentPane.add(lblNewLabel);
		
		lbl_Time.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Time.setBounds(216, 76, 51, 19);
		contentPane.add(lbl_Time);
		lbl_Time.setText("00:20");
		
		JLabel lblNewLabel_1 = new JLabel(new ImageIcon("D:/Gif/load.gif"));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(174, 105, 136, 91);
		contentPane.add(lblNewLabel_1);
		
		JButton btn_FRThoat = new JButton("Thoát");
		btn_FRThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isFinded)
		            return;
		        try {
		            Client.socketHandle.write("cancel-room,");
		        } catch (IOException ex) {
		            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
		        }
		        timer.stop();
		        Client.closeView(Client.View.FINDROOM);
		        //Client.openView(Client.View.HOMEPAGE);
			}
		});
		btn_FRThoat.setBounds(199, 238, 85, 21);
		contentPane.add(btn_FRThoat);
		
		lbl_ThongTin.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lbl_ThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ThongTin.setBounds(10, 206, 460, 22);
		contentPane.add(lbl_ThongTin);
		startFind();
		sendFindRequest();
	}
	public void stopAllThread(){
        timer.stop();
    }
	public void startFind(){
        timer = new Timer(1000, new ActionListener() {
            int count = 20;

            @Override
            public void actionPerformed(ActionEvent e) {
                count--;
                if (count >= 0) {
                    if(count>=10) {
                    	lbl_Time.setText("00:" + count);
                    }else {
                    	lbl_Time.setText("00:0" + count);
                    }
                } else {
                    ((Timer) (e.getSource())).stop();
                    try {
                        Client.socketHandle.write("cancel-room,");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    }
                    int res = JOptionPane.showConfirmDialog(rootPane, "Tìm kiếm thất bại, bạn muốn thử lại lần nữa chứ?");
                    if (res==JOptionPane.YES_OPTION){
                        startFind();
                        sendFindRequest();
                    }
                    else{
                        Client.closeView(Client.View.FINDROOM);
                        //Client.openView(Client.View.HOMEPAGE);
                    }
                }
            }

        });
        timer.setInitialDelay(0);
        timer.start();
    }
	public void sendFindRequest(){
        try {
            Client.socketHandle.write("quick-room,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }
	public void showFindedRoom(){
        isFinded = true;
        timer.stop();
        lbl_ThongTin.setText("Đã tìm thấy đối thủ, đang vào phòng");
        lbl_ThongTin.setForeground(Color.BLUE);
    }
}
