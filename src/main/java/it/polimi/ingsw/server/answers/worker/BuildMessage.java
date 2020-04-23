package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.constants.Couple;

/**
 * @author Alice Piemonti
 */
public class BuildMessage implements Answer {

    private Couple message;
    private boolean dome;


    public  BuildMessage(Space space, boolean dome){
        message = new Couple(space.getX(), space.getY());
        this.dome = dome;
    }

    @Override
    public Couple getMessage() {
        return message;
    }

    public boolean getDome(){ return dome;}
}
