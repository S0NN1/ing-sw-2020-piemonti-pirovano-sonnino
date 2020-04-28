package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;

public class LoaderController implements GUIController {

    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
