package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;

/**
 * This interface represents a single GUI controller, which is different from phase to phase
 * @author Luca Pirovano
 */
public interface GUIController {
    /**
     * Method setGui sets the GUI reference to the local controller.
     * @param gui of type GUI - the main GUI class.
     */
    void setGui(GUI gui);
}
