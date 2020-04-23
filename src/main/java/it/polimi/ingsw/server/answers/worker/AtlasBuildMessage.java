package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.answers.Answer;

/**
 * @author Alice Piemonti
 */
public class AtlasBuildMessage extends BuildMessage {

    public AtlasBuildMessage(Space space) {
        super(space,true);
    }

}
