package pieces;

import java.util.ArrayList;

import chess.Cell;

public class King extends Piece {

	private int x, y; // Extra variables for King class to keep a track of king's position
	private boolean firstMove;

	// King Constructor
	public King(String i, String p, int c, int x, int y) {
		setx(x);
		sety(y);
		setId(i);
		setPath(p);
		setColor(c);

		firstMove = true;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	public boolean isFirstMove() {
		return firstMove;
	}

	// general value access functions-chức năng truy cập giá trị chung
	public void setx(int x) {
		this.x = x;
	}

	public void sety(int y) {
		this.y = y;
	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}

	public ArrayList<Cell> move(Cell state[][], int x, int y) {
		return move(state, x, y, false);
	}

	// -Chức năng di chuyển cho King
	// Overridden from Pieces
	public ArrayList<Cell> move(Cell state[][], int x, int y, boolean ischecked) {
		// Vua chỉ có thể di chuyển một bước. Vì vậy, tất cả 8 ô liền kề đã được xem
		// xét.
		possiblemoves.clear();
		int posx[] = { x, x, x + 1, x + 1, x + 1, x - 1, x - 1, x - 1 };
		int posy[] = { y - 1, y + 1, y - 1, y, y + 1, y - 1, y, y + 1 };
		for (int i = 0; i < 8; i++)
			if ((posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8))
				if ((state[posx[i]][posy[i]].getpiece() == null
						|| state[posx[i]][posy[i]].getpiece().getcolor() != this.getcolor()))
					possiblemoves.add(state[posx[i]][posy[i]]);

		if (firstMove && !ischecked) {
			if (getcolor() == 0) {
				if (state[7][0].getpiece() instanceof Rook && ((Rook) state[7][0].getpiece()).isFirstMove()) {
					boolean flag = true;
					for (int i = 3; i > 0; i--) {
						flag &= (state[7][i].getpiece() == null);
					}
					if (flag) {
						possiblemoves.add(state[7][2]);
					}
				}

				if (state[7][7].getpiece() instanceof Rook && ((Rook) state[7][7].getpiece()).isFirstMove()) {
					if (state[7][6].getpiece() == null && state[7][5].getpiece() == null) {
						possiblemoves.add(state[7][6]);
					}
				}
			}

			if (getcolor() == 1) {
				if (state[0][0].getpiece() instanceof Rook && ((Rook) state[0][0].getpiece()).isFirstMove()) {
					boolean flag = true;
					for (int i = 3; i > 0; i--) {
						flag &= (state[0][i].getpiece() == null);
					}
					if (flag) {
						possiblemoves.add(state[0][2]);
					}
				}

				if (state[0][7].getpiece() instanceof Rook && ((Rook) state[0][7].getpiece()).isFirstMove()) {
					if (state[0][6].getpiece() == null && state[0][5].getpiece() == null) {
						possiblemoves.add(state[0][6]);
					}
				}
			}
		}

		return possiblemoves;
	}

	// Chức năng kiểm tra xem vua có chiếu không
	// kiểm tra xem có bất kỳ quân cờ nào có màu đối lập có thể tấn công vua hay không

	public boolean isindanger(Cell state[][]) {

		// Kiểm tra tấn công từ trái, phải, lên và xuống
		for (int i = x + 1; i < 8; i++) {
			if (state[i][y].getpiece() == null)
				continue;
			else if (state[i][y].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if ((state[i][y].getpiece() instanceof Rook) || (state[i][y].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}
		for (int i = x - 1; i >= 0; i--) {
			if (state[i][y].getpiece() == null)
				continue;
			else if (state[i][y].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if ((state[i][y].getpiece() instanceof Rook) || (state[i][y].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}
		for (int i = y + 1; i < 8; i++) {
			if (state[x][i].getpiece() == null)
				continue;
			else if (state[x][i].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if ((state[x][i].getpiece() instanceof Rook) || (state[x][i].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}
		for (int i = y - 1; i >= 0; i--) {
			if (state[x][i].getpiece() == null)
				continue;
			else if (state[x][i].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if ((state[x][i].getpiece() instanceof Rook) || (state[x][i].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}

		// kiểm tra tấn công từ hướng chéo
		int tempx = x + 1, tempy = y - 1;
		while (tempx < 8 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx++;
				tempy--;
			} else if (state[tempx][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if (state[tempx][tempy].getpiece() instanceof Bishop || state[tempx][tempy].getpiece() instanceof Queen)
					return true;
				else
					break;
			}
		}
		tempx = x - 1;
		tempy = y + 1;
		while (tempx >= 0 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx--;
				tempy++;
			} else if (state[tempx][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if (state[tempx][tempy].getpiece() instanceof Bishop || state[tempx][tempy].getpiece() instanceof Queen)
					return true;
				else
					break;
			}
		}
		tempx = x - 1;
		tempy = y - 1;
		while (tempx >= 0 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx--;
				tempy--;
			} else if (state[tempx][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if (state[tempx][tempy].getpiece() instanceof Bishop || state[tempx][tempy].getpiece() instanceof Queen)
					return true;
				else
					break;
			}
		}
		tempx = x + 1;
		tempy = y + 1;
		while (tempx < 8 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx++;
				tempy++;
			} else if (state[tempx][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if (state[tempx][tempy].getpiece() instanceof Bishop || state[tempx][tempy].getpiece() instanceof Queen)
					return true;
				else
					break;
			}
		}

		// Kiểm tra cuộc tấn công từ Mã khác màu
		int posx[] = { x + 1, x + 1, x + 2, x + 2, x - 1, x - 1, x - 2, x - 2 };
		int posy[] = { y - 2, y + 2, y - 1, y + 1, y - 2, y + 2, y - 1, y + 1 };
		for (int i = 0; i < 8; i++)
			if ((posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8))
				if (state[posx[i]][posy[i]].getpiece() != null
						&& state[posx[i]][posy[i]].getpiece().getcolor() != this.getcolor()
						&& (state[posx[i]][posy[i]].getpiece() instanceof Knight)) {
					return true;
				}

		// Kiểm tra tấn công từ Tốt khác màu
		int pox[] = { x + 1, x + 1, x + 1, x, x, x - 1, x - 1, x - 1 };
		int poy[] = { y - 1, y + 1, y, y + 1, y - 1, y + 1, y - 1, y };
		{
			for (int i = 0; i < 8; i++)
				if ((pox[i] >= 0 && pox[i] < 8 && poy[i] >= 0 && poy[i] < 8))
					if (state[pox[i]][poy[i]].getpiece() != null
							&& state[pox[i]][poy[i]].getpiece().getcolor() != this.getcolor()
							&& (state[pox[i]][poy[i]].getpiece() instanceof King)) {
						return true;
					}
		}
		if (getcolor() == 0) {
			if (x > 0 && y > 0 && state[x - 1][y - 1].getpiece() != null
					&& state[x - 1][y - 1].getpiece().getcolor() == 1
					&& (state[x - 1][y - 1].getpiece() instanceof Pawn))
				return true;
			if (x > 0 && y < 7 && state[x - 1][y + 1].getpiece() != null
					&& state[x - 1][y + 1].getpiece().getcolor() == 1
					&& (state[x - 1][y + 1].getpiece() instanceof Pawn))
				return true;
		} else {
			if (x < 7 && y > 0 && state[x + 1][y - 1].getpiece() != null
					&& state[x + 1][y - 1].getpiece().getcolor() == 0
					&& (state[x + 1][y - 1].getpiece() instanceof Pawn))
				return true;
			if (x < 7 && y < 7 && state[x + 1][y + 1].getpiece() != null
					&& state[x + 1][y + 1].getpiece().getcolor() == 0
					&& (state[x + 1][y + 1].getpiece() instanceof Pawn))
				return true;
		}
		return false;
	}
}