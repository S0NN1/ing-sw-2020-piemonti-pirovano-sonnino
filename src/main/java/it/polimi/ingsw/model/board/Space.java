package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.player.Worker;

public class Space {
    private int x, y;
    private Worker worker;
    private Tower builtTower;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Worker getWorker() {
        return worker;
    }
}
