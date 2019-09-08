package view.board;

import helpers.Helper;
import model.Board;
import model.BoardFactory;
import res.Values;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoardPanel extends JPanel {

    final private Dimension dimension;
    private BufferedImage boardImage;
    private BufferedImage state;
    final private Board board;


    public BoardPanel(Dimension dimension) {
        this.dimension = new Dimension((int) dimension.getHeight(), (int) dimension.getHeight());
        this.setPreferredSize(this.dimension);
        this.board = BoardFactory.getInstance();

        this.setBackground(Values.BG_COLOR);

        drawImage();
        updateState();

    }

    private void drawImage() {
        this.boardImage = new BufferedImage(this.dimension.width, this.dimension.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) boardImage.getGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Values.NEUTRA);
        final int size = boardImage.getWidth();
        g.fillOval(0, 0, size, size);

        final var boardMap = this.board.getBoardMap();
        final var angule = 360 / boardMap.length;
        var total_angule = 0;

        var cont = 0;

        for (boolean i : boardMap) {
            g.setColor(Values.COLORS[cont % Values.COLORS.length]);
            if (i) {
                g.fillArc(0, 0, size, size, total_angule, angule);
                cont++;
            }

            final var endX = size / 2 + size / 2 * Math.cos(Math.toRadians(total_angule));
            final var endY = size / 2 + size / 2 * Math.sin(Math.toRadians(total_angule));

            g.setColor(Values.LINES);
            g.drawLine(size / 2, size / 2, (int) endX, (int) endY);

            total_angule += angule;
        }

        g.setColor(getBackground());
        g.fillOval(Values.STROKE / 2, Values.STROKE / 2, size - Values.STROKE, size - Values.STROKE);


        try {
            var url = getClass().getResource("/res/images/trivial_logo.png");
            if (url != null) {
                BufferedImage img = ImageIO.read(url);
                g.drawImage(img, ((int) dimension.getWidth() - img.getWidth()) / 2, ((int) dimension.getHeight() - img.getHeight()) / 2, null);
            }
        } catch (IOException e) {
            System.out.println(Values.ERROR_IMAGE);
        }

        //TODO
        final var start_x = size / 2 + (size - Values.STROKE) / 2 * Math.cos(Math.toRadians(angule >> 1));
        final var start_y = size / 2 - (size - Values.STROKE) / 2 * Math.sin(Math.toRadians(angule >> 1));
        g.setFont(new Font("Helvetica",Font.BOLD,24));
        g.drawString("INÍCIO", (int) start_x + 10, (int) start_y);
    }

    private void updateState() {
        this.state = new BufferedImage(this.dimension.width, this.dimension.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) state.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final var angule = 360 / board.getBoardMap().length;
        final var stroke_size = Values.STROKE / 2;
        final var map = Helper.agrup_array(Values.PLAYERS);
        final int size = state.getWidth();
        g.setColor(Color.CYAN);
        for (int posicao : map.keySet()) {
            var quantity = map.get(posicao);

            int size_quantity = stroke_size / (quantity);
            for (int i = 0; i < quantity; i++) {
                final var a = Math.toRadians(angule * posicao + (angule >> 1));
                final var b = size - size_quantity * i * 2 - stroke_size / quantity;
                final var endXPiece = (size + b * Math.cos(a)) / 2;
                final var endYPiece = (size - b * Math.sin(a)) / 2;
                g.fillOval((int) endXPiece - size_quantity / 2, (int) endYPiece - size_quantity / 2, size_quantity, size_quantity);
            }

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(boardImage, ((int) dimension.getWidth() - boardImage.getWidth()) / 2, ((int) dimension.getHeight() - boardImage.getHeight()) / 2, null);
        g.drawImage(state, ((int) dimension.getWidth() - state.getWidth()) / 2, ((int) dimension.getHeight() - state.getHeight()) / 2, null);
    }

}
