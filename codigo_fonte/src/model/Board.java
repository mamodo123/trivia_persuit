package model;

public class Board{

    private String[] boardMap;
    private String[] questions_type =
            {
                    "Ciencias e Natureza",
                    "Historia",
                    "Desporto e Lazer",
                    "Entretenimento",
                    "Geografia",
                    "Arte e Literatura"
            };

    public String[] getBoardMap() {
        return boardMap;
    }

    Board() {
        this.boardMap = new String[18];
        int x = 0;
        for (int i = 0; i < boardMap.length; i++) {
            if ((i + 1) % 3 == 0) {
                boardMap[i] = questions_type[x++];
            }
        }
    }

}
