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
 * LoaderController class is the loading screen controller which covers the color, challenger and worker placement
 * phases.
 * By using several calls to main GUI class, it has a 360-degree view on the GUI package and can make modifications
 * based on the actual server request.
 * @author Luca Pirovano
 * @see GUIController
 */
public class LoaderController implements GUIController {

    public static final String GODS_MENU_FXML = "godsMenu.fxml";
    public static final String CHALLENGER_PHASE = "Challenger phase";
    private GUI gui;
    @FXML
    private Label displayStatus;
    private static final String ACTION = "action";


    /**
     * Method setText sets the text of this LoaderController object.
     *
     *
     *
     * @param text the text of this LoaderController object.
     *
     */
    public void setText(String text) {
        displayStatus.setText(text.toUpperCase());
    }

    /**
     * Method displayCustomMessage displays a popup containing a custom message from the server.
     * @param message of type String - the message to be displayed.
     */
    public void displayCustomMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Message from the server");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Method goTile opens a new god tile which displays information about the name, thumbnail and description of a
     * single god.
     * It's used during the challenger selection phase and during the single user selection phase.
     * @param god of type Card - the god tile to be opened.
     * @param isChoosing of type boolean - the value that states if the phase is the challenger or the choosing one.
     * @return boolean true if the god is chosen / selected, false otherwise.
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
     * Method workerPlacement lists all workers' available positions and make him choose the preferred one.
     * @param coords of type List<int[]> - the available coordinates.
     */
    public void workerPlacement(List<int[]> coords) {
        gui.getModelView().activateInput();
        int i=0;
        int j=1;
        int[] positions = new int[4];
        HashMap<String, int[]> nameMapPosition = new HashMap<>();
        coords.forEach(n -> nameMapPosition.put(Arrays.toString(n), n));
        ArrayList<String> selectable = new ArrayList<>();
        coords.forEach(n -> selectable.add(Arrays.toString(n)));
        while (i<4) {
            Alert workerPositions = new Alert(Alert.AlertType.NONE);
            workerPositions.setTitle("Choose your workers position");
            workerPositions.setHeaderText("Choose the position of worker " + j);
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
                j++;
            }
        }
        gui.getListeners().firePropertyChange(ACTION, null, "SET " + positions[0] + " " +
                positions[1] + " " + positions[2] + " " + positions[3]);
    }

    /**
     * Method startingPlayer lets the challenger choose the starting player by simply clicking on his nickname.
     * @param req of type ChallengerMessage - the first player request sent from the server.
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
        result.ifPresent(buttonType -> gui.getListeners().firePropertyChange(ACTION, null, "STARTER " + req.getPlayers().indexOf(buttonType.getText())));
    }

    /**
     * Method displayGodList displays the game's gods list received from the server and lets the challenger choose the
     * god powers to put in game deck.
     * @param req of type ChallengerMessage - the list received from the server
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
        } while (godListDropdown.getValue() == null || !godTile(Card.parseInput(godListDropdown.getValue()),false));
    }

    /**
     * Method chooseGod lets the user choose his god power relying on the list received from the server,
     * which contains the cards selected by the challenger in the previous phase.
     * @param req of type ChallengerMessages - the choosing request received from the server.
     */
    protected void chooseGod(ChallengerMessages req) {
        while(true) {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Choose your god power!");
            message.setHeaderText("Please choose your god power from one of the list below.");
            assert req.getSelectable() != null;
            message.setContentText(req.getMessage() + "\n" +
                    req.getSelectable().stream().map(Enum::toString).collect(Collectors.joining("\n")));
            ComboBox<Card> choices = new ComboBox<>(FXCollections.observableArrayList(req.getSelectable()));
            message.getDialogPane().setContent(choices);
            ButtonType choose = new ButtonType("DETAILS");
            message.getButtonTypes().setAll(choose);
            message.showAndWait();
            if (choices.getValue()!=null && godTile(choices.getValue(), true)) {
                break;
            }
        }
    }

    /**
     * Method ChallengerPhase receives a ChallengerMessage request from the server and calls one of the methods above,
     * relying on his type and fields.
     * @param req of type ChallengerMessages - the ChallengerMessage received from the server.
     */
    public void challengerPhase(ChallengerMessages req) {
        gui.getModelView().activateInput();
        if (req.isStartingPlayer() && req.getPlayers() != null) {
            startingPlayer(req);
        }
        else if (req.getGodList() != null) {
            displayGodList(req);
        }
        else if (req.getSelectable() != null) {
            gui.changeStage(GODS_MENU_FXML);
            gui.centerApplication();
            GodsPanelController controller = (GodsPanelController)gui.getControllerFromName(GODS_MENU_FXML);
            controller.chooseInit(req.getSelectable());
        }
        else if(req.getMessage() != null) {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle(CHALLENGER_PHASE);
            message.setHeaderText(CHALLENGER_PHASE);
            if(req.getMessage().contains("you are the challenger")) {
                message.setHeaderText(CHALLENGER_PHASE);
                message.setContentText("You are the challenger! Click below and choose the god power you want to put " +
                        "in the game deck; you can see property and description of each god by clicking on it!");
            }
            else if(req.getMessage().contains("All gods have been added!")) {
                gui.changeStage("loading.fxml");
                return;
            }
            else {
                message.setContentText(req.getMessage());
            }
            message.showAndWait();
            gui.changeStage(GODS_MENU_FXML);
        }
    }

    /**
     * Method requestPlayerNumber lets the first user, who connect to the server, choose the nax umber of player of the
     * lobby (2 o 3); the capacity is then set based on his choice.
     * @param message of type String - the request of players' number.
     */
    public void requestPlayerNumber(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lobby capacity");
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
     * Method requestColor lets each user choose his workers' color, however, in a match of 3 players the last one
     * receives the remaining color.
     * @param colors of type List<PlayerColors> - the available colors.
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
        result.ifPresent(buttonType -> gui.getConnection().send(new ChosenColor(
                PlayerColors.parseInput(buttonType.getText()))));
    }

    /**
     * Method setFontSize resizes the status label of the loader screen.
     * @param size of type int - the font size to be set.
     */
    public void setFontSize(int size) {
        displayStatus.setFont(Font.font(size));
    }

    /** @see GUIController#setGui(GUI)  */
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
