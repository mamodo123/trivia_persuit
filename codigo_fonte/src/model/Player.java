package model;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.HashSet;

public class Player implements Jogada {
    private String name;
    private int board_position;
    private HashSet<String> categorys;

    public Player(String name) {
        this.name = name;
        this.board_position = 0;
        this.categorys = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBoard_position() {
        return board_position;
    }

    public void setBoard_position(int board_position) {
        this.board_position = board_position;
    }

    public HashSet<String> getCategorys() {
        return categorys;
    }

    public void setCategorys(HashSet<String> categorys) {
        this.categorys = categorys;
    }
}
