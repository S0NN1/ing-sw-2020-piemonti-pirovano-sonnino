package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.answers.Answer;

public class RemoveMessage implements Answer {
    Couple removeSpace;

    public RemoveMessage(Space removePosition) {
        removeSpace = new Couple(removePosition.getRow(), removePosition.getColumn());
    }

    @Override
    public Couple getMessage() {
        return removeSpace;
    }
}
