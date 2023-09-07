package chess;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/*  Lớp player theo dõi tất cả người dùng
    Các đối tượng được cập nhật vào file "data_doan_chess.dat" sau mỗi game
 */
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private Integer gamesplayed;
    private Integer gameswon;

    // Constructor
    public Player(String name) {
        this.name = name.trim();
        // this.lname = lname.trim();
        gamesplayed = Integer.valueOf(0);
        gameswon = Integer.valueOf(0);
    }

    // Name Getter
    public String name() {
        return name;
    }

    // Returns the number of games played-Trả về số game đã chơi
    public Integer gamesplayed() {
        return gamesplayed;
    }

    // Returns the number of games won -Trả về số ván thắng
    public Integer gameswon() {
        return gameswon;
    }

    // Calculates the win percentage of the player -Tính phần trăm thắng của người
    // chơi
    public Integer winpercent() {
        return Integer.valueOf((gameswon * 100) / gamesplayed);
    }

    // Increments the number of games played - Tăng số game đã chơi
    public void updateGamesPlayed() {
        gamesplayed++;
    }

    // Increments the number of games won - Tăng số lượng game đã thắng
    public void updateGamesWon() {
        gameswon++;
    }

    public static ArrayList<Player> fetch_players() // Function to fetch the list of the players -Chức năng lấy danh
                                                    // sách người chơi
    {
        Player tempplayer;
        ObjectInputStream input = null;
        ArrayList<Player> players = new ArrayList<Player>();
        try {
            File infile = new File(System.getProperty("user.dir") + File.separator + "data_doan_chess.dat");
            input = new ObjectInputStream(new FileInputStream(infile));
            try {
                while (true) {
                    tempplayer = (Player) input.readObject();
                    players.add(tempplayer);
                }
            } catch (EOFException e) {
                input.close();
            }
        } catch (FileNotFoundException e) {
            players.clear();
            return players;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                input.close();
            } catch (IOException e1) {
            }
            JOptionPane.showMessageDialog(null, "Khong the doc file game!!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tep bi hong! Chon Ok de tao tep moi");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return players;
    }

    public void Update_Player() // Function to update the statistics of a player - Chức năng cập nhật số liệu
                                // thống kê của một người chơi
    {
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        Player temp_player;
        File inputfile = null;
        File outputfile = null;
        try {
            inputfile = new File(System.getProperty("user.dir") + File.separator + "data_doan_chess.dat");
            outputfile = new File(System.getProperty("user.dir") + File.separator + "tempfile.dat");
        } catch (SecurityException e) {
            JOptionPane.showMessageDialog(null, "Quyen bi tu choi! Khong the bat dau");
            System.exit(0);
        }
        boolean playerdonotexist;
        try {
            if (outputfile.exists() == false)
                outputfile.createNewFile();
            if (inputfile.exists() == false) {
                output = new ObjectOutputStream(new java.io.FileOutputStream(outputfile, true));
                output.writeObject(this);
            } else {
                input = new ObjectInputStream(new FileInputStream(inputfile));
                output = new ObjectOutputStream(new FileOutputStream(outputfile));
                playerdonotexist = true;
                try {
                    while (true) {
                        temp_player = (Player) input.readObject();
                        if (temp_player.name().equals(name())) {
                            output.writeObject(this);
                            playerdonotexist = false;
                        } else
                            output.writeObject(temp_player);
                    }
                } catch (EOFException e) {
                    input.close();
                }
                if (playerdonotexist)
                    output.writeObject(this);
            }
            inputfile.delete();
            output.close();
            File newf = new File(System.getProperty("user.dir") + File.separator + "data_doan_chess.dat");
            if (outputfile.renameTo(newf) == false)
                System.out.println("Doi ten khong thanh cong");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Khong the doc/ghi file game. Nhan Ok de tiep tuc");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tep bi hong! Chon Ok de tao tep moi");
        } catch (Exception e) {

        }
    }
}
