package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.UIManager;

public class RestartButton implements ActionListener {

    private JButton button;
    private Board board;
    private TopPanel tp;

    public RestartButton(Board board, TopPanel tp) {
        this.tp = tp;
        button = new JButton();
        button.addActionListener(this);

        button.setPreferredSize(new Dimension(82,40));
        button.setMargin(null);
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);

        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBackground(new Color(215, 215, 215));
        button.setText("<html><font color='rgb(65,120,65)'>Restart</font></html>");

        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.board = board;
    }

    public JButton getButton() {
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        board.reActivate();
        tp.stopTimer();
        tp.updateFlag(42);
    }
}
