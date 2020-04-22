package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.constants.Move;

/**
 * @author Alice Piemonti
 */
public class ApolloMoveMessage extends DoubleMoveMessage {

    public ApolloMoveMessage(Move myMove, Move otherMove) {
        super(myMove, otherMove);
    }
}
