package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.constants.Move;

/**
 * @author Alice Piemonti
 */
public class MinotaurMoveMessage extends DoubleMoveMessage {
    public MinotaurMoveMessage(Move myMove, Move otherMove) {
        super(myMove, otherMove);
    }
}
