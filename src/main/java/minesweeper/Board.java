package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Board {
    private Cell[][] cells;
    private final int HEIGHT, WIDTH, BOMBCOUNT = 42;
    private JFrame frame;
    private TopPanel top;
    private GridBagConstraints c = new GridBagConstraints();

    public Board() {
        HEIGHT = 14;
        WIDTH = 18;

        frame = new JFrame("Minesweeper      (;");
        top = new TopPanel(this);

        frameSet();
    }

    public void frameSet() {
        frame.setSize(754, 628);
        frame.setLayout(new GridBagLayout());

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        frame.add(top, c);

        c.gridy = 1;
        frame.add(addCells(), c);

        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void start() {
        plantMines();
        updateBoard();
    }

    public JPanel addCells() {
        JPanel panel = new JPanel(new GridLayout(HEIGHT,WIDTH, 2 , 2));
        panel.setSize(754, 586);
        panel.setBackground(Color.GRAY);

        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        cells = new Cell[HEIGHT][WIDTH];
        for(int i = 0; i< HEIGHT; i++){
            for(int j = 0; j< WIDTH; j++){
                cells[i][j] = new Cell(this, i, j, top);
                panel.add(cells[i][j].getButton());
            }
        }
        return panel;
    }

    public void plantMines() {
        Random random = new Random();
        int counter = 0;
        while (counter != BOMBCOUNT) {
            counter += cells[random.nextInt(HEIGHT)][random.nextInt(WIDTH)].setMine();
        }
    }

    public void updateBoard() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (!cells[row][col].isMine()) {
                    int nearby = 0;

                    if((row + 1) < HEIGHT && cells[row + 1][col].isMine())
                        nearby++;
                    if((col + 1) < WIDTH && cells[row][col + 1].isMine())
                        nearby++;
                    if((row - 1) > -1 && cells[row - 1][col].isMine())
                        nearby++;
                    if((col - 1) > -1 && cells[row][col - 1].isMine())
                        nearby++;

                    if((row + 1) < HEIGHT && (col + 1) < WIDTH && cells[row + 1][col + 1].isMine())
                        nearby++;
                    if((row - 1) > -1 && (col - 1) > -1 && cells[row - 1][col - 1].isMine())
                        nearby++;
                    if((row + 1) < HEIGHT && (col - 1) > -1 && cells[row + 1][col - 1].isMine())
                        nearby++;
                    if((row - 1) > -1 && (col + 1) < WIDTH && cells[row - 1][col + 1].isMine())
                        nearby++;

                    cells[row][col].setValue(nearby);
                }
            }
        }
    }

    public void floodFill(int y, int x) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT || cells[y][x].flagged || cells[y][x].revealed)
            return;

        cells[y][x].reveal();

        if (x+1 < WIDTH)
            cells[y][x+1].displayValue();
        if (x-1 >= 0)
            cells[y][x-1].displayValue();
        if (y+1 < HEIGHT)
            cells[y+1][x].displayValue();
        if (y-1 >= 0)
            cells[y-1][x].displayValue();

        if (y+1 < HEIGHT && x+1 < WIDTH)
            cells[y+1][x+1].displayValue();
        if (y-1 >= 0 && x-1 >= 0)
            cells[y-1][x-1].displayValue();
        if (y+1 < HEIGHT && x-1 >= 0)
            cells[y+1][x-1].displayValue();
        if (y-1 >= 0 && x+1 < WIDTH)
            cells[y-1][x+1].displayValue();

    }

    public void checkWin() {
        int hidden = 0;

        for(Cell[] row: cells) {
            for(Cell element: row) {
                if(!element.revealed)
                    hidden++;
            }
        }

        if(hidden == BOMBCOUNT)
            for (Cell[] row: cells) {
                for (Cell c: row) {
                    c.win();
                }
            }
        else
            return;
    }

    public void fail() {
        for (Cell[] row: cells) {
            for (Cell c: row) {
                c.fail();
            }
        }
    }

    public void reActivate() {
        for(Cell[] row: cells) {
            for(Cell element: row) {
                element.turnOff();
            }
        }
    }
}
