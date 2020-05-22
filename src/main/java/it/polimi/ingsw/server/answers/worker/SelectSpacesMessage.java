package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.constants.Couple;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alice Piemonti
 */
public class SelectSpacesMessage implements Answer {
    private final Action action;
    private final ArrayList<Couple> message = new ArrayList<>();

    public SelectSpacesMessage(List<Space> moves, Action action){
        moves.forEach(space -> message.add(new Couple(space.getRow(), space.getColumn())));
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public ArrayList<Couple> getMessage() {
        return message;
    }
}
