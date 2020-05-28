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
 * MainMenuController class is a controller, bound to the FXML scene of the main menu and the game setup, it handles
 * the event of play button and quit button.
 * @author Luca Pirovano
 * @see GUIController
 */
public class MainMenuController implements GUIController{
    private static final String URL = "https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino";
    private GUI gui;
    @FXML private TextField username;
    @FXML private TextField address;
    @FXML private TextField port;
    @FXML private Label confirmation;

    /**
     * Method about opens the project home website.
     * @param evt of type ActionEvent - the event notification.
     * @throws URISyntaxException when the URI is not valid.
     * @throws IOException when the browsing function stops working.
     */
    public void about(ActionEvent evt) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(URL));
    }

    /**
     * Method quit kills the application when the "Quit" button is pressed.
     * @param event of type ActionEvent - the event notification.
     */
    public void quit(ActionEvent event) {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }

    /**
     * Method play changes the stage scene to the setup one when the button "Play" is pressed.
     * @param event of type MouseEvent - the mouse click event.
     */
    public void play(MouseEvent event) {
        gui.changeStage("setup.fxml");
        gui.centerApplication();
    }

    /**
     * Method start, bound to the setup FXML scene, it instantiates a socket connection with the remote server and
     * changes the scene to the loader one.
     * @param event of type ActionEvent - the join pressed event.
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
                gui.getListeners().addPropertyChangeListener("action", new ActionParser(connectionSocket, gui.getModelView()));

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

    /** @see GUIController#setGui(GUI)  */
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
