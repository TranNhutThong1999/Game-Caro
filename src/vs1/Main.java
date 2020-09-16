package vs1;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Main extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Control control;
	private int size;

	public Main(int size) {
		control = new Control(size);
		control.setDefault();
		this.size = size;
	}

	static int COUNT = 0;

	JPanel panelNorth = new JPanel();
	JPanel panelSouth = new JPanel();
	JPanel panelCenter = new JPanel();// tạo mới 1 jpanel có tên là pan

	public JFrame frame = new JFrame();// tạo mới 1 jframe có tên là frame
	public int n = 15, m = 15, num = 0, diem = 0;// khởi tạo các giá trị : kích thước của từng button, điểm, số
	public JButton btn[][] = new JButton[n][m];// Tạo mới 1 jbutton kiểu mảng 2 chiều có n,m phần tử
	int pos[][] = new int[n][m]; // Tạo 1 biến kiểu int chứa giá trị tương ứng của phần tử trong mảng 2 chiều

	// ham add
	public void add() { // Khởi tạo 1 hàm để add các giá trị vào
		frame.setLayout(new BorderLayout());

		panelNorth.setBackground(Color.LIGHT_GRAY);
		panelSouth.setBackground(Color.DARK_GRAY);

		JLabel lbHeader = new JLabel("G A M E  C A R O");

		panelNorth.add(lbHeader);

		JButton btnStart = new JButton("Start");
		JButton btnRule = new JButton("Rule");

		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(null, "Trò Chơi Mới", "Message", JOptionPane.INFORMATION_MESSAGE);
				control = new Control(size);
				control.setDefault();
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
				JOptionPane.showMessageDialog(null, "-	Cách chơi: \n" + "Team nào 5 ô liên tiếp giống nhau thì thắng!",
						"Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		panelSouth.add(btnStart);
		panelSouth.add(btnRule);

		frame.add(panelNorth, BorderLayout.NORTH);
		frame.add(panelSouth, BorderLayout.SOUTH);
		frame.add(panelCenter); // add jpanel vào 1 jframe
		panelCenter.setLayout(new GridLayout(n, m));// xét giá trị vào jpanel
		for (int i = 0; i < n; i++) {// số hàng của jpanel
			for (int j = 0; j < m; j++) {
				// số cột của jpanel
				pos[i][j] = num;// gán từng giá trị của từng phần tử bằng giá trị số int
				num++;// tăng giá trị số lên lần lượt từng đơn vị
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				btn[i][j] = new JButton();// khởi tạo bộ nhớ cho từng jbutton
				btn[i][j].addActionListener(this);// khi con trỏ chuột trỏ vào phần tử tương ứng nào
				panelCenter.add(btn[i][j]);// add giá trị vào phần tử trỏ chuột tương ứng
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				btn[i][j].setBackground(Color.white);// Xét màu cho Jbutton tương ứng đang xét
			}
		}
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		panelCenter.setSize(700, 9000);
		frame.setSize(700, 700);
		frame.setResizable(false);

	}

	/**/
	JLabel lb1 = new JLabel("Tọa độ: ");
	JLabel lb2 = new JLabel("Tọa độ : ");

	/**/
	// tim o trong de set text

	@SuppressWarnings("unused")
	private void run() {
		control.move(control.isTurn(), 5, 5);
		btn[5][5].setText(control.isTurn() ? "X" : "O");
		btn[5][5].setForeground(control.isTurn() ? Color.RED : Color.BLUE);
		panelCenter.add(btn[5][5]);
	}

	public void actionPerformed(ActionEvent e) {

		boolean check;
		if (!control.isTurn()) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (e.getSource() == btn[i][j]) {
						check = control.move(control.isTurn(), i, j);
						if (check) {
							btn[i][j].setText(control.isTurn() ? "X" : "O");
							btn[i][j].setForeground(control.isTurn() ? Color.RED : Color.BLUE);

							if (control.isOver(i, j, control.getTempBoard())) {
								btn[i][j].setBackground(control.isTurn() ? Color.RED : Color.BLUE);
								JOptionPane.showMessageDialog(null, control.isTurn() ? "X win!" : "O win!",
										"Game Over!", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							control.changeTurn();
						}

					}

				}
			}
		}
		if (control.isTurn()) {
			Point point = control.findMoveAI();
			System.out.println(point.toString());
			control.move(control.isTurn(), point.getX(), point.getY());
			int i = point.getX();
			int j = point.getY();
			btn[i][j].setText(control.isTurn() ? "X" : "O");
			btn[i][j].setForeground(control.isTurn() ? Color.RED : Color.BLUE);

			if (control.isOver(i, j, control.getTempBoard())) {
				btn[i][j].setBackground(control.isTurn() ? Color.RED : Color.BLUE);
				JOptionPane.showMessageDialog(null, control.isTurn() ? "X win!" : "O win!", "Game Over!",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			control.changeTurn();

		}
	}

	public static void main(String[] args) {
		Main c = new Main(15);
		c.add();
	}
}