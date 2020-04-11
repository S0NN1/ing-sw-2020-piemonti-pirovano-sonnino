package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.answers.Answer;

public class DoubleMoveMessage implements Answer {

    Move myMove;
    Move otherMove;

    public DoubleMoveMessage(Move myMove, Move otherMove){
        this.myMove = myMove;
        this.otherMove = otherMove;
    }

    @Override
    public Move getMessage() {
        return myMove;
    }

    public Move getOtherMove(){
        return otherMove;
    }
}
