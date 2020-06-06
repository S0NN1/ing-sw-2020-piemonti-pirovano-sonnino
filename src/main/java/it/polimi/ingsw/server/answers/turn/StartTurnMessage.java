package it.polimi.ingsw.server.answers.turn;

import it.polimi.ingsw.server.answers.Answer;

/**
 * StartTurnMessage class is an Answer used for notifying a client about the start of his/her turn.
 *
 * @author Nicolò Sonnino
 * @see Answer
 */
public class StartTurnMessage implements Answer {
    private final String currentPlayer;

    /**
     * Constructor StartTurnMessage creates a new StartTurnMessage instance.
     *
     * @param currentPlayer of type String - the current player's nickname.
     */
    public StartTurnMessage(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Method getMessage returns the message of this WorkerPlacement object.
     *
     *
     *
     * @return the message (type Object) of this WorkerPlacement object.
     */
    @Override
    public Object getMessage() {
        return currentPlayer;
    }
}
