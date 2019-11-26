package controller;

import InterfaceGrafica.AtorJogador;
import InterfaceGrafica.Screen;
import Rede.AtorNetgames;
import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import model.Board;
import model.Move;
import model.Player;
import model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.*;

public class GameController {

    private Screen screen;
    private State gameState;
    private ArrayList<Player> players;
    private Map<String, Object> questions;

    private AtorJogador atorJogador;
    private boolean em_partida = false;

    private GameController() {
        File file = new File(
                getClass().getResource("/res/questions.txt").getFile()
        );

        try (Scanner scanner = new Scanner(file, "utf-8")) {
            String json = scanner.useDelimiter("\\A").next();
            questions = new Gson().fromJson(json, Map.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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

                for (String i : names) {
                    players.add(new Player(i));
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

            @Override
            public void receberMensagem(String msg) {
                JOptionPane.showMessageDialog(null, msg);
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
                        random = 5;
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

                        updateBoard(move);
                        Board board = gameState.getBoard();
                        int position = gameState.getCurrent_player().getBoard_position();
                        String category = board.getBoardMap()[position];
                        if (category != null) {
                            JOptionPane.showMessageDialog(null, "Você tirou " + category + "!");
                            ArrayList<Object> mcat = (ArrayList<Object>) questions.get(category);
                            int qrandom = gerador.nextInt(mcat.size());
                            HashMap<String, Object> mquestion = new HashMap<>((LinkedTreeMap) mcat.get(qrandom));

                            String question = (String) mquestion.get("question");
                            int answer = ((Double) mquestion.get("correct_answer")).intValue() - 1;
                            String[] options = ((ArrayList<String>) mquestion.get("answers")).toArray(new String[0]);

                            int resp = JOptionPane.showOptionDialog(null,
                                    question,
                                    "Pergunta!",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    buttons[0]);

                            Question quest = new Question(category, question, options[answer], options[resp], resp == answer);
                            move.setQuestion(quest);
                        }
                        updateCurrent(move.getQuestion());

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
        updateBoard(move);
        updateCurrent(move.getQuestion());
    }

    private void updateBoard(Move move) {
        int desloc = move.isDirection() ? -1 : 1;
        Player current = gameState.getCurrent_player();

        int new_p = (current.getBoard_position() + desloc * move.getDice_value()) % gameState.getBoard().getBoardMap().length;
        if (new_p < 0) {
            new_p += gameState.getBoard().getBoardMap().length;
        }

        current.setBoard_position(new_p);
        screen.updateState(players);
    }

    private void updateCurrent(Question question) {
        Player current = gameState.getCurrent_player();
        Proxy proxy = Proxy.getInstance();
        if (question != null) {
            String name;
            String text = "";
            if (current.getName().equals(proxy.getNomeJogador())) {
                name = "Você";
            } else {
                name = gameState.getCurrent_player().getName();
            }
            text += name;
            if (question.isAcertou()) {
                current.getCategorys().add(question.getCategory());
                text += " acertou!";
            } else {
                text += " errou!";
            }

            text += "\nPergunta respondida: " + question.getQuestion()
                    + "\nResposta dada: " + question.getAnswer()
                    + "\nResposta correta: " + question.getRight_answer();
            JOptionPane.showMessageDialog(null, text);

            if (current.getCategorys().size() == /*questions.size()*/1) {
                JOptionPane.showMessageDialog(null, name + " ganhou o jogo!");
                try {
                    if (!current.getName().equals(proxy.getNomeJogador())) {
                        proxy.finalizarPartida();
                    }
                } catch (NaoConectadoException | NaoJogandoException e) {
                    e.printStackTrace();
                } finally {
                    players = new ArrayList<>();
                    screen.updateState(players);
                    return;
                }
            }
        }

        int novo = (players.indexOf(current) + 1) % players.size();
        gameState.setCurrent_player(players.get(novo));
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
                JOptionPane.showMessageDialog(null, "Partida já em andamento!");
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
