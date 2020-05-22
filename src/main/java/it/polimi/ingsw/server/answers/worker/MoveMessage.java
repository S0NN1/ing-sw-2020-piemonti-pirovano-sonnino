package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.answers.Answer;

/**
 * @author Alice Piemonti
 */
public class MoveMessage implements Answer {
    private final Move message;

    public MoveMessage(Space oldPosition, Space newPosition){
        message = new Move(oldPosition.getRow(), oldPosition.getColumn(), newPosition.getRow(),newPosition.getColumn());
    }

    @Override
    public Move getMessage() {
        return message;
    }

}
