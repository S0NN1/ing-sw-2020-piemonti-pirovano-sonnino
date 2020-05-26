package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ActionParser;
import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.exceptions.InvalidNicknameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The main menu controller class, which is bound as a controller to the FXML scene of the main menu and the game setup.
 * It handles the event of Play button and Quit button.
 * See the resources MainMenu.fxml file for more details.
 * @author Luca Pirovano
 */
public class MainMenuController implements GUIController{
    private static final String URL = "https://sonar.lucapirovano.com";
    private GUI gui;
    @FXML
    private TextField username;
    @FXML
    private TextField address;
    @FXML
    private TextField port;
    @FXML
    private Label confirmation;

    /**
     * Opens the project home website.
     * @param evt the event notification.
     * @throws URISyntaxException if the URI is not valid.
     * @throws IOException for the browse function.
     */
    public void about(ActionEvent evt) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(URL));
    }

    /**
     * Quit the application when the "Quit" button is pressed.
     * @param event the event notification.
     */
    public void quit(ActionEvent event) {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }

    /**
     * Change the stage scene to the setup one when the button "Play" is pressed.
     * @param event the mouse click event.
     */
    public void play(MouseEvent event) {
        gui.changeStage("setup.fxml");
        gui.centerApplication();
    }

    /**
     * The GUI start method, which is bound to the setup FXML scene. It instantiates a socket connection with the remote
     * server and change the scene to the loader one.
     * @param event the join pressed event.
     */
    public void start(ActionEvent event) {
        if(username.getText().equals("") || address.getText().equals("") || port.getText().equals("")) {
            confirmation.setText("Error: missing parameters!");
        }
        else if(address.getText().contains(" ")) {
            confirmation.setText("Error: address must not contain spaces!");
        }
        else {
            LoaderController loaderController;
            try {
                Constants.setADDRESS(address.getText());
                Constants.setPORT(Integer.parseInt(port.getText()));
            } catch (NumberFormatException e) {
                confirmation.setText("Error: missing parameters!");
                return;
            }
            try {
                gui.changeStage("loading.fxml");
                gui.centerApplication();
                loaderController = (LoaderController)gui.getControllerFromName("loading.fxml");
                loaderController.setText("CONFIGURING SOCKET CONNECTION...");
                ConnectionSocket connectionSocket = new ConnectionSocket();
                connectionSocket.setup(username.getText(), gui.getModelView(), gui.getActionHandler());
                gui.setConnection(connectionSocket);
                loaderController.setText("SOCKET CONNECTION \nSETUP COMPLETED!");
                loaderController.setText("WAITING FOR PLAYERS");
                gui.getObservers().addPropertyChangeListener("action", new ActionParser(connectionSocket, gui.getModelView()));

            } catch (DuplicateNicknameException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate nickname");
                alert.setHeaderText("Duplicate nickname!");
                alert.setContentText("This nickname is already in use! Please choose another one.");
                alert.showAndWait();
                gui.changeStage("MainMenu.fxml");
            }
            catch (InvalidNicknameException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid character nickname");
                alert.setHeaderText("Special character contained in nickname!");
                alert.setContentText("Nickname can't contain - special character! Please choose another one");
                alert.showAndWait();
                gui.changeStage("MainMenu.fxml");
            }
        }
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
