package controller;

import model.Board;
import model.BoardFactory;
import model.Player;

public class State {
    private Board board;
    private Player current_player;

    State() {
        this.board = BoardFactory.getInstance();
    }


    Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getCurrent_player() {
        return current_player;
    }

    public void setCurrent_player(Player current_player) {
        this.current_player = current_player;
    }
}
