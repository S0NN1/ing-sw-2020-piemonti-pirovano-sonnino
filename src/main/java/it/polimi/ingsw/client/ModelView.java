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

    public void apolloDoubleMove(int oldRow1, int oldCol1, int oldRow2, int oldCol2){
        String color1 = grid[oldRow1][oldCol1].getColor();
        String color2 = grid[oldRow2][oldCol2].getColor();
        grid[oldRow1][oldCol1].setColor(color2);
        grid[oldRow2][oldCol2].setColor(color1);
    }

    public void minotaurDoubleMove(int oldRow1, int oldCol1, int oldRow2, int oldCol2, int newRow2, int newCol2){
        String color1 = grid[oldRow1][oldCol1].getColor();
        String color2 = grid[oldRow2][oldCol2].getColor();
        grid[newRow2][newCol2].setColor(color2);
        grid[oldRow2][oldCol2].setColor(color1);
        grid[oldRow1][oldCol1].setColor(null);
    }

    public void build(int row, int col, boolean dome){
        if(dome || grid[row][col].getLevel() == 3){
            grid[row][col].setDome(true);
        }
        else grid[row][col].addLevel();
    }

}
