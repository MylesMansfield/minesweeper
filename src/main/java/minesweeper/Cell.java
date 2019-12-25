package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cell extends MouseAdapter {

    private final int X, Y;
    public static int FIRSTX, FIRSTY;
    private JButton btn;
    private Board board;
    private int value;
    public boolean revealed = false;
    public boolean flagged = false;
    private boolean on = true;
    private boolean isBomb = false;
    public static boolean firstClick = true;
    private static int flagCount = 42;
    private TopPanel tp;

    public Cell(Board board, int y, int x, TopPanel tp) {
        this.tp = tp;
        X = x;
        Y = y;
        this.board = board;
        btn = new JButton();
        btn.addMouseListener(this);

        btn.setPreferredSize(new Dimension(40,40));
        btn.setMargin(null);  //new Insets(0,0,10,0));
        btn.setBorder(null);
        btn.setFocusPainted(false);

        btn.setBackground(new Color(215, 215, 215));
        btn.setFont(new Font("Arial", Font.PLAIN, 40));
    }

    public JButton getButton() {
        return btn;
    }

    public void setValue(int value) {
        this.value  = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isMine() {
        return isBomb;
    }

    public int setMine() {
        if (value == -1)
            return 0;
        else if (Y == FIRSTY && X == FIRSTX) {
            return 0;
        }
        else if (Y == FIRSTY+1 && X == FIRSTX) {
            return 0;
        }
        else if (Y == FIRSTY && X == FIRSTX+1) {
            return 0;
        }
        else if (Y == FIRSTY-1 && X == FIRSTX) {
            return 0;
        }
        else if (Y == FIRSTY && X == FIRSTX-1) {
            return 0;
        }
        else if (Y == FIRSTY+1 && X == FIRSTX+1) {
            return 0;
        }
        else if (Y == FIRSTY-1 && X == FIRSTX-1) {
            return 0;
        }
        else if (Y == FIRSTY+1 && X == FIRSTX-1) {
            return 0;
        }
        else if (Y == FIRSTY-1 && X == FIRSTX+1) {
            return 0;
        }
        else
            value = -1;
        isBomb = true;
        return 1;
    }

    public void reveal() {
        revealed = true;
    }

    public void displayValue() {
        btn.setBackground(new Color(150, 150, 150));

        switch (value) {
            case 0:
                board.floodFill(Y, X);
                break;
            case 1:
                btn.setText("<html><font color='rgb(0,0,200)'>1</font></html>");
                revealed = true;
                break;
            case 2:
                btn.setText("<html><font color='rgb(0,150,0)'>2</font></html>");
                revealed = true;
                break;
            case 3:
                btn.setText("<html><font color='rgb(180,0,0)'>3</font></html>");
                revealed = true;
                break;
            case 4:
                btn.setText("<html><font color='rgb(0,0,100)'>4</font></html>");
                revealed = true;
                break;
            case 5:
                btn.setText("<html><font color='rgb(100,0,0)'>5</font></html>");
                revealed = true;
                break;
            case 6:
                btn.setText("<html><font color='rgb(0,75,130)'>6</font></html>");
                revealed = true;
                break;
            case 7:
                btn.setText("<html><font color='rgb(25,25,25)'>7</font></html>");
                revealed = true;
                break;
            case 8:
                btn.setText("<html><font color='rgb(200,200,200)'>8</font></html>");
                revealed = true;
                break;
        }
    }

    private void flag() {
        if (!firstClick) {
            if (flagged) {
                flagCount++;
                flagged = false;
                btn.setText("");
            }
            else if (flagCount > 0) {
                flagCount--;
                flagged = true;
                btn.setText("<html><font color='rgb(179, 83, 0)'>F</font></html>");
            }
        }
    }

    public void turnOff() {
        on = false;

        revealed = false;
        flagged = false;
        on = true;
        isBomb = false;
        firstClick = true;
        flagCount = 42;

        value = 0;

        btn.setBackground(new Color(215, 215, 215));
        btn.setText("");
    }

    public void fail() {
        on = false;
        tp.stopTimer();

        if (flagged && !(value == -1)) {
            btn.setBackground(new Color(255, 255, 170));
        }
        if (flagged && value == -1) {
            btn.setBackground(new Color(220, 255, 220));
        }
        else if(value == -1) {
            btn.setText("B");
        }
    }

    public void win() {
        on = false;
        tp.stopTimer();

        if (flagged && value == -1) {
            btn.setBackground(new Color(220, 255, 220));
        }
        else if(value == -1) {
            btn.setBackground(new Color(220, 255, 220));
            btn.setText("B");
        }
    }

    public void mouseClicked (MouseEvent e) {
        if (on) {
            if (e.getModifiers() == MouseEvent.BUTTON3_MASK){
                if (!revealed) {
                    this.flag();
                    tp.updateFlag(flagCount);
                }
            }
            else {
                if (!flagged && !revealed)
                    if (firstClick) {
                        firstClick = false;
                        FIRSTY = Y;
                        FIRSTX = X;
                        board.start();
                        this.displayValue();
                        tp.startTimer();
                    }
                    else if (isBomb) {
                        revealed = true;

                        btn.setText("B");
                        btn.setBackground(new Color(255, 150, 150));
                        board.fail();
                    }
                    else {
                        this.displayValue();

                        board.checkWin();
                    }
            }
        }
    }

}
