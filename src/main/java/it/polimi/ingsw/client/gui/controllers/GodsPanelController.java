package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Card;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class GodsPanelController implements GUIController{
    private GUI gui;

    public void test(ActionEvent event) {
        LoaderController controller = (LoaderController)gui.getControllerFromName("loading.fxml");
        controller.godTile(Card.parseInput(((Button)event.getSource()).getText()), false);
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
