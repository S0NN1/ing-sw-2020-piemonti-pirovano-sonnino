package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.messages.Message;
import it.polimi.ingsw.client.messages.actions.workerActions.AtlasBuildAction;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.server.answers.*;
import it.polimi.ingsw.server.answers.worker.*;

import java.beans.PropertyChangeSupport;

public class ActionHandler {

    private ModelView modelView;
    private Model model;
    private CLI cli;
    private PropertyChangeSupport view = new PropertyChangeSupport(this);

    public ActionHandler(CLI cli, Model model) {
        this.cli = cli;
        modelView = new ModelView();
        view.addPropertyChangeListener(cli);
        this.model = model;
    }

    /*public ActionHandler(GUI gui) {
        //TODO
    }*/


    public void fullGamePhase(Answer answer) {
        if (answer instanceof SelectSpacesMessage) {
            //print list on cli
        } else if (answer instanceof MoveMessage) {
            Move message = (Move) answer.getMessage();
            modelView.move(message.getOldPosition().getX(), message.getOldPosition().getY(),
                    message.getNewPosition().getX(), message.getNewPosition().getY());
        } else if (answer instanceof BuildMessage) {
            Couple message = ((BuildMessage) answer).getMessage();
            boolean dome = ((BuildMessage) answer).getDome();
            modelView.build(message.getX(), message.getY(), dome);
        } else if (answer instanceof ApolloMoveMessage) {
            Move myMove = ((ApolloMoveMessage) answer).getMessage();
            Move otherMove = ((ApolloMoveMessage) answer).getOtherMove();
            modelView.apolloDoubleMove(myMove.getOldPosition().getX(), myMove.getOldPosition().getY(),
                    otherMove.getOldPosition().getX(), otherMove.getOldPosition().getY());
        } else if (answer instanceof MinotaurMoveMessage) {
            Move myMove = ((MinotaurMoveMessage) answer).getMessage();
            Move otherMove = ((MinotaurMoveMessage) answer).getOtherMove();
            modelView.minotaurDoubleMove(myMove.getOldPosition().getX(), myMove.getOldPosition().getY(),
                    otherMove.getOldPosition().getX(), otherMove.getOldPosition().getY(),
                    otherMove.getNewPosition().getX(), otherMove.getNewPosition().getY());
        }
    }

    public void initialGamePhase(Answer answer) {
        if (answer instanceof RequestPlayersNumber) {
            view.firePropertyChange("initialPhase", null, "RequestPlayerNumber");
        } else if (answer instanceof RequestColor) {
            view.firePropertyChange("initialPhase", null, "RequestColor");
        } else if (answer instanceof ChallengerMessages) {
            view.firePropertyChange("initialPhase", null, "GodRequest");
        }
    }

    //TODO ADD CALLS TO CLI/GUI'S METHOD
    /**
     * Handles the answer received from the server. It calls the client interface passing values relying on the type
     * of answer the server has sent.
     */

    public void answerHandler(){
        Answer answer = model.getServerAnswer();
        if(model.getGamePhase()==0) {
            initialGamePhase(answer);
        } else if(model.getGamePhase()==1) {
            fullGamePhase(answer);
        }
        if(answer instanceof CustomMessage) {
            view.firePropertyChange("customMessage", null, answer.getMessage());
            model.setCanInput(((CustomMessage) answer).canInput());
        }
        else if(answer instanceof GameError) {
            view.firePropertyChange("gameError", null, answer);
        }
        else if(answer instanceof ConnectionMessage) {
            view.firePropertyChange("connectionClosed", null, answer.getMessage());
            cli.toggleActiveGame(false);
        }
    }
}
