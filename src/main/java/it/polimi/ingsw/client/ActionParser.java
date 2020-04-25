package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Disconnect;
import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.client.messages.actions.WorkerSetupMessage;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.Card;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class ActionParser implements PropertyChangeListener {
    private final ConnectionSocket connection;
    private final Model model;
    private static final String red = Constants.ANSI_RED;
    private static final String rst = Constants.ANSI_RESET;

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
                    connection.send(new ChallengerPhaseAction("LIST"));
                    break;
                case "GODDESC":
                    try {
                        connection.send(new ChallengerPhaseAction("DESC", Card.parseInput(in[1])));
                    } catch (IllegalArgumentException e) {
                        System.out.println(red + "Not existing god with your input's name." + rst);
                        return false;
                    }
                    break;
                case "ADDGOD":
                    try {
                        connection.send(new ChallengerPhaseAction("ADD", Card.parseInput(in[1])));
                    } catch (IllegalArgumentException e) {
                        System.out.println(red + "Not existing god with your input's name." + rst);
                        return false;
                    }
                    break;
                case "CHOOSE":
                    try {
                        connection.send(new ChallengerPhaseAction("CHOOSE", Card.parseInput(in[1])));
                    } catch (IllegalArgumentException e) {
                        System.out.println(red + "Not existing god with your input's name." + rst);
                        return false;
                    }
                    break;
                case "SET":
                    try {
                        connection.send(new WorkerSetupMessage(in));
                    } catch (NumberFormatException e) {
                        System.out.println(red + "Unknown input, please try again!" + rst);
                        return false;
                    }
                    break;
                case "QUIT":
                    connection.send(new Disconnect());
                    System.err.println("Disconnected from the server.");
                    System.exit(0);
                default:
                    System.out.println(red + "Unknown input, please try again!" + rst);
                    return false;
            }
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(red + "Input error; try again!" + rst);
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
