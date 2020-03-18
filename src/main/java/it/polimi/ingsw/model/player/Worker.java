package it.polimi.ingsw.model.player;

public class Worker {
    private final String workerColor;

    public Worker(String color) {
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
}
