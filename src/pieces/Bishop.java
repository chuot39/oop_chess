package pieces;

import java.util.ArrayList;

import chess.Cell;

/**
 * Class Bishop.
 * Cách di chuyển Tượng trên bàn cờ
 */

public class Bishop extends Piece {

	// Constructor
	public Bishop(String i, String p, int c) {
		setId(i);
		setPath(p);
		setColor(c);
	}

	// chức năng di chuyển được xác định. Nó trả về một danh sách tất cả các điểm
	// đến có thể có của Bishop

	// Nguyên tắc cơ bản của cách di chuyển của Tượng trên bàn cờ đã được thực hiện
	public ArrayList<Cell> move(Cell state[][], int x, int y) {
		// Tượng có thể Di chuyển theo đường chéo theo cả 4 hướng chéo
		// Chức năng này xác định logic
		possiblemoves.clear();
		int tempx = x + 1, tempy = y - 1;
		while (tempx < 8 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null) {
				possiblemoves.add(state[tempx][tempy]);
			} else if (state[tempx][tempy].getpiece().getcolor() == this.getcolor())
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