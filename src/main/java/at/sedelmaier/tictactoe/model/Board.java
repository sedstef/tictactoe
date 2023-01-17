package at.sedelmaier.tictactoe.model;

public class Board {

    private final Player[][] data = new Player[3][3];

    public Player[][] getData() {
        return data;
    }

    public void setPlayer(int row, int column, Player player) {
        data[row][column] = player;
    }

}
