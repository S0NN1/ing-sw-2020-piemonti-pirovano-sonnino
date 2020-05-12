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

/**
 * This is the loading screen controller, which covers the color, challenger and worker placement phases.
 * With several calls to main GUI class, it has a 360 degree view on the GUI package, and can make modifications relying
 * on the actual server request.
 * @author Luca Pirovano
 */
public class LoaderController implements GUIController {

    private GUI gui;
    @FXML
    private Label displayStatus;
    private static final String ACTION = "action";

    /**
     * Set new text label on the loader screen.
     * @param text the string to be set as text.
     */
    public void setText(String text) {
        displayStatus.setText(text.toUpperCase());
    }

    /**
     * Open a popup to display a custom message that came from the server.
     * @param message the message to be displayed.
     */
    public void displayCustomMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Message from the server");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Opens a new god tile, which displays information about the name, thumbnail and description of a single god.
     * It's used during the challenger selection phase and during the single user selection phase.
     * @param god the god tile to be opened.
     * @param isChoosing value that states if the phase is the challenger or the choosing one.
     * @return true if the god is chosen / selected, false otherwise.
     */
    public boolean godTile(Card god, boolean isChoosing) {
        Stage godDetails = new Stage();
        GodTile godTile = new GodTile(god, godDetails, gui, isChoosing);
        Scene scene = new Scene(godTile);
        godDetails.setScene(scene);
        godDetails.setResizable(false);
        godDetails.showAndWait();
        return godTile.getValue();
    }

    /**
     * List all workers' available positions and make him choose the preferred one.
     * @param coords the available coordinates.
     */
    public void workerPlacement(List<int[]> coords) {
        gui.getModelView().toggleInput();
        int i=0;
        int[] positions = new int[4];
        HashMap<String, int[]> nameMapPosition = new HashMap<>();
        coords.forEach(n -> nameMapPosition.put(Arrays.toString(n), n));
        ArrayList<String> selectable = new ArrayList<>();
        coords.forEach(n -> selectable.add(Arrays.toString(n)));
        while (i<4) {
            Alert workerPositions = new Alert(Alert.AlertType.NONE);
            workerPositions.setTitle("Choose your workers position");
            workerPositions.setHeaderText("Choose the position of worker " + i);
            ComboBox<String> choices = new ComboBox<>(FXCollections.observableArrayList(selectable));
            workerPositions.getDialogPane().setContent(choices);
            ButtonType ok = new ButtonType("CHOOSE");
            workerPositions.getButtonTypes().setAll(ok);
            workerPositions.showAndWait();
            if(choices.getValue()!=null) {
                positions[i] = nameMapPosition.get(choices.getValue())[0];
                positions[i+1] = nameMapPosition.get(choices.getValue())[1];
                selectable.remove(choices.getValue());
                nameMapPosition.remove(choices.getValue());
                i+=2;
            }
        }
        gui.getObservers().firePropertyChange(ACTION, null, "SET " + positions[0] + " " +
                positions[1] + " " + positions[2] + " " + positions[3]);
    }

    /**
     * Makes challenger choose the starting player, by simply clicking on his nickname.
     * @param req the first player request sent from the server.
     */
    protected void startingPlayer(ChallengerMessages req) {
        Alert startingPlayer = new Alert(Alert.AlertType.CONFIRMATION);
        startingPlayer.setTitle("Choose starting player");
        startingPlayer.setContentText("Pick a starting player clicking on his nickname.");
        HashMap<String, ButtonType> players = new HashMap<>();
        assert req.getPlayers() != null;
        req.getPlayers().forEach(n -> players.put(n, new ButtonType(n)));
        startingPlayer.getButtonTypes().setAll(players.values());
        Optional<ButtonType> result = startingPlayer.showAndWait();
        result.ifPresent(buttonType -> gui.getObservers().firePropertyChange(ACTION, null, "STARTER " + req.getPlayers().indexOf(buttonType.getText())));
    }

    /**
     * Display the game's gods list received from the server, and makes the challenger choose the god powers to put in
     * game deck.
     * @param req the list received from the server
     */
    protected void displayGodList(ChallengerMessages req) {
        ComboBox<String> godListDropdown;
        LoaderController controller = (LoaderController)gui.getControllerFromName("loading.fxml");
        controller.setText("You are the challenger!");
        do {
            Alert godList = new Alert(Alert.AlertType.CONFIRMATION);
            godList.setTitle("Choose a god");
            godList.setHeaderText("Pick a god!");
            godListDropdown = new ComboBox<>(FXCollections.observableArrayList(req.getGodList()));
            godList.getDialogPane().setContent(godListDropdown);
            ButtonType ok = new ButtonType("SELECT");
            godList.getButtonTypes().setAll(ok);
            godList.showAndWait();
        } while (godListDropdown.getValue() == null || !godTile(Card.parseInput(godListDropdown.getValue()), false));
    }

    /**
     * Makes user choose his god power, relying on the list received from the server, which contains the cards selected
     * by the challenger in the previous phase.
     * @param req the choosing request received from the server.
     */
    protected void chooseGod(ChallengerMessages req) {
        while(true) {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Choose your god power!");
            message.setHeaderText("Please choose your god power from one of the list below.");
            assert req.getSelectable() != null;
            message.setContentText(req.getMessage() + "\n" + req.getSelectable().stream().map(Enum::toString).collect(Collectors.joining("\n")));
            ComboBox<Card> choices = new ComboBox<>(FXCollections.observableArrayList(req.getSelectable()));
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

    /**
     * Receives a ChallengerMessage request from the server and calls one of the methods above, relying on his type
     * and fields.
     * @param req the ChallengerMessage received from the server.
     */
    public void challengerPhase(ChallengerMessages req) {
        gui.getModelView().toggleInput();
        if (req.isStartingPlayer() && req.getPlayers() != null) {
            startingPlayer(req);
        }
        else if (req.getGodList() != null) {
            displayGodList(req);
        }
        else if (req.getSelectable() != null) {
            chooseGod(req);
        }
        else {
            assert req.getMessage() != null;
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            if(req.getMessage().contains("you are the challenger")) {
                message.setTitle("Challenger phase");
                message.setHeaderText("Challenger phase");
                message.setContentText("You are the challenger! Click below and choose the god power you want to put in" +
                        " the game deck; you can see property and description of each god by clicking on it!");
                ButtonType godList = new ButtonType("GODS' LIST");
                message.getButtonTypes().setAll(godList);
            }
            else {
                message.setTitle("Message from the server");
                message.setContentText(req.getMessage());
                ButtonType godList = new ButtonType("GODS' LIST");
                message.getButtonTypes().setAll(godList);
            }
            message.showAndWait();
            gui.getObservers().firePropertyChange(ACTION, null, "GODLIST");
        }
    }

    /**
     * Makes the first user who connect to the server choose the number of player that lobby can handles (2 o 3).
     * The capacity is then set relying on his choice.
     * @param message the request of players' number.
     */
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

    /**
     * Makes each user choose his workers' color. However, in a match of 3 players, the last one receives
     * the remaining color.
     * @param colors the available colors.
     */
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

    /**
     * Resize the status label of the loader screen.
     * @param size the font size to be set.
     */
    public void setFontSize(int size) {
        displayStatus.setFont(Font.font(size));
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
