package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.stage.Screen;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

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

    public void about(ActionEvent evt) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://lucapirovano.com"));
    }

    public void quit(ActionEvent event) {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }

    public void play(MouseEvent event) throws IOException {

        gui.changeStage("setup.fxml");
        gui.centerApplication();
    }

    public void start(ActionEvent event) throws InterruptedException {
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
                loaderController.setText("SOCKET CONNECTION \nSETUP COMPLETED!");

            } catch (DuplicateNicknameException e) {
                loaderController.setText("ERROR: NICKNAME IS ALREADY IN USE!");
                TimeUnit.SECONDS.sleep(2);
                gui.changeStage("MainMenu.fxml");
            }
        }
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
