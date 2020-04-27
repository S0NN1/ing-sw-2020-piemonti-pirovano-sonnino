package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.answers.Answer;

/**
 * @author Alice Piemonti
 */
public class DoubleMoveMessage implements Answer {

    String god;
    Move myMove;
    Move otherMove;

    public DoubleMoveMessage(Move myMove, Move otherMove, String god){
        this.myMove = myMove;
        this.otherMove = otherMove;
        this.god = god;
    }

    @Override
    public String getMessage() {
        return god;
    }

    public Move getOtherMove(){
        return otherMove;
    }

    public Move getMyMove(){ return myMove;}
}
