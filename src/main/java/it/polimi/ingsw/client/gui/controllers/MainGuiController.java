package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.Cell;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.shapes.Block;
import it.polimi.ingsw.client.gui.shapes.Dome;
import it.polimi.ingsw.client.gui.shapes.Worker;
import it.polimi.ingsw.constants.Constants;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MainGuiController implements GUIController{
    private GUI gui;
    @FXML
    GridPane grid;

    @FXML
    public void showBoard() {
        Cell[][] cell = gui.getModelView().getBoard().getGrid();
        grid.add(new Block(1),4,3);
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                grid.add(new Block(1), j, i);
                if(cell[i][j].getColor() != null){
                    String color = cell[i][j].getColor();
                    grid.add(new Worker(Color.RED), i, j);
                }
            }
        }
        grid.add(new Block(1),0,0);
        grid.add(new Block(2),0,0);
        grid.add(new Block(3),0,0);
        grid.add(new Dome(),0,0);

        grid.add(new Block(2),2,2);
        grid.add(new Block(3),2,2);
        grid.add(new Worker(Color.GOLDENROD),2,2);
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
