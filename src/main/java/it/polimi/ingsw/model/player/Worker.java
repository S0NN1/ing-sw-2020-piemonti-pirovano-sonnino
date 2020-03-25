package it.polimi.ingsw.model.player;

public class Worker {

    private Space position;
    private boolean isBlocked;
    private final String workerColor;

    public Worker(String color) {
        this.isBlocked = false;
        this.position = null;
        switch (color.toUpperCase()) {
            case "CYAN":
                this.workerColor = "\033[34m";
                break;
            case "WHITE":
                this.workerColor = "\033[39m";
                break;
            case "GREY":
                this.workerColor = "\033[37m";
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void setPosition(Space space) {
        this.position = space;
    }

    public Space getPosition() {
        return this.position;
    }

    public boolean isBlocked() {
        return this.isBlocked;
    }

    public boolean hasWon() {
        /*
        -----------------DA COMPLETARE----------------
         */
    }

    public void move(Gameboard board) {

    }

    public boolean isSelectable(Space space) {
        if( (space.getX - position.getX < 2) && (position.getX - space.getX < 2) &&
            (space.getY - position.getY < 2) && (position.getY - space.getY <2) &&
            !space.getTower.isCompleted() &&
            ( space.getTower.getHeight() - this.position.getTower.getHeight() < 2) &&
            !space.isEmpty){
            retun true;
        }
    }

    public ArrayList<Space> getMoves(Gameboard board) {
        ArrayList<Space> moves = new ArrayList<Space>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Space space = board.getSpace(i,j);
                if (isSelectable(space)) { moves.add(space);}
            }
        }
        return  moves;
    }
}