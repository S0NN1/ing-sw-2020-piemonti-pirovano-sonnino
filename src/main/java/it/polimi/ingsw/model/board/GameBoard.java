package it.polimi.ingsw.model.board;

public class GameBoard {
    private Space[][] grid = new Space[5][5];

    public GameBoard() {
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                grid[i][j] = new Space();
                grid[i][j].setX(i);
                grid[i][j].setY(j);
            }
        }
    }

    public Space getSpace(int row, int col) {
        return grid[row][col];
    }
}
