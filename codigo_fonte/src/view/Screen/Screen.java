package view.Screen;

import res.Values;
import view.board.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

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

        Container ct = new Container();
        ct.setPreferredSize(new Dimension(50, 0));
        this.add(ct);

        JButton button = new JButton("Rolar o dado");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Random gerador = new Random();
                int random = gerador.nextInt(6);
                JOptionPane.showMessageDialog(null, "Você rolou o dado: " + (random + 1));
            }
        });

        this.add(button);
    }

}