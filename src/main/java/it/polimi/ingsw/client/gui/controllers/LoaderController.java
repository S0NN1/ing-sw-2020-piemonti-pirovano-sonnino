package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.tiles.GodTile;
import it.polimi.ingsw.client.messages.ChosenColor;
import it.polimi.ingsw.client.messages.NumberOfPlayers;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.answers.ChallengerMessages;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class LoaderController implements GUIController {

    private GUI gui;
    @FXML
    private Label displayStatus;

    public void setText(String text) {
        displayStatus.setText(text.toUpperCase());
    }

    public void displayCustomMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Message from the server");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean godTile(Card god, boolean isChoosing) {
        Stage godDetails = new Stage();
        GodTile godTile = new GodTile(god, godDetails, gui, isChoosing);
        Scene scene = new Scene(godTile);
        godDetails.setScene(scene);
        godDetails.setResizable(false);
        godDetails.showAndWait();
        return godTile.getValue();
    }

    public void workerPlacement(List<int[]> coords) {
        int i=0;
        int[] positions = new int[4];
        HashMap<String, int[]> nameMAPposition = new HashMap<>();
        coords.forEach(n -> nameMAPposition.put(Arrays.toString(n), n));
        ArrayList<String> choosable = new ArrayList<>();
        coords.forEach(n -> choosable.add(Arrays.toString(n)));
        while (i<2) {
            Alert workerPositions = new Alert(Alert.AlertType.NONE);
            workerPositions.setTitle("Choose your workers position");
            workerPositions.setHeaderText("Choose the position of worker " + i);
            ComboBox<String> choices = new ComboBox<>(FXCollections.observableArrayList(choosable));
            workerPositions.getDialogPane().setContent(choices);
            ButtonType ok = new ButtonType("CHOOSE");
            workerPositions.getButtonTypes().setAll(ok);
            workerPositions.showAndWait();
            if(choices.getValue()!=null) {
                positions[0] = nameMAPposition.get(choices.getValue())[0];
                positions[1] = nameMAPposition.get(choices.getValue())[1];
                choosable.remove(choices.getValue());
                nameMAPposition.remove(choices.getValue());
                i++;
            }
        }
        gui.getObservers().firePropertyChange("action", null, "SET " + positions[0] + " " +
                positions[1] + " " + positions[2] + " " + positions[3]);
    }

    protected void startingPlayer(ChallengerMessages req) {
        Alert startingPlayer = new Alert(Alert.AlertType.CONFIRMATION);
        startingPlayer.setTitle("Choose starting player");
        startingPlayer.setContentText("Pick a starting player clicking on his nickname.");
        HashMap<String, ButtonType> players = new HashMap<>();
        req.players.forEach(n -> players.put(n, new ButtonType(n)));
        startingPlayer.getButtonTypes().setAll(players.values());
        Optional<ButtonType> result = startingPlayer.showAndWait();
        result.ifPresent(buttonType -> gui.getObservers().firePropertyChange("action", null, "STARTER " + req.players.indexOf(buttonType.getText())));
    }

    protected void displayGodList(ChallengerMessages req) {
        ComboBox<String> godListDropdown;
        LoaderController controller = (LoaderController)gui.getControllerFromName("loading.fxml");
        controller.setText("You are the challenger!");
        while (true) {
            Alert godList = new Alert(Alert.AlertType.CONFIRMATION);
            godList.setTitle("Choose a god");
            godList.setHeaderText("Pick a god!");
            godListDropdown = new ComboBox<>(FXCollections.observableArrayList(req.godList));
            godList.getDialogPane().setContent(godListDropdown);
            ButtonType ok = new ButtonType("SELECT");
            godList.getButtonTypes().setAll(ok);
            godList.showAndWait();
            if (godListDropdown.getValue()!=null) {
                if(godTile(Card.parseInput(godListDropdown.getValue()), false)) break;
            }
        }
    }

    protected void chooseGod(ChallengerMessages req) {
        while(true) {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Choose your god power!");
            message.setHeaderText("Please choose your god power from one of the list below.");
            message.setContentText(req.message + "\n" + req.choosable.stream().map(Enum::toString).collect(Collectors.joining("\n")));
            ComboBox<Card> choices = new ComboBox<>(FXCollections.observableArrayList(req.choosable));
            message.getDialogPane().setContent(choices);
            ButtonType choose = new ButtonType("DETAILS");
            message.getButtonTypes().setAll(choose);
            message.showAndWait();
            if (choices.getValue()!=null) {
                if(godTile(choices.getValue(), true)) {
                    break;
                }
            }
        }
    }

    public void challengerPhase(ChallengerMessages req) {
        gui.getModelView().toggleInput();
        if (req.startingPlayer && req.players != null) {
            startingPlayer(req);
        }
        else if (req.godList != null) {
            displayGodList(req);
        }
        else if (req.choosable != null) {
            chooseGod(req);
        }
        else if(req.message.contains("you are the challenger")) {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Challenger phase");
            message.setHeaderText("Challenger phase");
            message.setContentText("You are the challenger! Click below and choose the god power you want to put in" +
                    " the game deck; you can see property and description of each god by clicking on it!");
            ButtonType godList = new ButtonType("GODS' LIST");
            message.getButtonTypes().setAll(godList);
            message.showAndWait();
            gui.getObservers().firePropertyChange("action", null, "GODLIST");
        }
        else {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Message from the server");
            message.setContentText(req.message);
            ButtonType godList = new ButtonType("GODS' LIST");
            message.getButtonTypes().setAll(godList);
            message.showAndWait();
            gui.getObservers().firePropertyChange("action", null, "GODLIST");
        }
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
        if(result.isPresent() && result.get()== two) {
            players=2;
        } else if(result.isPresent() && result.get() == three) {
            players=3;
        }
        gui.getConnection().send(new NumberOfPlayers(players));
    }

    public void requestColor(List<PlayerColors> colors) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Workers' color");
        alert.setHeaderText("Choose your workers' color!");
        alert.setContentText("Click one of the color below!");
        HashMap<String, ButtonType> buttons = new HashMap<>();
        colors.forEach(n -> buttons.put(n.toString(), new ButtonType(n.toString())));
        alert.getButtonTypes().setAll(buttons.values());
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> gui.getConnection().send(new ChosenColor(PlayerColors.parseInput(buttonType.getText()))));
    }

    public void godDescription(String description) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("God's Description");
        alert.setContentText(description);
        alert.getButtonTypes().setAll(new ButtonType("CLOSE"));
        alert.showAndWait();
    }

    public void setFontSize(int size) {
        displayStatus.setFont(Font.font(size));
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
