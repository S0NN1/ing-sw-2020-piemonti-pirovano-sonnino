package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.server.answers.*;
import it.polimi.ingsw.server.answers.worker.*;

import java.beans.PropertyChangeSupport;

/**
 * Handles the answers from the server, notifying the correct part of the GUI or CLI through property change
 * listeners.
 * @author Luca Pirovano, Alice Piemonti
 */
public class ActionHandler {

    private ModelView modelView;
    private CLI cli;
    private GUI gui;
    private PropertyChangeSupport view = new PropertyChangeSupport(this);

    /**
     * Constructor of the ActionHandler in case players are using the CLI.
     * @param cli the command line interface reference.
     * @param modelView the client model of the game.
     */
    public ActionHandler(CLI cli, ModelView modelView) {
        this.cli = cli;
        view.addPropertyChangeListener(cli);
        this.modelView = modelView;
    }

    /**
     * Constructor of the ActionHandler in case players are using the GUI.
     * @param gui the graphical user interface reference.
     * @param modelView the client model of the game.
     */
    public ActionHandler(GUI gui, ModelView modelView) {
        this.gui = gui;
        view.addPropertyChangeListener(gui);
        this.modelView = modelView;
    }

    /**
     * Handles an answer of the full game phase (like a move, build or god-use action).
     * @param answer the answer received from the server.
     */
    public void fullGamePhase(Answer answer) {
        ClientBoard clientBoard = modelView.getBoard();
        if (answer instanceof SelectSpacesMessage) {
            view.firePropertyChange("select", null, answer.getMessage());
        }
        else{
            if (answer instanceof MoveMessage) {
                Move message = (Move) answer.getMessage();
                clientBoard.move(message.getOldPosition().getX(), message.getOldPosition().getY(),
                        message.getNewPosition().getX(), message.getNewPosition().getY());
            } else if (answer instanceof BuildMessage) {
                Couple message = ((BuildMessage) answer).getMessage();
                boolean dome = ((BuildMessage) answer).getDome();
                clientBoard.build(message.getX(), message.getY(), dome);
            } else if (answer instanceof DoubleMoveMessage) {
                String message = ((DoubleMoveMessage) answer).getMessage();
                if (message.equals("ApolloDoubleMove")) { //type Apollo
                    Move myMove = ((DoubleMoveMessage) answer).getMyMove();
                    Move otherMove = ((DoubleMoveMessage) answer).getOtherMove();
                    clientBoard.apolloDoubleMove(myMove.getOldPosition().getX(), myMove.getOldPosition().getY(),
                            otherMove.getOldPosition().getX(), otherMove.getOldPosition().getY());
                } else if (message.equals("MinotaurDoubleMove")) { //type Minotaur
                    Move myMove = ((DoubleMoveMessage) answer).getMyMove();
                    Move otherMove = ((DoubleMoveMessage) answer).getOtherMove();
                    clientBoard.minotaurDoubleMove(myMove.getOldPosition().getX(), myMove.getOldPosition().getY(),
                            otherMove.getOldPosition().getX(), otherMove.getOldPosition().getY(),
                            otherMove.getNewPosition().getX(), otherMove.getNewPosition().getY());
                }
            }
            view.firePropertyChange("boardUpdate", null,null);
        }

    }

    /**
     * Handles the server answers of the initial game phase; notifies the view relying on the server request through
     * a property change listener.
     * @param answer the answer received from the server.
     */
    public void initialGamePhase(Answer answer) {
        String initial = "initialPhase";
        if (answer instanceof RequestPlayersNumber) {
            view.firePropertyChange(initial, null, "RequestPlayerNumber");
        } else if (answer instanceof RequestColor) {
            view.firePropertyChange(initial, null, "RequestColor");
        } else if (answer instanceof ChallengerMessages) {
            view.firePropertyChange(initial, null, "GodRequest");
        } else if (answer instanceof WorkerPlacement) {
            view.firePropertyChange(initial, null, "WorkerPlacement");
        }
        else if(answer instanceof SetWorkersMessage) {
            SetWorkersMessage message = (SetWorkersMessage) answer;
            modelView.getBoard().setColor(message.getWorker1().getX(), message.getWorker1().getY(), message.getMessage());
            modelView.getBoard().setColor(message.getWorker2().getX(), message.getWorker2().getY(), message.getMessage());
            view.firePropertyChange("boardUpdate", null, null);
        }

    }

    //TODO ADD CALLS TO CLI/GUI'S METHOD
    /**
     * Handles the answer received from the server. It calls the client interface passing values relying on the type
     * of answer the server has sent.
     */

    public void answerHandler(){
        Answer answer = modelView.getServerAnswer();
        if(modelView.getGamePhase()==0) {
            initialGamePhase(answer);
        } else if(modelView.getGamePhase()==1) {
            fullGamePhase(answer);
        }
        if(answer instanceof CustomMessage) {
            view.firePropertyChange("customMessage", null, answer.getMessage());
            modelView.setCanInput(((CustomMessage) answer).canInput());
        }
        else if(answer instanceof GameError) {
            view.firePropertyChange("gameError", null, answer);
        }
        else if(answer instanceof ConnectionMessage) {
            if(cli!=null) {
                view.firePropertyChange("connectionClosed", null, answer.getMessage());
                cli.toggleActiveGame(false);
            }
        }
    }
}
