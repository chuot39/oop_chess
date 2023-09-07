package pieces;

import java.util.ArrayList;

import chess.Cell;

/* Class Piece.Là một lớp trừu tượng mà từ đó tất cả các quân được kế thừa.
 * Xác định tất cả các chức năng chung cho tất cả các phần
 * Hàm move() một hàm trừu tượng phải được override trong tất cả các lớp kế thừa
 */
public abstract class Piece implements Cloneable {

	// Member Variables
	private int color;
	private String id = null;
	private String path;
	protected ArrayList<Cell> possiblemoves = new ArrayList<Cell>(); // Protected (access from child classes)

	public abstract ArrayList<Cell> move(Cell pos[][], int x, int y); // Abstract Function. Must be overridden

	// Id Setter
	public void setId(String id) {
		this.id = id;
	}

	// Path Setter
	public void setPath(String path) {
		this.path = path;
	}

	// Color Setter
	public void setColor(int c) {
		this.color = c;
	}

	// Path getter
	public String getPath() {
		return path;
	}

	// Id getter
	public String getId() {
		return id;
	}

	// Color Getter
	public int getcolor() {
		return this.color;
	}

	// Hàm trả về bản sao của đối tượng. Bản sao có cùng một giá trị biến nhưng tham
	// chiếu khác nhau
	public Piece getcopy() throws CloneNotSupportedException {
		return (Piece) this.clone();
	}
}