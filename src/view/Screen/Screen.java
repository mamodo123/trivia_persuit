package view.Screen;

import res.Values;
import view.board.BoardPanel;

import javax.swing.*;
import java.awt.*;

public class Screen extends JFrame {

    private Dimension screenSize;

    public Screen() {
        this.setTitle(Values.APP_NAME);
        setImage();

        setConfig();
        setSize();
        setComponents();

        this.getContentPane().setBackground(Values.BG_COLOR);
        this.setVisible(true);
    }

    private void setImage() {
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/images/triviando.png"));
        ImageIcon icon = new ImageIcon(image);
        setIconImage(icon.getImage());
    }

    private void setSize() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(screenSize);
        this.setMinimumSize(new Dimension((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.5)));
    }

    private void setConfig() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void setComponents() {
        this.setLayout(new GridBagLayout());
        this.add(new BoardPanel(new Dimension((int) (screenSize.getWidth() * 0.9), (int) (screenSize.getHeight() * 0.9))));
    }

}
