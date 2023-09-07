package chess;

import java.awt.*;
import javax.swing.*;

import pieces.*;

/* Lớp Cell. Có tổng cộng 64 ô */
public class Cell extends JPanel implements Cloneable {

	// Member var
	private static final long serialVersionUID = 1L;
	private boolean ispossibledestination;
	private JLabel content;
	private Piece piece;
	int x, y; // Public vì đc truy cập bởi các lớp khác
	private boolean isSelected = false;
	private boolean ischeck = false;

	// Constructors
	public Cell(int x, int y, Piece p) {
		this.x = x;
		this.y = y;

		setLayout(new BorderLayout());

		if ((x + y) % 2 == 1)
			setBackground(new Color(128, 128, 128));
		else
			setBackground(Color.white);

		if (p != null)
			setPiece(p);
	}

	// Hàm tạo lấy một ô làm đối số và trả về một ô mới sẽ có cùng dữ liệu nhưng
	// tham chiếu khác
	public Cell(Cell cell) throws CloneNotSupportedException {
		this.x = cell.x;
		this.y = cell.y;
		setLayout(new BorderLayout());
		if ((x + y) % 2 == 1)
			setBackground(new Color(128, 128, 128));
		else
			setBackground(Color.white);
		if (cell.getpiece() != null) {
			setPiece(cell.getpiece().getcopy());
		} else
			piece = null;
	}

	public void setPiece(Piece p) // Lấp cell bằng 1 quân
	{
		this.removeAll();
		piece = p;
		ImageIcon img = new javax.swing.ImageIcon(this.getClass().getResource(p.getPath()));
		content = new JLabel(img);
		this.add(content);
		repaint();
	}

	public void removePiece() // Xóa quân khỏi cell
	{
		if (piece instanceof King) {
			piece = null;
			this.remove(content);
		} else {
			piece = null;
			this.remove(content);
		}
	}

	public Piece getpiece() // truy cập vào một phần của một ô cụ thể
	{
		return this.piece;
	}

	public void select() // đánh dấu một ô đang được chọn
	{
		this.setBorder(BorderFactory.createLineBorder(Color.red, 6));
		this.isSelected = true;
	}

	public boolean isselected() // trả về nếu ô đang được chọn
	{
		return this.isSelected;
	}

	public void deselect() // bỏ chọn ô
	{
		this.setBorder(null);
		this.isSelected = false;
	}

	public void setpossibledestination() // Highlight một ô để cho biết rằng đó là một nước đi hợp lệ có thể
	{
		this.setBorder(BorderFactory.createLineBorder(Color.blue, 4));
		this.ispossibledestination = true;
	}

	public void removepossibledestination() // Xóa ô khỏi danh sách các nước đi có thể
	{
		this.setBorder(null);
		this.ispossibledestination = false;
	}

	public boolean ispossibledestination() // kiểm tra xem ô có phải là đích đến không
	{
		return this.ispossibledestination;
	}

	public void setcheck() // tô sáng ô hiện tại khi đã chọn (Đối với vua)
	{
		this.setBackground(Color.RED);
		this.ischeck = true;
	}

	public void removecheck() // bỏ chọn ktra
	{
		this.setBorder(null);
		if ((x + y) % 2 == 1)
			setBackground(new Color(128, 128, 128));
		else
			setBackground(Color.white);
		this.ischeck = false;
	}

	public boolean ischeck() // kiểm tra xem ô hiện tại có đang được kiểm tra hay không
	{
		return ischeck;
	}
}