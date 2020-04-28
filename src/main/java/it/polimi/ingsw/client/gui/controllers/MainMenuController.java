package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    public void start(ActionEvent event) {
        if(username.getText().equals("") || address.getText().equals("") || port.getText().equals("")) {
            confirmation.setText("Error: missing paramenters!");
        }
        else {
            gui.changeStage("loading.fxml");
            gui.centerApplication();
        }
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
