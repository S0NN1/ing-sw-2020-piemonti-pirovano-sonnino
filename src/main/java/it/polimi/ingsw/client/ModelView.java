package it.polimi.ingsw.client;

/**
 * @author Alice Piemonti
 */
public class ModelView {
    private Cell[][] grid;

    public ModelView(){
        grid = new Cell[5][5];
    }

    public Cell[][] getGrid(){
        return grid;
    }

    public void setColor(int row, int col, String color){
        grid[row][col].setColor(color);
    }

    public String getColor(int row, int col){
        return grid[row][col].getColor();
    }

    public void move(int oldRow, int oldCol, int newRow, int newCol){
        String color = grid[oldRow][oldCol].getColor();
        grid[oldRow][oldCol].setColor(null);
        grid[newRow][newCol].setColor(color);
    }

    public void doubleMove(int oldRow1, int oldCol1, int newRow1, int newCol1, //TODO PASSARE I MOVE MAGARI
                           int oldRow2, int oldCol2, int newRow2, int newCol2){
        if(oldRow1 == newRow1 && oldCol1 == newCol1) {
            String color1 = grid[oldRow1][oldCol1].getColor();
            move(oldRow2, oldCol2, newRow2, newCol2);
            grid[newRow1][newCol1].setColor(color1);
        }
    }

    public void build(int row, int col){
        grid[row][col].addLevel();
    }







}
