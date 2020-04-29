package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.concurrent.TimeUnit;

public class LoaderController implements GUIController {

    private GUI gui;
    @FXML
    private Label displayStatus;

    public void setText(String text) {
        displayStatus.setText(text);
    }

    public void display(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Message from the server");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
