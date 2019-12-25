package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {

    private JLabel jl1;
    private JLabel jl2;
    private Timer timer;
    private GridBagConstraints c = new GridBagConstraints();
    private long lastTickTime;

    public TopPanel(Board board) {
        this.setBackground(new Color(100, 100, 100));
        this.setLayout(new GridBagLayout());

        RestartButton rb = new RestartButton(board, this);
        c.insets = new Insets(4,0,4,0);
        c.weightx = 10;
        c.gridx = 0;
        c.gridy = 0;
        this.add(rb.getButton(), c);

        jl1 = new JLabel(String.format("%02d:%02d", 0, 0, 0));
        jl1.setPreferredSize(new Dimension(82,40));
        jl1.setBorder(null);
        jl1.setOpaque(true);
        jl1.setFont(new Font("Arial", Font.PLAIN, 20));
        jl1.setBackground(new Color(215, 215, 215));
        jl1.setHorizontalAlignment(JLabel.CENTER);
        jl1.setVerticalAlignment(JLabel.CENTER);

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long runningTime = 0;
                runningTime = System.currentTimeMillis() - lastTickTime;
                Duration duration = Duration.ofMillis(runningTime);
                long hours = duration.toHours();
                duration = duration.minusHours(hours);
                long minutes = duration.toMinutes();
                duration = duration.minusMinutes(minutes);
                long millis = duration.toMillis();
                long seconds = millis / 1000;
                millis -= (seconds * 1000);
                jl1.setText(String.format("%02d:%02d", minutes, seconds));
            }
        };

        timer = new Timer(1000, listener);

        c.gridx = 1;
        this.add(jl1, c);

        jl2 = new JLabel();
        jl2.setPreferredSize(new Dimension(100,40));
        jl2.setBorder(null);
        jl2.setOpaque(true);
        jl2.setFont(new Font("Arial", Font.PLAIN, 20));
        jl2.setBackground(new Color(215, 215, 215));
        jl2.setText("<html><font color='rgb(179, 83, 0)'>Flag(s) 42</font></html>");
        jl2.setHorizontalAlignment(JLabel.CENTER);
        jl2.setVerticalAlignment(JLabel.CENTER);
        c.gridx = 2;
        this.add(jl2, c);

        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateFlag(int flagCount) {
        jl2.setText("Flag(s) " + flagCount);
        Color myCustomColor = new Color(179,83,0);
        jl2.setForeground(myCustomColor);
    }

    public void startTimer() {
        lastTickTime = System.currentTimeMillis();
        timer.start();
    }
    public void stopTimer() {
        timer.stop();
    }
}
