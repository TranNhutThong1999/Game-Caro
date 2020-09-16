package vs1;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Control {
	private int[][] tempBoard;
	private int size;
	private boolean turn; 
	private final int depth=3;

	public Control(int size) {
		this.tempBoard = new int[size][size];
		this.turn = false;
		this.size = size;
	}

	public Point findMoveAI() {
		int dem = 0;
		int temp = Integer.MIN_VALUE;
		 int score = 0;
		Point point = null;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (this.tempBoard[i][j] == -5) {
					int[][] board = clone(this.tempBoard);
					board[i][j] = 0;
					score = Minimax(board, depth -1, false, i, j, Integer.MIN_VALUE, Integer.MAX_VALUE);
					dem++;
					System.out.println("so lan chay " + dem);
					if (score >= temp) {
						temp = score;
						point = new Point();
						point.setX(i);
						point.setY(j);
					}
				}
			}
		}
		System.out.println("score: " + score);
		System.out.println("diem dnah tot nhat22222: " + point);
		
		return point;
	}

	public int Minimax(int[][] board, int depth, boolean turn, int x, int y, int alpha, int beta) {
		System.out.println("depth:" + depth);
		if (isOver(x, y, board)) {
			System.out.println("over");
			if (!turn)
				return 999999;
			else
				return -999999;
		}
		if (depth == 1) {
			return heuristic(board, x, y);
		}
		int temp = 0;
		if (turn == true) {
			temp = Integer.MIN_VALUE;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (board[i][j] == -5) {
						int[][] boardMax = clone(board);
						boardMax[i][j] = 0;
						int score = Minimax(boardMax, depth - 1, false, i, j, alpha, beta);
						temp = Math.max(score, temp);
						alpha = Math.max(alpha, temp);
						System.out.println("alpha" + beta + "||" + alpha);
						if (beta <= alpha) {
							System.out.println("break alpha");
							break;
						}
					}
				}
			}
		} else if (turn == false) {
			temp = Integer.MAX_VALUE;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (board[i][j] == -5) {
						int[][] boardMin = clone(board);
						boardMin[i][j] = 1;
						int score = Minimax(boardMin, depth - 1, true, i, j, alpha, beta);
						temp = Math.min(score, temp);
						beta =  Math.min(beta, temp); 
						System.out.println("beta:" + beta + " || " + alpha);
						if (beta <= alpha) {
							System.out.println("break beta");
							break;
						}
					}
				}
			}

		}
		return temp;
	}

	private int[][] clone(int[][] board) {
		int[][] temp = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				temp[i][j] = board[i][j];
			}
		}
		return temp;
	}


	private int heuristic(int[][] tempBoard, int i, int j) {
		int SCORE_ATTACK = score_vertical_attack(i, j, tempBoard) + score_Horizontal_attack(i, j, tempBoard)
				+ score_diagonalA_attack(i, j, tempBoard) + score_diagonalB_attack(i, j, tempBoard);
		int SCORE_DEFENSE = score_vertical_defense(i, j, tempBoard) + score_Horizontal_defense(i, j, tempBoard)
				+ score_diagonalA_defense(i, j, tempBoard) + score_diagonalB_defense(i, j, tempBoard);
		return SCORE_ATTACK - SCORE_DEFENSE;
	}

	private int score_Horizontal_attack(int x, int y, int[][] tempBoard) {
		String a = "";
		for (int i = 1; i < 5 && y + i < size; i++) {
			if (tempBoard[x][y + i] == 0) {
				a += "0";
			} else if (tempBoard[x][y + i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		for (int i = 1; i < 5 && y - i >= 0; i++) {
			if (tempBoard[x][y - i] == 0) {
				a += "0";
			} else if (tempBoard[x][y - i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		return countAttack(a);
	}

	private int score_Horizontal_defense(int x, int y, int[][] tempBoard) {
		String a = "";
		for (int i = 1; i < 5 && y + i < size; i++) {
			if (tempBoard[x][y + i] == 0) {
				a += "0";
			} else if (tempBoard[x][y + i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}

		}
		a += ";";
		for (int i = 1; i < 5 && y - i >= 0; i++) {
			if (tempBoard[x][y - i] == 0) {
				a += "0";
			} else if (tempBoard[x][y - i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";

		return countDefense(a);
	}

	private int score_vertical_attack(int x, int y, int[][] tempBoard) {
		String a = "";
		for (int i = 1; i < 5 && x + i < size; i++) {
			if (tempBoard[x + i][y] == 0) {
				a += "0";
			} else if (tempBoard[x + i][y] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		for (int i = 1; i < 5 && x - i >= 0; i++) {
			if (tempBoard[x - i][y] == 0) {
				a += "0";
			} else if (tempBoard[x - i][y] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		return countAttack(a);
	}

	private int score_vertical_defense(int x, int y, int[][] tempBoard) {
		String a = "";
		for (int i = 1; i < 5 && x + i < size; i++) {
			if (tempBoard[x + i][y] == 0) {
				a += "0";
			} else if (tempBoard[x + i][y] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		for (int i = 1; i < 5 && x - i >= 0; i++) {
			if (tempBoard[x - i][y] == 0) {
				a += "0";
			} else if (tempBoard[x - i][y] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		return countDefense(a);
	}

	private int score_diagonalA_attack(int x, int y, int[][] tempBoard) {
		String a = "";
		for (int i = 1; i < 5 && x + i < size && y + i < size; i++) {
			if (tempBoard[x + i][y + i] == 0) {
				a += "0";
			} else if (tempBoard[x + i][y + i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		for (int i = 1; i < 5 && x - i >= 0 && y - i >= 0; i++) {
			if (tempBoard[x - i][y - i] == 0) {
				a += "0";
			} else if (tempBoard[x - i][y - i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		return countAttack(a);
	}

	private int score_diagonalA_defense(int x, int y, int[][] tempBoard) {
		String a = "";
		for (int i = 1; i < 5 && x + i < size && y + i < size; i++) {
			if (tempBoard[x + i][y + i] == 0) {
				a += "0";
			} else if (tempBoard[x + i][y + i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		for (int i = 1; i < 5 && x - i >= 0 && y - i >= 0; i++) {
			if (tempBoard[x - i][y - i] == 0) {
				a += "0";
			} else if (tempBoard[x - i][y - i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		return countDefense(a);
	}

	private int score_diagonalB_attack(int x, int y, int[][] tempBoard) {
		String a = "";
		for (int i = 1; i < 5 && x + i < size && y - i >= 0; i++) {
			if (tempBoard[x + i][y - i] == 0) {
				a += "0";
			} else if (tempBoard[x + i][y - i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		for (int i = 1; i < 5 && x - i >= 0 && y + i < size; i++) {
			if (tempBoard[x - i][y + i] == 0) {
				a += "0";
			} else if (tempBoard[x - i][y + i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		return countAttack(a);
	}

	private int score_diagonalB_defense(int x, int y, int[][] tempBoard) {
		String a = "";
		for (int i = 1; i < 5 && x + i < size && y - i >= 0; i++) {
			if (tempBoard[x + i][y - i] == 0) {
				a += "0";
			} else if (tempBoard[x + i][y - i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		for (int i = 1; i < 5 && x - i >= 0 && y + i < size; i++) {
			if (tempBoard[x - i][y + i] == 0) {
				a += "0";
			} else if (tempBoard[x - i][y + i] == 1) {
				a += "1";
			} else {
				a += "-5";
				break;
			}
		}
		a += ";";
		return countDefense(a);
	}

	private int sum(String s, String f) {
		Pattern pattern = Pattern.compile(f);
		Matcher matcher = pattern.matcher(s);
		int i = 0;
		while (matcher.find()) {
			i++;
		}
		return i;
	}

	String[] tempAt = { "0", "0-5", "00-5", "001-5", "001", "000-5", "0001", "0000", "00001", "0-5000" };
	String[] tempDe = { "1", "1-5", "11-5", "110-5", "110", "111-5", "1110", "1111", "11110", "1-5111" };
	int[] tempScore = { 10, 10, 300, 100, 100, 1000, 500, 5000, 5000, 5000 };

	private int countAttack(String a) {
		int result = 1;
		for (int i = 0; i < tempAt.length; i++) {
			StringBuilder ai = new StringBuilder();
			ai.append(tempAt[i]);
			result += tempScore[i] * sum(a, ai.toString());
		}
		return result;
	}

	private int countDefense(String a) {
		int result = 1;

		for (int i = 0; i < tempDe.length; i++) {
			StringBuilder person = new StringBuilder();
			person.append(tempDe[i]);
			result += tempScore[i] * sum(a, person.toString());

		}
		return result;
	}

	public void changeTurn() {
		if (this.turn == true) {
			this.turn = false;
		} else {
			this.turn = true;
		}
	}

	public void setDefault() {
		for (int i = 0; i < this.tempBoard.length; i++) {
			for (int j = 0; j < this.tempBoard[i].length; j++) {
				this.tempBoard[i][j] = -5;
			}
		}
	}

	public boolean isValid(int x, int y) {
		boolean check = false;
		if (tempBoard[x][y] == -5) {
			check = true;
		}
		return check;
	}

	public boolean move(boolean turn, int x, int y) {
		if (isValid(x, y)) {
			if (turn) {
				tempBoard[x][y] = 0;
			} else {
				tempBoard[x][y] = 1;
			}

			return true;
		}
		return false;
	}

	// kiem tra win
	public boolean isOver(int x, int y, int[][] board) {
		// TODO Auto-generated method stub
		if(board[x][y]!=-5) {
			if (horizontal(x, y, board) || vertical(x, y, board) || diagonalA(x, y, board) || diagonalB(x, y, board)) {
				System.out.println("trueeeeeeeeeeeeeeeeeeeeee");
				return true;
			}
		}
		return false;
	}

	// kiem tra win duong cheo phu
	private boolean diagonalB(int x, int y, int[][] board) {
		// TODO Auto-generated method stub
		int row = x;
		int column = y;
		int check = 0;
		if (row < size && column < size) {
			while (board[x][y] == board[row][column]) {
				check++;
				row++;
				column--;
				if (row >= size || column < 0)
					break;
			}
		}
		row = x - 1;
		column = y + 1;
		if (row >= 0 && column < size) {
			while (board[x][y] == board[row][column]) {
				check++;
				row--;
				column++;
				if (row < 0 || column >= size)
					break;
			}
		}
		if (check > 4)
			return true;
		
		return false;
	}

	// kiem tra win duong cheo chinh
	private boolean diagonalA(int x, int y, int[][] board) {
		// TODO Auto-generated method stub
		int row = x;
		int column = y;
		int check = 0;
		if (row < size && column < size) {
			while (board[x][y] == board[row][column]) {
				check++;
				row++;
				column++;
				if (row >= size || column >= size)
					break;
			}
		}
		row = x - 1;
		column = y - 1;
		if (row >= 0 && column >= 0) {
			while (board[x][y] == board[row][column]) {
				check++;
				row--;
				column--;
				if (row < 0 || column < 0)
					break;
			}
		}
		if (check > 4)
			return true;
		return false;
	}

	// kiem tra win chieu ngang
	private boolean horizontal(int x, int y, int[][] board) {
		// TODO Auto-generated method stub
		int check = 0;

		int column = y;
		if (column < size) {
			while (board[x][column] == board[x][y]) {
				check++;
				column++;
				if (column >= size)
					break;
			}
		}
		column = y - 1;
		if (column >= 0) {
			while (board[x][column] == board[x][y]) {
				check++;
				column--;
				if (column < 0)
					break;
			}
		}
		if (check > 4) {
			System.out.println("check :"+check);
			return true;
		}
		return false;
	}

	// kiem tra win chieu doc
	private boolean vertical(int x, int y, int[][] board) {
		// TODO Auto-generated method stub
		int row = x;
		int check = 0;
		if (row < size) {
			while (board[row][y] == board[x][y]) {
				check++;
				row++;
				if (row >= size)
					break;
			}
		}
		row = x - 1;
		if (row >= 0) {
			while (board[row][y] == board[x][y]) {
				check++;
				row--;
				if (row < 0)
					break;
			}
		}
		if (check > 4)
			return true;

		return false;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public int[][] getTempBoard() {
		return tempBoard;
	}

	public void setTempBoard(int[][] tempBoard) {
		this.tempBoard = tempBoard;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public static void main(String[] args) {
	}
}
