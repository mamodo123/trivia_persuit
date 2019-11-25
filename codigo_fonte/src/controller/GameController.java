package controller;

import InterfaceGrafica.AtorJogador;
import InterfaceGrafica.Screen;
import Rede.AtorNetgames;
import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import model.Move;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GameController {

    private Screen screen;
    private State gameState;
    private ArrayList<Player> players;

    private AtorJogador atorJogador;
    private boolean em_partida = false;
    private GameController() {

        AtorNetgames ator_netgames = new AtorNetgames() {
            @Override
            public void iniciarNovaPartida(Integer posicao) {
                em_partida = true;

                players = new ArrayList<>();

                List<String> names = proxy.obterNomeAdversarios();
                names.add(proxy.getNomeJogador());

                Collections.sort(names, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });

                for (String i: names) {
                    players.add(new Player(i, 0));
                }

                gameState.setCurrent_player(players.get(0));
                screen.updateState(players);

                String current_name = gameState.getCurrent_player().getName();
                Proxy proxy = Proxy.getInstance();
                if (proxy.getNomeJogador().equals(current_name)) {
                    JOptionPane.showMessageDialog(null, "Partida iniciada! Você começa ela.");
                } else {
                    JOptionPane.showMessageDialog(null, "Partida iniciada! " + current_name + " começa ela.");
                }
            }

            @Override
            public void receberJogada(Jogada jogada) {
                updateState((Move) jogada);
            }
        };

        atorJogador = new AtorJogador(ator_netgames);

        ActionListener random_listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Proxy proxy = Proxy.getInstance();
                if (em_partida) {
                    if (gameState.getCurrent_player().getName().equals(proxy.getNomeJogador())) {
                        Random gerador = new Random();
                        int random = gerador.nextInt(6) + 1;
                        JOptionPane.showMessageDialog(null, gameState.getCurrent_player().getName() + " rolou o dado: " + (random));
                        String[] buttons = {"Esquerda", "Direita"};
                        boolean direcao = JOptionPane.showOptionDialog(null,
                                "Esquerda ou direita?",
                                "Escolha uma direção!",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                buttons,
                                buttons[0]) == 1;
                        Move move = new Move(gameState.getCurrent_player(), random, direcao);

                        updateState(move);

                        try {
                            proxy.enviaJogada(move);
                        } catch (NaoJogandoException e) {
                            JOptionPane.showMessageDialog(null, "Erro ao enviar jogada!");
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Não é sua vez!");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Partida ainda não iniciada.");
                }
            }
        };
        screen = new Screen(random_listener, new SwingAction(), new SwingAction_1(), new SwingAction_2());
        gameState = new State();

        players = new ArrayList<>();
        screen.updateState(players);
    }

    private void updateState(Move move) {
        int desloc = move.isDirection() ? -1 : 1;
        Player current = gameState.getCurrent_player();

        current.setBoard_position(current.getBoard_position() + desloc * move.getDice_value() % gameState.getBoard().getBoardMap().length);
        screen.updateState(players);

        int novo = (players.indexOf(current) + 1) % players.size();
        gameState.setCurrent_player(players.get(novo));
        Proxy proxy = Proxy.getInstance();
        if (gameState.getCurrent_player().getName().equals(proxy.getNomeJogador())) {
            JOptionPane.showMessageDialog(null, "Sua vez de jogar!");
        }
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

    private class SwingAction extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        public SwingAction() {
            putValue(NAME, "conectar");
            putValue(SHORT_DESCRIPTION, "conectar a Netgames Server");
        }
        public void actionPerformed(ActionEvent e) {
            if (!em_partida) {
                String servidor = JOptionPane.showInputDialog("Qual o servidor?");
                String name = JOptionPane.showInputDialog("Qual o seu nome?");
                String mensagem = atorJogador.conectar(servidor, name);
                JOptionPane.showMessageDialog(null, mensagem);
            } else {
                JOptionPane.showMessageDialog(null,  "Partida já em andamento!");
            }
        }
    }
    private class SwingAction_1 extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        public SwingAction_1() {
            putValue(NAME, "desconectar");
            putValue(SHORT_DESCRIPTION, "desconectar de Netgames Server");
        }
        public void actionPerformed(ActionEvent e) {
            String mensagem = atorJogador.desconectar();
            JOptionPane.showMessageDialog(null, mensagem);
        }
    }
    private class SwingAction_2 extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        public SwingAction_2() {
            putValue(NAME, "iniciar partida");
            putValue(SHORT_DESCRIPTION, "iniciar partida do seu jogo");
        }
        public void actionPerformed(ActionEvent e) {
            String mensagem = atorJogador.iniciarPartida();
            JOptionPane.showMessageDialog(null, mensagem);
        }
    }

}
