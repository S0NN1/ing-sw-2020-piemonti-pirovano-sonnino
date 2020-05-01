package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.tiles.GodTile;
import it.polimi.ingsw.client.messages.ChosenColor;
import it.polimi.ingsw.client.messages.NumberOfPlayers;
import it.polimi.ingsw.model.player.PlayerColors;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
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
        GodTile god = new GodTile(getClass().getResource("/graphics/gods/010_prometheus.png").toExternalForm());
        Scene scene = new Scene(god);
        Stage stage = new Stage();
        stage.setMinHeight(563);
        stage.setMinWidth(250);
        stage.setTitle("Test");
        stage.setScene(scene);
        stage.showAndWait();
        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
        gui.getConnection().send(new NumberOfPlayers(players));*/
    }

    public void requestColor(ArrayList<PlayerColors> colors) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Workers' color");
        alert.setHeaderText("Choose your workers' color!");
        alert.setContentText("Click one of the color below!");
        HashMap<String, ButtonType> buttons = new HashMap<>();
        colors.forEach(n -> buttons.put(n.toString(), new ButtonType(n.toString())));
        alert.getButtonTypes().setAll(buttons.values());
        Optional<ButtonType> result = alert.showAndWait();
        gui.getConnection().send(new ChosenColor(PlayerColors.parseInput(result.get().getText())));
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
