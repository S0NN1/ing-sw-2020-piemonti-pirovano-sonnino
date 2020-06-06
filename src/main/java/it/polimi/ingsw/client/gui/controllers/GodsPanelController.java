package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Class GodsPanelController displays god cards on GUI.
 *
 * @author Alice Piemonti
 * @see GUIController
 */
public class GodsPanelController implements GUIController{
    private GUI gui;
    private boolean choose = false;
    @FXML
    VBox mainPane;

    /**
     * Method showGod displays single god tile.
     *
     * @param event of type ActionEvent - the event received.
     */
    public void showGod(ActionEvent event) {
        LoaderController controller = (LoaderController)gui.getControllerFromName("loading.fxml");
        controller.godTile(Card.parseInput(((Button)event.getSource()).getText()), choose);
    }

    /**
     * Method chooseInit initializes the god panel for user's choosing action. It creates the buttons "Choose" and
     * "Close" and prepares the command "CHOOSE &lt;god-name&gt;" to be sent to the server.
     *
     * @param cards of type List&lt;Card&gt; - the deck of cards.
     */
    public void chooseInit(List<Card> cards) {
        for(int i=0; i<3; i++){
            AnchorPane anchorPane = (AnchorPane) mainPane.getChildren().get(i);
            HBox hbox = (HBox) anchorPane.getChildren().get(0);
            for(int j=0; j<5; j++) {
                if(i==2 && j==4) break;
                VBox vBox = (VBox)hbox.getChildren().get(j);
                AnchorPane anchorPane1 =(AnchorPane)vBox.getChildren().get(0);
                if(!cards.contains(Card.parseInput(((Button) anchorPane1.getChildren().get(1)).getText()))) {
                    ((Button)anchorPane1.getChildren().get(1)).setOnAction(null);
                    anchorPane1.getChildren().get(0).setOpacity(0.4);
                    anchorPane1.getChildren().get(1).getStyleClass().clear();
                    anchorPane1.getChildren().get(1).getStyleClass().add("grayedOut");
                }
            }
        }
        choose = true;
    }

    /** @see GUIController#setGui(GUI)*/
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
