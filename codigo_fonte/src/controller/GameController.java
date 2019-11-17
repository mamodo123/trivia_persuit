package controller;

import InterfaceGrafica.Screen;
import model.Move;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GameController {

    private Screen screen;
    private State gameState;
    private ArrayList<Player> players;

    private GameController() {
        ActionListener random_listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Random gerador = new Random();
                int random = gerador.nextInt(6) + 1;
                JOptionPane.showMessageDialog(null, "Você rolou o dado: " + (random));
                String[] buttons = { "Esquerda", "Direita"};
                boolean direcao = JOptionPane.showOptionDialog(null,
                        "Esquerda ou direita?",
                        "Escolha uma direção!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        buttons,
                        buttons[0]) == 1;
                Move move = new Move(5, random, direcao);
                updateState(move);
            }
        };
        screen = new Screen(random_listener);
        gameState = new State();
        players = new ArrayList<>();

        //TODO
        players.add(new Player("Alo", 2));
        players.add(new Player("Ihhaa", 2));
        gameState.setCurrent_player(players.get(0));

        screen.updateState(players);
    }

    private void updateState(Move move) {
        int desloc = move.isDirection() ? -1 : 1;
        Player current = gameState.getCurrent_player();

        current.setBoard_position(current.getBoard_position() + desloc * move.getDice_value() % gameState.getBoard().getBoardMap().length);
        screen.updateState(players);

        int novo = (players.indexOf(current) + 1) % players.size();
        gameState.setCurrent_player(players.get(novo));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameController controler = new GameController();
                    controler.screen.getFrame().setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
