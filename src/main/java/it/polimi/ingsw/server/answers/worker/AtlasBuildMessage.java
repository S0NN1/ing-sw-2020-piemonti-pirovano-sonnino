package it.polimi.ingsw.server.answers.worker;

import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.server.answers.Answer;

public class AtlasBuildMessage extends BuildMessage {

    String string = "You can build a dome";

    public AtlasBuildMessage(Space space) {
        super(space);
    }

    public String getString(){ return string;}
}
