package it.polimi.ingsw.client.gui.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {

    public Block(int level) {
        super();
        switch (level) {
            case 1 -> {
                setWidth(65);
                setHeight(65);
            }
            case 2 -> {
                setWidth(47);
                setHeight(47);
            }
            case 3 -> {
                setWidth(30);
                setHeight(30);
            }
            default -> throw new IllegalArgumentException();
        }
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }
}
