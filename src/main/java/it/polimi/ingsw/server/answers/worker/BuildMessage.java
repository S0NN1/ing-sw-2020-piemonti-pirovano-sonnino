package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.constants.Couple;

/**
 * @author Alice Piemonti
 */
public class BuildMessage implements Answer {

    private final Action action;
    private final Couple message;
    private final boolean dome;


    public  BuildMessage(Space space, boolean dome){
        message = new Couple(space.getRow(), space.getColumn());
        this.dome = dome;
        action = Action.BUILD;
    }

    public BuildMessage(Space space, Action action) {
        message = new Couple(space.getRow(), space.getColumn());
        this.action = action;
        this.dome = false;
    }

    @Override
    public Couple getMessage() {
        return message;
    }

    public boolean getDome(){ return dome;}

    public Action getAction() {
        return action;
    }
}
