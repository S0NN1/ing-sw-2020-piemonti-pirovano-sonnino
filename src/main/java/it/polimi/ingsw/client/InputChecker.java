package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Disconnect;
import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.WorkerSetupMessage;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.Card;

import java.io.PrintWriter;

public class InputChecker {
    private final ConnectionSocket connection;
    private static final String GOD_NOT_FOUND = "Not existing god with your input's name.";
    private final PrintWriter output = new PrintWriter(System.out);
    private static final String RED = Constants.ANSI_RED;
    private static final String RST = Constants.ANSI_RESET;

    public InputChecker(ConnectionSocket connection) {
        this.connection = connection;
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
}
