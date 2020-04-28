package it.polimi.ingsw.client.gui.controllers;

import javafx.event.ActionEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainMenuController {

    public void about(ActionEvent evt) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://lucapirovano.com"));
    }

    public void quit(ActionEvent event) {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }

    public void start(ActionEvent event) {
        //TODO
    }
}
