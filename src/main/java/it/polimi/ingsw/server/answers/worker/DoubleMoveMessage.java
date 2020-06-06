package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.server.answers.Answer;

/**
 * DoubleMoveMessage class is an Answer used for sending information about a double move action to the client.
 * @author Alice Piemonti
 * @see Answer
 */
public class DoubleMoveMessage implements Answer {

    private final String god;
    private final Move myMove;
    private final Move otherMove;

    /**
     * Constructor DoubleMoveMessage creates a new DoubleMoveMessage instance.
     *
     * @param myMove of type Move - the first move.
     * @param otherMove of type Move - the second move.
     * @param god of type String - the type of god.
     */
    public DoubleMoveMessage(Move myMove, Move otherMove, String god){
        this.myMove = myMove;
        this.otherMove = otherMove;
        this.god = god;
    }

    /**
     * Method getMessage returns the message of this WorkerPlacement object.
     *
     *
     *
     * @return the message (type Object) of this WorkerPlacement object.
     * @see Answer#getMessage()
     */
    @Override
    public String getMessage() {
        return god;
    }

    /**
     * Method getOtherMove returns the otherMove of this DoubleMoveMessage object.
     *
     *
     *
     * @return the otherMove (type Move) of this DoubleMoveMessage object.
     */
    public Move getOtherMove(){
        return otherMove;
    }

    /**
     * Method getMyMove returns the myMove of this DoubleMoveMessage object.
     *
     *
     *
     * @return the myMove (type Move) of this DoubleMoveMessage object.
     */
    public Move getMyMove(){ return myMove;}
}
