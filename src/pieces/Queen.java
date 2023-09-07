package pieces;

import java.util.ArrayList;

import chess.Cell;

// Đây là lớp Queen được kế thừa từ lớp Piece trừu tượng
public class Queen extends Piece {
	// Constructors
	public Queen(String i, String p, int c) {
		setId(i);
		setPath(p);
		setColor(c);
	}

	// Chức năng di chuyển
	public ArrayList<Cell> move(Cell state[][], int x, int y) {
		// Queen có thể di chuyển bất kỳ trong cả 8 hướng
		possiblemoves.clear();

		// Kiểm tra các di chuyển có thể theo hướng thẳng
		int tempx = x - 1;
		while (tempx >= 0) {
			if (state[tempx][y].getpiece() == null)
				possiblemoves.add(state[tempx][y]);
			else if (state[tempx][y].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[tempx][y]);
				break;
			}
			tempx--;
		}

		tempx = x + 1;
		while (tempx < 8) {
			if (state[tempx][y].getpiece() == null)
				possiblemoves.add(state[tempx][y]);
			else if (state[tempx][y].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[tempx][y]);
				break;
			}
			tempx++;
		}

		// Kiểm tra các di chuyển có thể theo hướng ngang
		int tempy = y - 1;
		while (tempy >= 0) {
			if (state[x][tempy].getpiece() == null)
				possiblemoves.add(state[x][tempy]);
			else if (state[x][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[x][tempy]);
				break;
			}
			tempy--;
		}
		tempy = y + 1;
		while (tempy < 8) {
			if (state[x][tempy].getpiece() == null)
				possiblemoves.add(state[x][tempy]);
			else if (state[x][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[x][tempy]);
				break;
			}
			tempy++;
		}

		// Kiểm tra các di chuyển có thể theo hướng chéo
		tempx = x + 1;
		tempy = y - 1;
		while (tempx < 8 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null)
				possiblemoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy--;
		}
		tempx = x - 1;
		tempy = y + 1;
		while (tempx >= 0 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null)
				possiblemoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy++;
		}
		tempx = x - 1;
		tempy = y - 1;
		while (tempx >= 0 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null)
				possiblemoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy--;
		}
		tempx = x + 1;
		tempy = y + 1;
		while (tempx < 8 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null)
				possiblemoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy++;
		}
		return possiblemoves;
	}
}