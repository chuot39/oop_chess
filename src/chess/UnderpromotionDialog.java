package chess;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class UnderpromotionDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 4118010399990219930L;

    private JComboBox<String> pieceList;
    private JButton btnOk;
    private String result;

    public UnderpromotionDialog(Frame parent) {
        super(parent, "Phong cap", true);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        result = "Hau";
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel colorLabel = new JLabel("Chon quan:");
        colorLabel.setFont(new Font("Tahomas", Font.PLAIN, 16));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(colorLabel, gbc);

        String[] pieceStrings = { "Hau", "Xe", "Tuong", "Ma" };
        pieceList = new JComboBox<String>(pieceStrings);
        pieceList.setFont(new Font("Tahomas", Font.PLAIN, 16));
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(pieceList, gbc);

        btnOk = new JButton("Ok");
        btnOk.setPreferredSize(new Dimension(100, 30));
        btnOk.addActionListener(this);
        btnOk.setFont(new Font("Tahomas", Font.PLAIN, 16));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(btnOk, gbc);

        getContentPane().add(panel);
        pack();
    }

    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == btnOk) {
            result = (String) pieceList.getSelectedItem();
            dispose();
        }
    }

    public String run() {
        this.setVisible(true);
        return result;
    }
}
