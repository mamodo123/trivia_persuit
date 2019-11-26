package model;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Move implements Jogada {
    private Player player;
    private int dice_value;
    private boolean direction;
    private Question question;

    public Move(Player player, int dice_value, boolean direction) {
        this.player = player;
        this.dice_value = dice_value;
        this.direction = direction;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getDice_value() {
        return dice_value;
    }

    public void setDice_value(int dice_value) {
        this.dice_value = dice_value;
    }

    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
