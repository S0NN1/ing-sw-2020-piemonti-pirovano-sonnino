package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Disconnect;
import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.WorkerSetupMessage;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.Card;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;


public class ActionParser implements PropertyChangeListener {
    private final ConnectionSocket connection;
    private final ModelView modelView;
    private static final String RED = Constants.ANSI_RED;
    private static final String RST = Constants.ANSI_RESET;
    private final PrintWriter output = new PrintWriter(System.out);
    private static final String GOD_NOT_FOUND = "Not existing god with your input's name.";

    public ActionParser(ConnectionSocket connection, ModelView modelView) {
        this.connection = connection;
        this.modelView = modelView;
    }

    public boolean desc(String[] in) {
        try {
            connection.send(new ChallengerPhaseAction("DESC", Card.parseInput(in[1])));
        } catch (IllegalArgumentException e) {
            output.println(RED + GOD_NOT_FOUND + RST);
            return false;
        }
        return true;
    }

    public boolean addGod(String[] in) {
        try {
            connection.send(new ChallengerPhaseAction("ADD", Card.parseInput(in[1])));
        } catch (IllegalArgumentException e) {
            output.println(RED + GOD_NOT_FOUND + RST);
            return false;
        }
        return true;
    }

    public boolean choose(String[] in) {
        try {
            connection.send(new ChallengerPhaseAction("CHOOSE", Card.parseInput(in[1])));
        } catch (IllegalArgumentException e) {
            output.println(RED + GOD_NOT_FOUND + RST);
            return false;
        }
        return true;
    }

    public boolean starter(String[] in){
        try {
            int startingPlayer = Integer.parseInt(in[1]);
            connection.send(new ChallengerPhaseAction(startingPlayer));
        } catch (NumberFormatException e) {
            output.println(RED + "Error: it must be a numeric value, please try again." + RST);
        }
        return true;
    }

    public boolean set(String[] in) {
        try {
            connection.send(new WorkerSetupMessage(in));
            return true;
        } catch (NumberFormatException e) {
            output.println(RED + "Unknown input, please try again!" + RST);
            return false;
        }
    }

    public void quit() {
        connection.send(new Disconnect());
        System.err.println("Disconnected from the server.");
        System.exit(0);
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
                    return true;
                case "GODDESC":
                    return desc(in);
                case "ADDGOD":
                    return addGod(in);
                case "CHOOSE":
                    return choose(in);
                case "STARTER":
                    return starter(in);
                case "SET":
                    return set(in);
                case "QUIT":
                    quit();
                    return true;
                default:
                    output.println(RED + "Unknown input, please try again!" + RST);
                    return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            output.println(RED + "Input error; try again!" + RST);
            return false;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(!modelView.getCanInput()) {
            output.println(RED + "Error: not your turn!");
            return;
        }
        if(action(evt.getNewValue().toString())) {
            modelView.untoggleInput();
        }
        else {
            modelView.toggleInput();
        }
    }
}
