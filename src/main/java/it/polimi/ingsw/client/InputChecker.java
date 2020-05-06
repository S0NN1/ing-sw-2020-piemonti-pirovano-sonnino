package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Disconnect;
import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.WorkerSetupMessage;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.Card;


/**
 * Check the correctness of the input received from the ActionParser, returning either true or false after his check.
 * @author Luca Pirovano, Nicolò Sonnino
 */
public class InputChecker {
    private final ConnectionSocket connection;
    private static final String GOD_NOT_FOUND = "Not existing god with your input's name.";
    private static final String RED = Constants.ANSI_RED;
    private static final String RST = Constants.ANSI_RESET;

    public InputChecker(ConnectionSocket connection) {
        this.connection = connection;
    }

    /**
     * Validates a "GODDESC <god-name>" message type.
     * @param in the user input under array representation.
     * @return true if the input is valid and sent to the server, false otherwise.
     */
    public boolean desc(String[] in) {
        try {
            connection.send(new ChallengerPhaseAction("DESC", Card.parseInput(in[1])));
        } catch (IllegalArgumentException e) {
            System.out.println(RED + GOD_NOT_FOUND + RST);
            return false;
        }
        return true;
    }

    /**
     * Validates an "ADDGOD <god-name>" message type.
     * @param in the user input under array representation.
     * @return true if the input is valid and sent to the server, false otherwise.
     */
    public boolean addGod(String[] in) {
        try {
            connection.send(new ChallengerPhaseAction("ADD", Card.parseInput(in[1])));
        } catch (IllegalArgumentException e) {
            System.out.println(RED + GOD_NOT_FOUND + RST);
            return false;
        }
        return true;
    }

    /**
     * Validates a "CHOOSE <god-name>" message type.
     * @param in the user input under array representation.
     * @return true if the input is valid and sent to the server, false otherwise.
     */
    public boolean choose(String[] in) {
        try {
            connection.send(new ChallengerPhaseAction("CHOOSE", Card.parseInput(in[1])));
        } catch (IllegalArgumentException e) {
            System.out.println(RED + GOD_NOT_FOUND + RST);
            return false;
        }
        return true;
    }

    /**
     * Validates a "STARTER <player-number>" starting player message type.
     * @param in the user input under array representation.
     * @return true if the input is valid and sent to the server, false otherwise.
     */
    public boolean starter(String[] in){
        try {
            int startingPlayer = Integer.parseInt(in[1]);
            connection.send(new ChallengerPhaseAction(startingPlayer));
        } catch (NumberFormatException e) {
            System.out.println(RED + "Error: it must be a numeric value, please try again." + RST);
            return false;
        }
        return true;
    }

    /**
     * Validates a "SET <x1> <y1> <x2> <y2>" worker placement message type.
     * @param in the user input under array representation.
     * @return true if the input is valid and sent to the server, false otherwise.
     */
    public boolean set(String[] in) {
        try {
            connection.send(new WorkerSetupMessage(in));
            return true;
        } catch (NumberFormatException e) {
            System.out.println(RED + "Unknown input, please try again!" + RST);
            return false;
        }
    }

    /**
     * Handles the program quit statement, terminating the local service with exit code 0.
     */
    public void quit() {
        connection.send(new Disconnect());
        System.err.println("Disconnected from the server.");
        System.exit(0);
    }
}
