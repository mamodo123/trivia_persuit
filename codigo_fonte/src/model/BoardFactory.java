package model;

public class BoardFactory {

    private static Board board = new Board();

    public static Board getInstance() {
        return board;
    }

}
