package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.Cell;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.constants.Constants;
import javafx.fxml.FXML;
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
        grid.add(new Rectangle(45,45),4,3);

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(cell[i][j].getColor() != null){
                    String color = cell[i][j].getColor();
                    grid.add(new Circle(10.0, Color.RED), i, j);
                }
            }
        }
        grid.add(new Rectangle(45,45),0,0);
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
