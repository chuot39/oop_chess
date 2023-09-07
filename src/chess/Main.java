package chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pieces.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author mouseNGUYXN
 *
 */

/*
 * Mainclass của Project. GUI được khai báo, khởi tạo, sdung ở class này
 * Thừa kế JFrame, Java Swing
 */

public class Main extends JFrame implements MouseListener {
    private static final long serialVersionUID = 1L;

    // Khai báo biến
    private static final int Height = 700;
    private static final int Width = 1110;
    private static Rook wr01, wr02, br01, br02;
    private static Knight wk01, wk02, bk01, bk02;
    private static Bishop wb01, wb02, bb01, bb02;
    private static Pawn wp[], bp[];
    private static Queen wq, bq;
    private static King wk, bk;
    private Cell c, previous;
    private int chance = 0;
    private Cell boardState[][];
    private ArrayList<Cell> destinationlist = new ArrayList<Cell>();
    private Player White = null, Black = null;
    private JPanel fullBoard = new JPanel(new BorderLayout());
    private JPanel topBoard = new JPanel(new GridLayout(1, 8));
    private JPanel bottomBoard = new JPanel(new GridLayout(1, 8));
    private JPanel leftBoard = new JPanel(new GridLayout(8, 1));
    private JPanel rightBoard = new JPanel(new GridLayout(8, 1));
    private JPanel board = new JPanel(new GridLayout(8, 8));
    private JPanel wdetails = new JPanel(new GridLayout(3, 3));
    private JPanel bdetails = new JPanel(new GridLayout(3, 3));
    private JPanel wEats = new JPanel(new GridLayout(2, 8));
    private JPanel bEats = new JPanel(new GridLayout(2, 8));
    private int wEatIndex = 0;
    private int bEatIndex = 0;
    private JPanel wcombopanel = new JPanel();
    private JPanel bcombopanel = new JPanel();
    private JPanel controlPanel, WhitePlayer, BlackPlayer, temp, displayTime, showPlayer, configPanel;
    private JTextArea moves;
    private JSplitPane split;
    private JLabel label, mov;
    private static JLabel CHNC;
    private Time timer;
    public static Main Mainboard;
    private boolean selected = false, end = false;
    private Container content;
    private ArrayList<Player> wplayer, bplayer;
    private ArrayList<String> Wnames = new ArrayList<String>();
    private ArrayList<String> Bnames = new ArrayList<String>();
    private JComboBox<String> wcombo, bcombo;
    private String wname = null, bname = null, winner = null;
    static String move;
    private Player tempPlayer;
    private JScrollPane wscroll, bscroll;
    private String[] WNames = {}, BNames = {};
    private JSlider timeSlider;
    private BufferedImage image;
    private Button start, wselect, bselect, WNewPlayer, BNewPlayer;
    public static int timeRemaining = 60;

    public static void main(String[] args) {

        // Khởi tạo biến
        wr01 = new Rook("WR01", "img/White_Rook.png", 0);
        wr02 = new Rook("WR02", "img/White_Rook.png", 0);
        br01 = new Rook("BR01", "img/Black_Rook.png", 1);
        br02 = new Rook("BR02", "img/Black_Rook.png", 1);
        wk01 = new Knight("WK01", "img/White_Knight.png", 0);
        wk02 = new Knight("WK02", "img/White_Knight.png", 0);
        bk01 = new Knight("BK01", "img/Black_Knight.png", 1);
        bk02 = new Knight("BK02", "img/Black_Knight.png", 1);
        wb01 = new Bishop("WB01", "img/White_Bishop.png", 0);
        wb02 = new Bishop("WB02", "img/White_Bishop.png", 0);
        bb01 = new Bishop("BB01", "img/Black_Bishop.png", 1);
        bb02 = new Bishop("BB02", "img/Black_Bishop.png", 1);
        wq = new Queen("WQ", "img/White_Queen.png", 0);
        bq = new Queen("BQ", "img/Black_Queen.png", 1);
        wk = new King("WK", "img/White_King.png", 0, 7, 4);
        bk = new King("BK", "img/Black_King.png", 1, 0, 4);
        wp = new Pawn[8];
        bp = new Pawn[8];
        for (int i = 0; i < 8; i++) {
            wp[i] = new Pawn("WP0" + (i + 1), "img/White_Pawn.png", 0);
            bp[i] = new Pawn("BP0" + (i + 1), "img/Black_Pawn.png", 1);
        }

        // cài bảng main
        Mainboard = new Main();
        Mainboard.setVisible(true);
        Mainboard.setResizable(false);
    }

    // Constructor
    private Main() {
        timeRemaining = 60;
        timeSlider = new JSlider();
        move = "Trang";
        wname = null;
        bname = null;
        winner = null;
        fullBoard = new JPanel(new BorderLayout());
        board = new JPanel(new GridLayout(8, 8));
        wdetails = new JPanel(new GridLayout(3, 3));
        bdetails = new JPanel(new GridLayout(3, 3));
        bcombopanel = new JPanel();
        wcombopanel = new JPanel();
        Wnames = new ArrayList<String>();
        Bnames = new ArrayList<String>();
        ImageIcon img = new ImageIcon(this.getClass().getResource("img/Logo.png"));
        this.setIconImage(img.getImage());

        // tính giờ
        timeSlider.setMinimum(1);
        timeSlider.setMaximum(15);
        timeSlider.setValue(1);
        timeSlider.setMajorTickSpacing(2);
        timeSlider.setPaintLabels(true);
        timeSlider.setPaintTicks(true);
        timeSlider.addChangeListener(new TimeChange());

        // ghi TT ng chơi
        wplayer = Player.fetch_players();
        Iterator<Player> witr = wplayer.iterator();
        while (witr.hasNext())
            Wnames.add(witr.next().name());

        bplayer = Player.fetch_players();
        Iterator<Player> bitr = bplayer.iterator();
        while (bitr.hasNext())
            Bnames.add(bitr.next().name());
        WNames = Wnames.toArray(WNames);
        BNames = Bnames.toArray(BNames);

        Cell cell;
        board.setBorder(BorderFactory.createLoweredBevelBorder());
        pieces.Piece P;
        content = getContentPane();
        setSize(Width, Height);
        setTitle("Chess");
        content.setBackground(Color.black);
        controlPanel = new JPanel();
        content.setLayout(new BorderLayout());
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createTitledBorder(null, "Game co vua", TitledBorder.TOP,
                TitledBorder.CENTER, new Font("Tahomas", Font.PLAIN, 20), Color.PINK));

        // xác định TT ng chơi
        WhitePlayer = new JPanel();
        WhitePlayer.setBorder(BorderFactory.createTitledBorder(null, "Quan trang", TitledBorder.TOP,
                TitledBorder.CENTER, new Font("Tahomas", Font.BOLD, 18), Color.blue));
        WhitePlayer.setLayout(new BorderLayout());

        BlackPlayer = new JPanel();
        BlackPlayer.setBorder(BorderFactory.createTitledBorder(null, "Quan den", TitledBorder.TOP,
                TitledBorder.CENTER, new Font("Tahomas", Font.BOLD, 18), Color.blue));
        BlackPlayer.setLayout(new BorderLayout());

        JPanel whitestats = new JPanel(new GridLayout(3, 3));
        JPanel blackstats = new JPanel(new GridLayout(3, 3));
        wcombo = new JComboBox<String>(WNames);
        bcombo = new JComboBox<String>(BNames);
        wscroll = new JScrollPane(wcombo);
        bscroll = new JScrollPane(bcombo);
        wcombopanel.setLayout(new FlowLayout());
        bcombopanel.setLayout(new FlowLayout());
        wselect = new Button("Chon");
        bselect = new Button("Chon");
        wselect.addActionListener(new SelectHandler(0));
        bselect.addActionListener(new SelectHandler(1));
        WNewPlayer = new Button("Nguoi choi moi");
        BNewPlayer = new Button("Nguoi choi moi");
        WNewPlayer.addActionListener(new Handler(0));
        BNewPlayer.addActionListener(new Handler(1));
        wcombopanel.add(wscroll);
        wcombopanel.add(wselect);
        wcombopanel.add(WNewPlayer);
        bcombopanel.add(bscroll);
        bcombopanel.add(bselect);
        bcombopanel.add(BNewPlayer);
        WhitePlayer.add(wcombopanel, BorderLayout.NORTH);
        BlackPlayer.add(bcombopanel, BorderLayout.NORTH);
        whitestats.add(new JLabel("Ten:"));
        whitestats.add(new JLabel("Da choi:"));
        whitestats.add(new JLabel("Da thang:"));
        blackstats.add(new JLabel("Ten:"));
        blackstats.add(new JLabel("Da choi:"));
        blackstats.add(new JLabel("Thang:"));
        WhitePlayer.add(whitestats, BorderLayout.WEST);
        BlackPlayer.add(blackstats, BorderLayout.WEST);

        //lich su nuoc di
        wEats = new JPanel(new GridLayout(2, 8));
        wEats.setPreferredSize(new Dimension(240, 60));
        for (int i = 0; i < 16; i++) {
            JLabel label = new JLabel();
            label.setMinimumSize(new Dimension(30, 30));
            label.setPreferredSize(new Dimension(30, 30));
            label.setMaximumSize(new Dimension(30, 30));
            wEats.add(label);
        }
        WhitePlayer.add(wEats, BorderLayout.SOUTH);

        bEats = new JPanel(new GridLayout(2, 8));
        bEats.setPreferredSize(new Dimension(240, 60));
        for (int i = 0; i < 16; i++) {
            JLabel label = new JLabel();
            label.setMinimumSize(new Dimension(30, 30));
            label.setPreferredSize(new Dimension(30, 30));
            label.setMaximumSize(new Dimension(30, 30));
            bEats.add(label);
        }
        BlackPlayer.add(bEats, BorderLayout.SOUTH);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        controlPanel.add(WhitePlayer, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        controlPanel.add(BlackPlayer, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        moves = new JTextArea(100, 100);
        moves.setFont(new Font("Tahomas", Font.BOLD, 14));
        moves.setMinimumSize(new Dimension(130, 100));
        moves.setEditable(false);
        controlPanel.add(moves, gbc);

        // xác định vtrí quân cờ trên ô
        boardState = new Cell[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                P = null;
                if (i == 0 && j == 0)
                    P = br01;
                else if (i == 0 && j == 7)
                    P = br02;
                else if (i == 7 && j == 0)
                    P = wr01;
                else if (i == 7 && j == 7)
                    P = wr02;
                else if (i == 0 && j == 1)
                    P = bk01;
                else if (i == 0 && j == 6)
                    P = bk02;
                else if (i == 7 && j == 1)
                    P = wk01;
                else if (i == 7 && j == 6)
                    P = wk02;
                else if (i == 0 && j == 2)
                    P = bb01;
                else if (i == 0 && j == 5)
                    P = bb02;
                else if (i == 7 && j == 2)
                    P = wb01;
                else if (i == 7 && j == 5)
                    P = wb02;
                else if (i == 0 && j == 3)
                    P = bq;
                else if (i == 0 && j == 4)
                    P = bk;
                else if (i == 7 && j == 3)
                    P = wq;
                else if (i == 7 && j == 4)
                    P = wk;
                else if (i == 1)
                    P = bp[j];
                else if (i == 6)
                    P = wp[j];
                cell = new Cell(i, j, P);
                cell.addMouseListener(this);
                board.add(cell);
                boardState[i][j] = cell;
            }
        showPlayer = new JPanel(new FlowLayout());
        showPlayer.add(timeSlider);
        JLabel setTime = new JLabel("Thoi gian:");
        start = new Button("Bat dau");
        start.setBackground(Color.black);
        start.setForeground(Color.white);
        start.addActionListener(new START());
        start.setPreferredSize(new Dimension(120, 40));
        setTime.setFont(new Font("Tahomas", Font.BOLD, 16));
        label = new JLabel("Bat dau", JLabel.CENTER);
        label.setFont(new Font("Tahomas", Font.BOLD, 30));
        displayTime = new JPanel(new FlowLayout());

        configPanel = new JPanel(new GridLayout(3, 3));
        configPanel.add(setTime);
        configPanel.add(showPlayer);
        displayTime.add(start);
        configPanel.add(displayTime);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        controlPanel.add(configPanel, gbc);

        fullBoard.setMinimumSize(new Dimension(700, 700));
        bottomBoard = new JPanel(new GridLayout(1, 9));
        leftBoard = new JPanel(new GridLayout(8, 1));

        bottomBoard.setPreferredSize(new Dimension(700, 70));
        bottomBoard.add(new JLabel());
        for (int i = 65; i < 73; i++) {
            JLabel label = new JLabel(String.valueOf((char) i), SwingConstants.CENTER);
            label.setPreferredSize(new Dimension(100, 30));
            label.setFont(new Font("Tahomas", Font.BOLD, 22));
            bottomBoard.add(label);
        }

        leftBoard.setPreferredSize(new Dimension(77, 700));
        for (int i = 8; i > 0; i--) {
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            label.setPreferredSize(new Dimension(30, 100));
            label.setFont(new Font("Tahomas", Font.BOLD, 22));
            leftBoard.add(label);
        }

        topBoard.setMinimumSize(new Dimension(700, 50));
        rightBoard.setMinimumSize(new Dimension(50, 600));

        board.setMinimumSize(new Dimension(560, 560));

        fullBoard.add(board, BorderLayout.CENTER);
        fullBoard.add(topBoard, BorderLayout.NORTH);
        fullBoard.add(rightBoard, BorderLayout.EAST);
        fullBoard.add(bottomBoard, BorderLayout.SOUTH);
        fullBoard.add(leftBoard, BorderLayout.WEST);

        // hình ảnh bên trái ở menu main
        temp = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                try {
                    image = ImageIO.read(this.getClass().getResource("img/Logo.png"));
                } catch (IOException ex) {
                    System.out.println("Khong tim thay");
                }

                g.drawImage(image, 0, 0, null);
            }
        };

        temp.setMinimumSize(new Dimension(700, 700));
        controlPanel.setMinimumSize(new Dimension(255, 700));
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, temp, controlPanel);

        content.add(split);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Chuyển đổi giữa trắng và đen
    // Public vì truy cập trong Class time
    public void changechance() {
        if (boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck()) {
            chance ^= 1;
            gameend();
        }
        if (destinationlist.isEmpty() == false)
            cleandestinations(destinationlist);
        if (previous != null)
            previous.deselect();
        previous = null;
        chance ^= 1;
        if (!end && timer != null) {
            timer.reset();
            timer.start();
            showPlayer.remove(CHNC);
            if (Main.move == "Den")
                Main.move = "Trang";
            else
                Main.move = "Den";
            CHNC.setText(Main.move);
            showPlayer.add(CHNC);
        }
    }

    // Lấy vua đen hoặc trắng
    private King getKing(int color) {
        if (color == 0)
            return wk;
        else
            return bk;
    }

    // làm sạch các điểm nổi bật của các ô đích có thể
    private void cleandestinations(ArrayList<Cell> destlist) // xóa điểm đến của nước đi
    {
        ListIterator<Cell> it = destlist.listIterator();
        while (it.hasNext())
            it.next().removepossibledestination();
    }

    // Các ô có thể di chuyển
    private void highlightdestinations(ArrayList<Cell> destlist) {
        ListIterator<Cell> it = destlist.listIterator();
        while (it.hasNext())
            it.next().setpossibledestination();
    }

    // kiểm tra xem vua có chiếu nếu nước đi đã đi được thực hiện
    private boolean willkingbeindanger(Cell fromcell, Cell tocell) {
        Cell newboardstate[][] = new Cell[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                try {
                    newboardstate[i][j] = new Cell(boardState[i][j]);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    System.out.println("There is a problem with cloning !!");
                }
            }

        if (newboardstate[tocell.x][tocell.y].getpiece() != null)
            newboardstate[tocell.x][tocell.y].removePiece();

        newboardstate[tocell.x][tocell.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
        if (newboardstate[tocell.x][tocell.y].getpiece() instanceof King) {
            ((King) (newboardstate[tocell.x][tocell.y].getpiece())).setx(tocell.x);
            ((King) (newboardstate[tocell.x][tocell.y].getpiece())).sety(tocell.y);
        }
        newboardstate[fromcell.x][fromcell.y].removePiece();
        if (((King) (newboardstate[getKing(chance).getx()][getKing(chance).gety()].getpiece()))
                .isindanger(newboardstate) == true)
            return true;
        else
            return false;
    }

    // loại bỏ các nước đi có thể khiến Vua bị chiếu
    private ArrayList<Cell> filterdestination(ArrayList<Cell> destlist, Cell fromcell) {
        ArrayList<Cell> newlist = new ArrayList<Cell>();
        Cell newboardstate[][] = new Cell[8][8];
        ListIterator<Cell> it = destlist.listIterator();
        int x, y;
        while (it.hasNext()) {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++) {
                    try {
                        newboardstate[i][j] = new Cell(boardState[i][j]);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }

            Cell tempc = it.next();

            if (newboardstate[tempc.x][tempc.y].getpiece() != null)
                newboardstate[tempc.x][tempc.y].removePiece();
            newboardstate[tempc.x][tempc.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
            x = getKing(chance).getx();
            y = getKing(chance).gety();
            if (newboardstate[fromcell.x][fromcell.y].getpiece() instanceof King) {
                ((King) (newboardstate[tempc.x][tempc.y].getpiece())).setx(tempc.x);
                ((King) (newboardstate[tempc.x][tempc.y].getpiece())).sety(tempc.y);
                x = tempc.x;
                y = tempc.y;
            }
            newboardstate[fromcell.x][fromcell.y].removePiece();
            if ((((King) (newboardstate[x][y].getpiece())).isindanger(newboardstate) == false))
                newlist.add(tempc);
        }
        return newlist;
    }

    // lọc các nước đi có thể xảy ra khi vua đang bị chiếu
    private ArrayList<Cell> incheckfilter(ArrayList<Cell> destlist, Cell fromcell, int color) {
        ArrayList<Cell> newlist = new ArrayList<Cell>();
        Cell newboardstate[][] = new Cell[8][8];
        ListIterator<Cell> it = destlist.listIterator();
        int x, y;
        while (it.hasNext()) {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++) {
                    try {
                        newboardstate[i][j] = new Cell(boardState[i][j]);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            Cell tempc = it.next();
            if (newboardstate[tempc.x][tempc.y].getpiece() != null)
                newboardstate[tempc.x][tempc.y].removePiece();
            newboardstate[tempc.x][tempc.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
            x = getKing(color).getx();
            y = getKing(color).gety();
            if (newboardstate[tempc.x][tempc.y].getpiece() instanceof King) {
                ((King) (newboardstate[tempc.x][tempc.y].getpiece())).setx(tempc.x);
                ((King) (newboardstate[tempc.x][tempc.y].getpiece())).sety(tempc.y);
                x = tempc.x;
                y = tempc.y;
            }
            newboardstate[fromcell.x][fromcell.y].removePiece();
            if ((((King) (newboardstate[x][y].getpiece())).isindanger(newboardstate) == false))
                newlist.add(tempc);
        }
        return newlist;
    }

    // Kiểm tra chiếu tướng. Game end nếu trả về true
    public boolean checkmate(int color) {
        ArrayList<Cell> dlist = new ArrayList<Cell>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardState[i][j].getpiece() != null && boardState[i][j].getpiece().getcolor() == color) {
                    dlist.clear();
                    dlist = boardState[i][j].getpiece().move(boardState, i, j);
                    dlist = incheckfilter(dlist, boardState[i][j], color);
                    if (dlist.size() != 0)
                        return false;
                }
            }
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    private void gameend() {
        cleandestinations(destinationlist);
        displayTime.disable();
        timer.countdownTimer.stop();
        if (previous != null)
            previous.removePiece();
        if (chance == 0) {
            White.updateGamesWon();
            White.Update_Player();
            winner = White.name();
        } else {
            Black.updateGamesWon();
            Black.Update_Player();
            winner = Black.name();
        }
        JOptionPane.showMessageDialog(board, "Chieu het\n" + winner + " thang");
        WhitePlayer.remove(wdetails);
        BlackPlayer.remove(bdetails);
        displayTime.remove(label);

        displayTime.add(start);
        showPlayer.remove(mov);
        showPlayer.remove(CHNC);
        showPlayer.revalidate();
        showPlayer.add(timeSlider);

        wEatIndex = 0;
        wEats.removeAll();
        for (int i = 0; i < 16; i++) {
            JLabel label = new JLabel();
            label.setMinimumSize(new Dimension(30, 30));
            label.setPreferredSize(new Dimension(30, 30));
            label.setMaximumSize(new Dimension(30, 30));
            wEats.add(label);
        }

        bEatIndex = 0;
        bEats.removeAll();
        for (int i = 0; i < 16; i++) {
            JLabel label = new JLabel();
            label.setMinimumSize(new Dimension(30, 30));
            label.setPreferredSize(new Dimension(30, 30));
            label.setMaximumSize(new Dimension(30, 30));
            bEats.add(label);
        }

        split.remove(fullBoard);
        split.add(temp);
        WNewPlayer.enable();
        BNewPlayer.enable();
        wselect.enable();
        bselect.enable();
        end = true;
        Mainboard.disable();
        Mainboard.dispose();
        Mainboard = new Main();
        Mainboard.setVisible(true);
        Mainboard.setResizable(false);
    }

    // hàm trừu tượng của lớp cha.
    // Chức năng khi nhấp chuột
    // được gọi khi người dùng nhấp vào một ô cụ thể
    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-phương thức được khởi tạo tự động
        c = (Cell) arg0.getSource();
        if (previous == null) {
            if (c.getpiece() != null) {
                if (c.getpiece().getcolor() != chance)
                    return;
                c.select();
                previous = c;
                destinationlist.clear();
                if (c.getpiece() instanceof King) {
                    destinationlist = ((King) c.getpiece()).move(boardState, c.x, c.y,
                            boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck());
                    destinationlist = filterdestination(destinationlist, c);
                } else {
                    destinationlist = c.getpiece().move(boardState, c.x, c.y);
                    if (boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck())
                        destinationlist = new ArrayList<Cell>(filterdestination(destinationlist, c));
                    else if (destinationlist.isEmpty() == false && willkingbeindanger(c, destinationlist.get(0)))
                        destinationlist.clear();
                }
                highlightdestinations(destinationlist);
            }
        } else {
            if (c.x == previous.x && c.y == previous.y) {
                c.deselect();
                cleandestinations(destinationlist);
                destinationlist.clear();
                previous = null;
            } else if (c.getpiece() == null || previous.getpiece().getcolor() != c.getpiece().getcolor()) {
                if (c.ispossibledestination()) {
                    Piece piece = c.getpiece();
                    if (c.getpiece() != null)
                        c.removePiece();
                    c.setPiece(previous.getpiece());
                    if (previous.ischeck())
                        previous.removecheck();
                    previous.removePiece();
                    if (getKing(chance ^ 1).isindanger(boardState)) {
                        boardState[getKing(chance ^ 1).getx()][getKing(chance ^ 1).gety()].setcheck();
                        if (checkmate(getKing(chance ^ 1).getcolor())) {
                            previous.deselect();
                            if (previous.getpiece() != null)
                                previous.removePiece();
                            gameend();
                        }
                    }
                    if (getKing(chance).isindanger(boardState) == false)
                        boardState[getKing(chance).getx()][getKing(chance).gety()].removecheck();
                    if (c.getpiece() instanceof King) {
                        King k = ((King) c.getpiece());
                        if (k.isFirstMove()) {
                            if (c.x == 7 && c.y == 2) {
                                boardState[7][3].setPiece(boardState[7][0].getpiece());
                                boardState[7][0].removePiece();
                                boardState[7][0].repaint();

                                moves.append(getLocation(7, 0) + " > " + getLocation(7, 3) + ",");
                            } else if (c.x == 7 && c.y == 6) {
                                boardState[7][5].setPiece(boardState[7][7].getpiece());
                                boardState[7][7].removePiece();
                                boardState[7][7].repaint();

                                moves.append(getLocation(7, 7) + " > " + getLocation(7, 5) + ",");
                            } else if (c.x == 0 && c.y == 2) {
                                boardState[0][3].setPiece(boardState[0][0].getpiece());
                                boardState[0][0].removePiece();
                                boardState[0][0].repaint();

                                moves.append(getLocation(0, 0) + " > " + getLocation(0, 3) + ",");
                            } else if (c.x == 0 && c.y == 6) {
                                boardState[0][5].setPiece(boardState[0][7].getpiece());
                                boardState[0][7].removePiece();
                                boardState[0][7].repaint();

                                moves.append(getLocation(0, 7) + " > " + getLocation(0, 5) + ",");
                            }

                        }

                        k.setx(c.x);
                        k.sety(c.y);
                        k.setFirstMove(false);
                    }

                    moves.append(getLocation(previous.x, previous.y) + " > " + getLocation(c.x, c.y) + "\n");

                    if (c.getpiece() instanceof Rook) {
                        ((Rook) c.getpiece()).setFirstMove(false);
                    }

                    if (piece != null) {
                        if (piece.getcolor() == 0) {
                            if (bEats.getComponents()[bEatIndex] instanceof JLabel) {
                                ((JLabel) bEats.getComponents()[bEatIndex]).setIcon(
                                        new ImageIcon(new ImageIcon(this.getClass().getResource(piece.getPath()))
                                                .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
                                bEatIndex++;
                            }
                        } else {
                            if (wEats.getComponents()[wEatIndex] instanceof JLabel) {
                                ((JLabel) wEats.getComponents()[wEatIndex]).setIcon(
                                        new ImageIcon(new ImageIcon(this.getClass().getResource(piece.getPath()))
                                                .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
                                wEatIndex++;
                            }
                        }
                    }

                    if (c.getpiece() instanceof Pawn && (c.x == 0 || c.x == 7)) {
                        String result = null;
                        while (result == null) {
                            UnderpromotionDialog dialog = new UnderpromotionDialog(Main.this);
                            result = dialog.run();
                        }

                        if (c.getpiece().getcolor() == 0) {
                            if (result.equalsIgnoreCase("Xe")) {
                                c.setPiece(new Rook(result, "img/White_Rook.png", 0));
                            } else if (result.equalsIgnoreCase("Tuong")) {
                                c.setPiece(new Bishop(result, "img/White_Bishop.png", 0));
                            } else if (result.equalsIgnoreCase("Ma")) {
                                c.setPiece(new Knight(result, "img/White_Knight.png", 0));
                            } else {
                                c.setPiece(new Queen(result, "img/White_Queen.png", 0));
                            }
                        } else {
                            if (result.equalsIgnoreCase("Xe")) {
                                c.setPiece(new Rook(result, "img/Black_Rook.png", 1));
                            } else if (result.equalsIgnoreCase("Tuong")) {
                                c.setPiece(new Bishop(result, "img/Black_Bishop.png", 1));
                            } else if (result.equalsIgnoreCase("Ma")) {
                                c.setPiece(new Knight(result, "img/Black_Knight.png", 1));
                            } else {
                                c.setPiece(new Queen(result, "img/Black_Queen.png", 1));
                            }
                        }
                    }

                    changechance();
                    if (!end) {
                        timer.reset();
                        timer.start();
                    }
                }
                if (previous != null) {
                    previous.deselect();
                    previous = null;
                }
                cleandestinations(destinationlist);
                destinationlist.clear();
            } else if (previous.getpiece().getcolor() == c.getpiece().getcolor()) {
                previous.deselect();
                cleandestinations(destinationlist);
                destinationlist.clear();
                c.select();
                previous = c;
                if (c.getpiece() instanceof King) {
                    destinationlist = ((King) c.getpiece()).move(boardState, c.x, c.y,
                            boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck());
                    destinationlist = filterdestination(destinationlist, c);
                } else {
                    destinationlist = c.getpiece().move(boardState, c.x, c.y);
                    if (boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck()) {
                        destinationlist = new ArrayList<Cell>(filterdestination(destinationlist, c));
                    } else if (destinationlist.isEmpty() == false && willkingbeindanger(c, destinationlist.get(0))) {
                        destinationlist.clear();
                    }
                }
                highlightdestinations(destinationlist);
            }
        }
        if (c.getpiece() != null && c.getpiece() instanceof King) {
            ((King) c.getpiece()).setx(c.x);
            ((King) c.getpiece()).sety(c.y);
        }
    }

    // Hàm trừu tượng không liên quan khác. Chỉ có thao tác nhấp chuột được ghi lại.
    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    private String getLocation(int x, int y) {
        return String.valueOf((char) ('A' + y)) + ":" + (8 - x);
    }

    class START implements ActionListener {

        @SuppressWarnings("deprecation")
        @Override
        public void actionPerformed(ActionEvent arg0) {

            if (White == null || Black == null) {
                JOptionPane.showMessageDialog(controlPanel, "Chon nguoi choi");
                return;
            }

            White.updateGamesPlayed();
            White.Update_Player();
            Black.updateGamesPlayed();
            Black.Update_Player();
            WNewPlayer.disable();
            BNewPlayer.disable();
            wselect.disable();
            bselect.disable();
            split.remove(temp);
            split.add(fullBoard);

            showPlayer.remove(timeSlider);
            mov = new JLabel("Luot:");
            mov.setFont(new Font("Tahomas", Font.PLAIN, 20));
            mov.setForeground(Color.red);
            showPlayer.add(mov);
            CHNC = new JLabel(move);
            CHNC.setFont(new Font("Tahomas", Font.BOLD, 20));
            CHNC.setForeground(Color.blue);
            showPlayer.add(CHNC);
            displayTime.remove(start);
            displayTime.add(label);
            timer = new Time(label);
            timer.start();
        }
    }

    class TimeChange implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent arg0) {
            timeRemaining = timeSlider.getValue() * 60;
        }
    }

    class SelectHandler implements ActionListener {
        private int color;

        SelectHandler(int i) {
            color = i;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {

            tempPlayer = null;
            String n = (color == 0) ? wname : bname;
            JComboBox<String> jc = (color == 0) ? wcombo : bcombo;
            JComboBox<String> ojc = (color == 0) ? bcombo : wcombo;
            ArrayList<Player> pl = (color == 0) ? wplayer : bplayer;
            // ArrayList<Player> otherPlayer=(color==0)?bplayer:wplayer;
            ArrayList<Player> opl = Player.fetch_players();
            if (opl.isEmpty())
                return;
            JPanel det = (color == 0) ? wdetails : bdetails;
            JPanel PL = (color == 0) ? WhitePlayer : BlackPlayer;
            if (selected == true)
                det.removeAll();
            n = (String) jc.getSelectedItem();
            Iterator<Player> it = pl.iterator();
            Iterator<Player> oit = opl.iterator();
            while (it.hasNext()) {
                Player p = it.next();
                if (p.name().equals(n)) {
                    tempPlayer = p;
                    break;
                }
            }
            while (oit.hasNext()) {
                Player p = oit.next();
                if (p.name().equals(n)) {
                    opl.remove(p);
                    break;
                }
            }

            if (tempPlayer == null)
                return;
            if (color == 0)
                White = tempPlayer;
            else
                Black = tempPlayer;
            bplayer = opl;
            ojc.removeAllItems();
            for (Player s : opl)
                ojc.addItem(s.name());
            det.add(new JLabel(" " + tempPlayer.name()));
            det.add(new JLabel(" " + tempPlayer.gamesplayed()));
            det.add(new JLabel(" " + tempPlayer.gameswon()));

            PL.revalidate();
            PL.repaint();
            PL.add(det);
            selected = true;
        }

    }

    class Handler implements ActionListener {
        private int color;

        Handler(int i) {
            color = i;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String n = (color == 0) ? wname : bname;
            JPanel j = (color == 0) ? WhitePlayer : BlackPlayer;
            ArrayList<Player> N = Player.fetch_players();
            Iterator<Player> it = N.iterator();
            JPanel det = (color == 0) ? wdetails : bdetails;
            n = JOptionPane.showInputDialog(j, "Nhap ten");

            if (n != null) {

                while (it.hasNext()) {
                    if (it.next().name().equals(n)) {
                        JOptionPane.showMessageDialog(j, "Nguoi choi bi trung");
                        return;
                    }
                }

                if (n.length() != 0) {
                    Player tem = new Player(n);
                    tem.Update_Player();
                    if (color == 0)
                        White = tem;
                    else
                        Black = tem;
                } else
                    return;
            } else
                return;
            det.removeAll();
            det.add(new JLabel(" " + n));
            det.add(new JLabel(" 0"));
            det.add(new JLabel(" 0"));
            j.revalidate();
            j.repaint();
            j.add(det);
            selected = true;
        }
    }
}