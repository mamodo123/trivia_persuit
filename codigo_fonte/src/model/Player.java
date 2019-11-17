package model;

public class Player {
    private String name;
    private int board_position;

    public Player(String name, int board_position) {
        this.name = name;
        this.board_position = board_position;
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
}
