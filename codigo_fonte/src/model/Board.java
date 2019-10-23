package model;

public class Board{

    private boolean[] boardMap;

    public boolean[] getBoardMap() {
        return boardMap;
    }

    Board() {
        this.boardMap = new boolean[18];
        for (int i = 0; i < boardMap.length; i++) {
            if ((i + 1) % 3 == 0) {
                boardMap[i] = true;
            }
        }
    }

}
