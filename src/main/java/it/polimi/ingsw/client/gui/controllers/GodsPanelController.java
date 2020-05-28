package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class GodsPanelController implements GUIController{
    private GUI gui;
    private boolean choose = false;
    @FXML
    StackPane stackPane;

    public void showGod(ActionEvent event) {
        LoaderController controller = (LoaderController)gui.getControllerFromName("loading.fxml");
        controller.godTile(Card.parseInput(((Button)event.getSource()).getText()), choose);
    }

    public void chooseInit(List<Card> cards) {
        for(int i=0; i<3; i++){
            HBox hbox = (HBox)stackPane.getChildren().get(i);
            for(int j=0; j<5; j++) {
                if(i==2 && j==4) break;
                VBox vBox = (VBox)hbox.getChildren().get(j);
                if(!cards.contains(Card.parseInput(((Button) vBox.getChildren().get(1)).getText()))) {
                    ((Button)vBox.getChildren().get(1)).setOnAction(null);
                    vBox.getChildren().get(0).setOpacity(0.4);
                    vBox.getChildren().get(1).getStyleClass().clear();
                    vBox.getChildren().get(1).getStyleClass().add("grayedOut");
                }
            }
        }
        choose = true;
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
