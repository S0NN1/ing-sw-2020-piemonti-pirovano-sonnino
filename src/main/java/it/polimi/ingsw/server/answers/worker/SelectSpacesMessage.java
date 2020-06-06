package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.constants.Couple;

import java.util.ArrayList;
import java.util.List;

/**
 * SelectSpacesMessage class is an Answer used for sending selectable spaces to the client.
 *
 * @author Alice Piemonti
 * @see Answer
 */
public class SelectSpacesMessage implements Answer {
    private final Action action;
    private final ArrayList<Couple> message = new ArrayList<>();

    /**
     * Constructor SelectSpacesMessage creates a new SelectSpacesMessage instance.
     *
     * @param moves of type List&lt;Space&gt; - the list of selectable spaces.
     * @param action of type Action - the type of action.
     */
    public SelectSpacesMessage(List<Space> moves, Action action){
        moves.forEach(space -> message.add(new Couple(space.getRow(), space.getColumn())));
        this.action = action;
    }

    /**
     * Method getAction returns the action of this SelectSpacesMessage object.
     *
     *
     *
     * @return the action (type Action) of this SelectSpacesMessage object.
     */
    public Action getAction() {
        return action;
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
    public ArrayList<Couple> getMessage() {
        return message;
    }
}
