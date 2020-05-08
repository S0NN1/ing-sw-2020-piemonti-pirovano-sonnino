package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ActionParser;
import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
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

public class MainMenuController implements GUIController{

    private GUI gui;
    @FXML
    private TextField username;
    @FXML
    private TextField address;
    @FXML
    private TextField port;
    @FXML
    private Label confirmation;
    private final static String url = "https://sonar.lucapirovano.com";

    public void about(ActionEvent evt) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(url));
    }

    public void quit(ActionEvent event) {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }

    public void play(MouseEvent event) {
        gui.changeStage("setup.fxml");
        gui.centerApplication();
    }

    public void start(ActionEvent event) {
        if(username.getText().equals("") || address.getText().equals("") || port.getText().equals("")) {
            confirmation.setText("Error: missing paramenters!");
        }
        else {
            LoaderController loaderController = null;
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
                gui.getObservers().addPropertyChangeListener(new ActionParser(connectionSocket, gui.getModelView()));

            } catch (DuplicateNicknameException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate nickname");
                alert.setHeaderText("Duplicate nickname!");
                alert.setContentText("This nickname is already in use! Please choose one other.");
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
