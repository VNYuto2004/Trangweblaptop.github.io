package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import Client.Client;
import Client.SocketHandle;

import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import javax.swing.JSeparator;

public class Carogame_view extends JFrame {

	private JPanel contentPane;
    JPanel panelchinh = new JPanel();
	public int n = 17, m = 17, num = 0, diem = 0;//khởi tạo các giá trị : kích thước của từng button, điểm, số 
    public JButton btn[][] = new JButton[n][m];//Tạo mới 1 jbutton kiểu mảng 2 chiều có n,m phần tử
    private JTextField textField;
    private int numberOfMatch;
    private Timer timer;
    private Integer second, minute;
    private String competitorIP;
    private String yourTurn;
    private int yourScore=0;
    private int enmScore=0;
    private String enmTurn;
    JLabel lbl_Time = new JLabel();
    JTextArea textArea = new JTextArea();
    JLabel lbl_Luot = new JLabel();
    JLabel lbl_TenBan = new JLabel();
    JLabel lbl_TenDoiThu = new JLabel();
    JLabel lblYourSC = new JLabel();
    JLabel lblEnmSC = new JLabel();
    private final JLabel lblNewLabel_2 = new JLabel("Điểm :");
    private final JLabel lblNewLabel_2_1 = new JLabel("Điểm :");
	/**
	 * Launch the application.
	 */
	public Carogame_view(int room_ID, int isStart, String competitorIP) {
		setTitle("Caro game");
		KhoiTao();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 50, 1075, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setResizable(false);
		
		numberOfMatch = isStart;
		this.competitorIP = competitorIP;
        second = 60;
        minute = 0;
        
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = minute.toString();
                String temp1 = second.toString();
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                if (temp1.length() == 1) {
                    temp1 = "0" + temp1;
                }
                if (second == 0) {
                    lbl_Time.setText("Thời Gian:" + temp + ":" + temp1);
                    second = 60;
                    minute = 0;
                    try {
                    	JOptionPane.showMessageDialog(rootPane, "bạn đã thua do hết thời gian");
                        Client.socketHandle.write("lose,");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    }
                    
                } else {
                	lbl_Time.setText("Thời Gian:" + temp + ":" + temp1);
                    second--;
                }
            }
        });   

		setContentPane(contentPane);
		contentPane.setLayout(null);
		panelchinh.setBackground(new Color(192, 192, 192));
		
		panelchinh.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelchinh.setBounds(350, 5, 700, 700);
		contentPane.add(panelchinh);
		panelchinh.setLayout(new GridLayout(n,m));
		
		JLabel lblNewLabel = new JLabel("Bạn");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 10, 330, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lbliTh = new JLabel("Đối thủ");
		lbliTh.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbliTh.setBounds(10, 113, 330, 28);
		contentPane.add(lbliTh);
		lbl_TenBan.setHorizontalAlignment(SwingConstants.CENTER);
		
		lbl_TenBan.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_TenBan.setBounds(10, 43, 60, 20);
		contentPane.add(lbl_TenBan);
		lbl_TenDoiThu.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_TenDoiThu.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		
		lbl_TenDoiThu.setBounds(10, 157, 60, 20);
		contentPane.add(lbl_TenDoiThu);
		
		textArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10, 357, 330, 119);
		contentPane.add(scroll);
		scroll.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setBounds(10, 501, 234, 28);
		contentPane.add(textField);
		textField.requestFocus();
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
    		            Client.socketHandle.write("chat," + textField.getText());
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
				try {
		            if (textField.getText().isEmpty()) {
		                throw new Exception("Vui lòng nhập nội dung tin nhắn");
		            }
		            String temp = textArea.getText();
		            temp += "Tôi: " + textField.getText() + "\n";
		            textArea.setText(temp);
		            Client.socketHandle.write("chat," + textField.getText());
		            textField.setText("");
		            textField.requestFocus();
		            textArea.setCaretPosition(textArea.getDocument().getLength());
		        } catch (IOException ex) {
		            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
		        }
			}
		});
		btn_Gui.setBounds(268, 500, 72, 28);
		contentPane.add(btn_Gui);
		
		lbl_Time.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Time.setBounds(62, 251, 207, 20);
		contentPane.add(lbl_Time);
		lbl_Luot.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		lbl_Luot.setBounds(10, 290, 330, 28);
		contentPane.add(lbl_Luot);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 101, 338, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 215, 338, 2);
		contentPane.add(separator_1);
		
		JLabel lblNewLabel_1 = new JLabel("Chat");
		lblNewLabel_1.setBounds(10, 344, 72, 13);
		contentPane.add(lblNewLabel_1);
		
		lblYourSC.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblYourSC.setBounds(238, 43, 45, 20);
		contentPane.add(lblYourSC);
		
		lblEnmSC.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEnmSC.setBounds(238, 157, 45, 20);
		contentPane.add(lblEnmSC);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(162, 157, 45, 20);
		
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2_1.setBounds(162, 43, 45, 20);
		
		contentPane.add(lblNewLabel_2_1);
		
		URL url = Carogame_view.class.getResource("caro_game.png");
		Image logo = Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage(logo);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitGame();
            }
        });

	}
	//khởi tạo mảng cho các j button
	public void KhoiTao() {
		for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                btn[i][j] = new JButton();//khởi tạo bộ nhớ cho từng jbutton
                btn[i][j].addActionListener(new lis());//khi con trỏ chuột trỏ vào phần tử tương ứng nào
                btn[i][j].setBackground(Color.white);//Xét màu cho Jbutton tương ứng đang xét
                btn[i][j].setFont(new Font("Tahoma", Font.BOLD, 11));
                panelchinh.add(btn[i][j]);//add giá trị vào phần tử trỏ chuột tương ứng
            }
        }
	}
	class lis implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < m; j++) {
	                if (e.getSource() == btn[i][j] && btn[i][j].getText() != "X" && btn[i][j].getText() != "O") {
	                    if (diem % 2 == 0) {
	                        /**/
	                        /**/
	                        btn[i][j].setText("X");
	                        btn[i][j].setForeground(Color.RED);
	                        try {
								Client.socketHandle.write("caro," + i + "," + j);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	                        setEnableButton(false);
	                        displayCompetitorTurn();
	                        diem++;
	                        if (win(i, j, btn[i][j].getText())) {
	                            btn[i][j].setBackground(Color.red);
	                            JOptionPane.showMessageDialog(null, "Bạn thắng!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
	                            JOptionPane.showMessageDialog(null, "Trò Chơi Mới", "Thoát", JOptionPane.INFORMATION_MESSAGE);
	                            yourScore++;
	                            try {
									Client.socketHandle.write("win,"+i+","+j);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	                        }
	                    } else {
	                        /**/
	                        //lb2.setText("Tọa độ : (" + i + ", " + j + ")");
	                        /**/
	                        btn[i][j].setText("O");
	                        btn[i][j].setForeground(Color.blue);
	                        try {
								Client.socketHandle.write("caro," + i + "," + j);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	                        setEnableButton(false);
	                        displayCompetitorTurn();
	                        diem++;
	                        if (win(i, j, btn[i][j].getText())) {
	                            btn[i][j].setBackground(Color.green);
	                            JOptionPane.showMessageDialog(null, "Bạn thắng!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
	                            JOptionPane.showMessageDialog(null, "Trò Chơi Mới", "Thoát", JOptionPane.INFORMATION_MESSAGE);
	                            yourScore++;
	                            try {
									Client.socketHandle.write("win,"+i+","+j);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	                        }
	                    }
	                    stopTimer();
	                }
	            }
	        }
			
		}
		
	}
	//kiểm tra thắng
    public boolean win(int x, int y, String name) {
        int k, j;
        int d = 0;
        // kt chiều dọc
        for (k = -4; k <= 4; k++) {
            if (x + k >= 0 && x + k < n) {
                if (btn[x + k][y].getText() == name) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        //kt hàng ngang
        for (k = -5; k <= 5; k++) {
            if (y + k >= 0 && y + k < n) {
                if (btn[x][y + k].getText() == name) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        //kt đường chéo
        for (k = -4, j = 4; k <= 4 && j >= -4; k++, j--) {
            if (y + k >= 0 && y + k < n && x + j >= 0 && x + j < m) {
                if (btn[x + j][y + k].getText() == name) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        for (k = -4; k <= 4; k++) {
            if (y + k >= 0 && y + k < n && x + k >= 0 && x + k < m) {
                if (btn[x + k][y + k].getText() == name) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        return d >= 5;
    }
    public void exitGame() {
        try {
            timer.stop();
            Client.socketHandle.write("left-room,");
            Client.closeAllViews();
            JOptionPane.showMessageDialog(rootPane, "Đang trở về lại màn hình chính");
            Client.openView(Client.View.HOMEPAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        Client.closeAllViews();
        Client.openView(Client.View.HOMEPAGE);
    }
    public void stopAllThread(){
        timer.stop();
    }
    public void stopTimer(){
        timer.stop();
    }
    public void showMessage(String message){
        JOptionPane.showMessageDialog(rootPane, message);
    }
    int not(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 0) {
            return 1;
        }
        return 0;
    }
    public void displayDrawRefuse(){
        JOptionPane.showMessageDialog(rootPane, "Đối thủ không chấp nhận hòa, mời bạn chơi tiếp");
        timer.start();
        setEnableButton(true);
    }
    public void setEnableButton(boolean b) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (btn[i][j].getText() != "X" && btn[i][j].getText() != "O") {
                    btn[i][j].setEnabled(b);
                }
            }
        }
    }
    public void setEnableAllButton(boolean b) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {               
                    btn[i][j].setEnabled(b);
            }
        }
    }
    public void caro(String x, String y) {
        int xx, yy;
        xx = Integer.parseInt(x);
        yy = Integer.parseInt(y);
        // danh dau vi tri danh
        if ( btn[xx][yy].getText() != "X" && btn[xx][yy].getText() != "O") {
            if (diem % 2 == 0) {
                /**/
                /**/
                btn[xx][yy].setText("X");
                btn[xx][yy].setForeground(Color.RED);    
                setEnableButton(true);
                diem++;
                if (win(xx, yy, btn[xx][yy].getText())) {
                    btn[xx][yy].setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "Đối thủ thắng!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Trò Chơi Mới", "Thoát", JOptionPane.INFORMATION_MESSAGE);
                    enmScore++;
                    timer.stop();
                }
            } else {
                btn[xx][yy].setText("O");
                btn[xx][yy].setForeground(Color.blue);
                setEnableButton(true);
                diem++;
                if (win(xx, yy, btn[xx][yy].getText())) {
                    btn[xx][yy].setBackground(Color.green);
                    JOptionPane.showMessageDialog(null, "Đối thủ thắng!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Trò Chơi Mới", "Thoát", JOptionPane.INFORMATION_MESSAGE);
                    enmScore++;
                    timer.stop();
                }
            }
        }

//            Client.openView(Client.View.GAMENOTICE,"Bạn đã thua","Đang thiết lập ván chơi mới");
    }
    void setupButton() {
    	
    }
    public void addMessage(String message){
        String temp = textArea.getText();
        temp += "Đối thủ " + ": " + message+"\n";
        textArea.setText(temp);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    public void addCompetitorMove(String x, String y){
        displayUserTurn();
        startTimer();
        //setEnableButton(true);
        caro(x, y);
    }
    public void startTimer(){
        second = 60;
        minute = 0;
        timer.start();
    }
    public void newgame() {
        if (numberOfMatch % 2 == 0) {
            JOptionPane.showMessageDialog(rootPane, "Đến lượt bạn đi trước");
            yourTurn = "X";
            enmTurn ="O";
            startTimer();
            displayUserTurn();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Đối thủ đi trước");
            yourTurn = "O";
            enmTurn ="X";
            displayCompetitorTurn();
            setEnableAllButton(false);
        }
        for (int i1 = 0; i1 < n; i1++) {
            for (int j1 = 0; j1 < m; j1++) {
                btn[i1][j1].setText("");
                btn[i1][j1].setBackground(Color.white);
            }
        }
        if(yourTurn=="O") {
    		lbl_TenBan.setForeground(Color.BLUE);
    		lbl_TenDoiThu.setForeground(Color.RED);
    	}else {
    		lbl_TenBan.setForeground(Color.RED);
    		lbl_TenDoiThu.setForeground(Color.BLUE);
    	}
        lblYourSC.setText(yourScore+"");
        lblEnmSC.setText(enmScore+"");
        lbl_TenBan.setText(yourTurn);
        lbl_TenDoiThu.setText(enmTurn);
        diem = 0;
        numberOfMatch++;
    }
    public void displayUserTurn() {
    	if(yourTurn=="O") {
    		lbl_Luot.setForeground(Color.BLUE);
    	}else {
    		lbl_Luot.setForeground(Color.RED);
    	}
        lbl_Luot.setText("Lượt của bạn : "+ yourTurn);
        setEnableButton(true);
        lbl_Time.setVisible(true);
    }
    public void displayCompetitorTurn() {
    	if(enmTurn=="O") {
    		lbl_Luot.setForeground(Color.BLUE);
    	}else {
    		lbl_Luot.setForeground(Color.RED);
    	}
    	lbl_Luot.setText("Lượt của đối thủ : "+ enmTurn);
    	setEnableButton(false);
    	lbl_Time.setVisible(false);
    }
    public void displayCopetitorOut() {
    	JOptionPane.showMessageDialog(rootPane, "Đối thủ đã thoát, đang trở về màn hình chính");
    }
}
