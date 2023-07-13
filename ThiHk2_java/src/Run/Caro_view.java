package Run;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
//Khởi tạo 1 lưới các ô vuông là các button

public class Caro_view extends JPanel implements ActionListener {

    static int COUNT = 0;

    JPanel panelNorth = new JPanel();
    JPanel panelWest = new JPanel();
    JPanel panelEast = new JPanel();
    JPanel panelSouth = new JPanel();
    JPanel panelCenter = new JPanel();
    JLabel lb1 = new JLabel("Tọa độ: ");
    JLabel lb2 = new JLabel("Tọa độ : ");

    public JFrame frame = new JFrame();//tạo mới 1 jframe có tên là frame
    public int n = 19, m = 19, num = 0, diem = 0;//khởi tạo các giá trị : kích thước của từng button, điểm, số 
    public JButton btn[][] = new JButton[n][m];//Tạo mới 1 jbutton kiểu mảng 2 chiều có n,m phần tử
    int pos[][] = new int[m][n]; //Tạo 1 biến kiểu int chứa giá trị tương ứng của phần tử trong mảng 2 chiều
    
    //ham add
    public void add() { //Khởi tạo 1 hàm để add các giá trị vào 
        frame.setLayout(new BorderLayout());

        panelNorth.setBackground(Color.pink);
        panelEast.setBackground(Color.gray);
        panelSouth.setBackground(Color.red);
        panelWest.setBackground(Color.gray);

        JLabel lbHeader = new JLabel("GAME CARO");
        JLabel lbTime = new JLabel("Time : ");

        panelNorth.add(lbHeader);
        panelNorth.add(lbTime);

        JLabel lbPlayer1 = new JLabel("Player1");
        JLabel lbImagePlayer1 = new JLabel();
        //ImageIcon ImgPlay1 = new ImageIcon("C://Users//Admin//OneDrive//Pictures//Saved Pictures/kirito.jpg");
        //lbImagePlayer1.setIcon(ImgPlay1);
        JLabel lbName1 = new JLabel("Steps : ");
        JLabel lbTime1 = new JLabel("Time Thinhking : ");

        //panelEast.setLocation(2, 2);
        panelEast.add(lbPlayer1);
        panelEast.add(lbImagePlayer1);
        panelEast.add(lbName1);
        panelEast.add(lbTime1);

        //lbName1.setLocation(0,0);
        JLabel lbPlayer2 = new JLabel("Player2");
        JLabel lbImagePlayer2 = new JLabel();
        //ImageIcon ImgPlay2 = new ImageIcon("C://Users//Admin//OneDrive//Pictures//Saved Pictures/ayaka3.jpg");
        //lbImagePlayer2.setIcon(ImgPlay2);
        JLabel lbName2 = new JLabel("Steps : ");
        JLabel lbTime2 = new JLabel("Time Thinhking : ");

        panelWest.add(lbImagePlayer2);
        panelWest.add(lbPlayer2);
        panelWest.add(lbName2);
        panelWest.add(lbTime2);

        JButton btnStart = new JButton("Start");
        JButton btnPause = new JButton("Pause");
        JButton btnPlay = new JButton("Play");
        JButton btnRule = new JButton("Rule");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null, "Trò Chơi Mới", "Thoát", JOptionPane.INFORMATION_MESSAGE);
                for (int i1 = 0; i1 < n; i1++) {
                    for (int j1 = 0; j1 < m; j1++) {
                        btn[i1][j1].setText("");
                        btn[i1][j1].setBackground(Color.white);
                    }
                }
            }
        });

        btnRule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null, "-	Cách chơi: \n"
                        + "Trò chơi được chơi trên bàn cờ có 324 ô, với 18 dòng và 18 cột."
                        + " Người chiến thắng là người tạo được đường thẳng theo chiều dọc"
                        + " hoặc ngang hoặc chéo với chính xác 5 con cờ của mình.", "Thoát", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panelSouth.add(btnStart);
        panelSouth.add(btnPause);
        panelSouth.add(btnPlay);
        panelSouth.add(btnRule);

        frame.add(panelNorth, BorderLayout.NORTH);
        frame.add(panelEast, BorderLayout.EAST);
        frame.add(panelWest, BorderLayout.WEST);
        frame.add(panelSouth, BorderLayout.SOUTH);
        frame.add(panelCenter); //add jpanel vào 1 jframe
        panelCenter.setLayout(new GridLayout(n, m));//xét giá trị vào jpanel 
        for (int i = 0; i < n; i++) {//số hàng của jpanel
            for (int j = 0; j < m; j++) {
                //số cột của jpanel
                pos[i][j] = num;//gán từng giá trị của từng phần tử bằng giá trị số int
                num++;//tăng giá trị số lên lần lượt từng đơn vị
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                btn[i][j] = new JButton();//khởi tạo bộ nhớ cho từng jbutton
                btn[i][j].addActionListener(this);//khi con trỏ chuột trỏ vào phần tử tương ứng nào
                panelCenter.add(btn[i][j]);//add giá trị vào phần tử trỏ chuột tương ứng
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                btn[i][j].setBackground(Color.white);//Xét màu cho Jbutton tương ứng đang xét
            }
        }
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        panelCenter.setSize(700, 9000);
        panelEast.setSize(50, 9000);
        panelWest.setSize(50, 9000);
        frame.setSize(1500, 700);
        frame.setResizable(false);
        /*frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOS E);*/
    }
    /**/


    /**/
    //tim o trong de set text
    public void actionPerformed(ActionEvent e) {
        /**/
        if (COUNT == 0) {
            panelEast.add(lb1);
            lb1.setLocation(100, 100);
            panelWest.add(lb2);
            lb2.setLocation(100, 100);
        } else {
            panelEast.remove(lb1);
            panelWest.remove(lb2);
            panelEast.add(lb1);
            lb1.setLocation(100, 100);
            panelWest.add(lb2);
            lb2.setLocation(100, 100);
        }
        COUNT = 1;
        /**/

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (e.getSource() == btn[i][j] && btn[i][j].getText() != "X" && btn[i][j].getText() != "O") {
                    if (diem % 2 == 0) {
                        /**/
                        lb1.setText("Tọa độ : (" + i + ", " + j + ")");
                        /**/
                        btn[i][j].setText("X");
                        btn[i][j].setForeground(Color.RED);
                        diem++;
                        if (win(i, j, btn[i][j].getText())) {
                            btn[i][j].setBackground(Color.red);
                            JOptionPane.showMessageDialog(null, "X win!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
                            for (int i1 = 0; i1 < n; i1++) {
                                for (int j1 = 0; j1 < m; j1++) {
                                    btn[i1][j1].setText("");
                                    btn[i1][j1].setBackground(Color.white);
                                }
                            }
                        }
                    } else {
                        /**/
                        lb2.setText("Tọa độ : (" + i + ", " + j + ")");
                        /**/
                        btn[i][j].setText("O");
                        btn[i][j].setForeground(Color.BLACK);
                        diem++;
                        if (win(i, j, btn[i][j].getText())) {
                            btn[i][j].setBackground(Color.green);
                            JOptionPane.showMessageDialog(null, "O win!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
                            JOptionPane.showMessageDialog(null, "Trò Chơi Mới", "Thoát", JOptionPane.INFORMATION_MESSAGE);
                            for (int i1 = 0; i1 < n; i1++) {
                                for (int j1 = 0; j1 < m; j1++) {
                                    btn[i1][j1].setText("");
                                    btn[i1][j1].setBackground(Color.white);
                                }
                            }
                        }
                    }
//                JOptionPane.showMessageDialog(this, i + " " + j);
                }

            }
        }
    }

    //kiem tra thang 
    public boolean win(int x, int y, String name) {
        int k, j;
        int d = 0;
        // kt chieu doc
        for (k = -4; k <= 4; k++) {
            if (x + k >= 0 && x + k < n) {
                if (btn[x + k][y].getText() == name) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        if (d >= 5) {
            return true;
        } else {
            d = 0;
        }
        //xet ngang
        for (k = -5; k <= 5; k++) {
            if (y + k >= 0 && y + k < n) {
                if (btn[x][y + k].getText() == name) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        if (d >= 5) {
            return true;
        } else {
            d = 0;
        }
        //cheo
        for (k = -4, j = 4; k <= 4 && j >= -4; k++, j--) {
            if (y + k >= 0 && y + k < n && x + j >= 0 && x + j < m) {
                if (btn[x + j][y + k].getText() == name) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        if (d >= 5) {
            return true;
        } else {
            d = 0;
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
        if (d >= 5) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Caro_view c = new Caro_view();
        c.add();
        c.setVisible(true);
    }
}
