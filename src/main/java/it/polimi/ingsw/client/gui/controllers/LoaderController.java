package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.messages.NumberOfPlayers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.Optional;

public class LoaderController implements GUIController {

    private GUI gui;
    @FXML
    private Label displayStatus;

    public void setText(String text) {
        displayStatus.setText(text);
    }

    public void displayCustomMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Message from the server");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void requestPlayerNumber(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Looby capacity");
        alert.setHeaderText("Choose the number of players.");
        alert.setContentText(message);

        ButtonType two = new ButtonType("2");
        ButtonType three = new ButtonType("3");

        alert.getButtonTypes().setAll(two, three);
        Optional<ButtonType> result = alert.showAndWait();
        int players = 0;
        if(result.get()== two) {
            players=2;
        } else if(result.get() == three) {
            players=3;
        }
        System.out.println(players);
        gui.getConnection().send(new NumberOfPlayers(players));
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
