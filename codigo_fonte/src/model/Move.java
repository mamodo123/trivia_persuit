package model;

public class Move {
    private int player;
    private int dice_value;
    private boolean direction;

    public Move(int player, int dice_value, boolean direction) {
        this.player = player;
        this.dice_value = dice_value;
        this.direction = direction;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
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
}
