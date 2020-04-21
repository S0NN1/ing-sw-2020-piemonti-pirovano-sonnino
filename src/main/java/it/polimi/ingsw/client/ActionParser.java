package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Disconnect;
import it.polimi.ingsw.client.messages.actions.GodSelectionAction;
import it.polimi.ingsw.model.Card;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class ActionParser implements PropertyChangeListener {
    private final ConnectionSocket connection;
    private final Model model;

    public ActionParser(ConnectionSocket connection, Model model) {
        this.connection = connection;
        this.model = model;
    }

    /**
     * Synchronized action method; it's called when a player inserts a command in the CLI interface, and manages his
     * selection before sending it to the server through the socket. It also performs an initial check about the
     * rightness of the captured command.
     */
    public synchronized boolean action(String input) {
        String[] in = input.split(" ");
        String command = in[0];
        try {
            switch (command.toUpperCase()) {
                case "GODLIST":
                    connection.send(new GodSelectionAction("LIST"));
                    break;
                case "GODDESC":
                    try {
                        connection.send(new GodSelectionAction("DESC", Card.parseInput(in[1])));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Not existing god with your input's name.");
                        return false;
                    }
                    break;
                case "ADDGOD":
                    try {
                        connection.send(new GodSelectionAction("ADD", Card.parseInput(in[1])));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Not existing god with your input's name.");
                        return false;
                    }
                    break;
                case "CHOOSE":
                    try {
                        connection.send(new GodSelectionAction("CHOOSE", Card.parseInput(in[1])));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Not existing god with your input's name.");
                        return false;
                    }
                    break;
                case "QUIT":
                    connection.send(new Disconnect());
                    System.err.println("Disconnected from the server.");
                    System.exit(0);
                default:
                    System.err.println("Unknown input, please try again!");
                    return false;
            }
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Input error; try again!");
            return false;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(action(evt.getNewValue().toString())) {
            model.untoggleInput();
        }
        else {
            model.toggleInput();
        }
    }
}
