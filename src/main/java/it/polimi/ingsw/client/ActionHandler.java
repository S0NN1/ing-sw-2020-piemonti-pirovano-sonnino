package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.server.answers.MatchStartedMessage;
import it.polimi.ingsw.server.answers.*;
import it.polimi.ingsw.server.answers.turn.EndTurnMessage;
import it.polimi.ingsw.server.answers.turn.WorkerConfirmedMessage;
import it.polimi.ingsw.server.answers.turn.WorkersRequestMessage;
import it.polimi.ingsw.server.answers.worker.*;

import java.beans.PropertyChangeSupport;

/**
 * Handles the answers from the server, notifying the correct part of the GUI or CLI through property change
 * listeners.
 * @author Luca Pirovano, Alice Piemonti
 */
public class ActionHandler {

    public static final String FIRST_BOARD_UPDATE = "firstBoardUpdate";
    private final ModelView modelView;
    private CLI cli;
    private GUI gui;
    private final PropertyChangeSupport view = new PropertyChangeSupport(this);

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
            if(((SelectSpacesMessage)answer).getMessage()==null){
                modelView.activateInput();
                view.firePropertyChange("noPossibleMoves", null, null);
            }
            else {
                fireSelectSpaces((SelectSpacesMessage) answer);
            }
        }
        else if(answer instanceof WorkersRequestMessage){
            fireSelectWorker();
        }
        else if (answer instanceof EndTurnMessage){
            fireEndTurn(answer);
        }
        else {
            String boardUpdate = "boardUpdate";
            if (answer instanceof MoveMessage) {
                updateClientBoardMove(answer, clientBoard);
            } else if(answer instanceof WorkerConfirmedMessage) {
                view.firePropertyChange(boardUpdate, null, null);
                modelView.activateInput();
                return;
            } else if (answer instanceof BuildMessage) {
                Couple message = ((BuildMessage) answer).getMessage();
                boolean dome = ((BuildMessage) answer).getDome();
                clientBoard.build(message.getX(), message.getY(), dome);
                checkTurnActiveBuild();
            } else if (answer instanceof DoubleMoveMessage) {
                String message = ((DoubleMoveMessage) answer).getMessage();
                defineDoubleMove((DoubleMoveMessage) answer, clientBoard, message);
            }
            view.firePropertyChange(boardUpdate, null,null);
        }

    }

    private void defineDoubleMove(DoubleMoveMessage answer, ClientBoard clientBoard, String message) {
        if (message.equals("ApolloDoubleMove")) {
            fireApolloMove(answer, clientBoard);
        } else if (message.equals("MinotaurDoubleMove")) {
            fireMinotaurMove(answer, clientBoard);
        }
    }

    private void updateClientBoardMove(Answer answer, ClientBoard clientBoard) {
        Move message = (Move) answer.getMessage();
        clientBoard.move(message.getOldPosition().getX(), message.getOldPosition().getY(),
                message.getNewPosition().getX(), message.getNewPosition().getY());
        checkTurnActiveMove();
    }

    private void fireMinotaurMove(DoubleMoveMessage answer, ClientBoard clientBoard) {
        Move myMove = answer.getMyMove();
        Move otherMove = answer.getOtherMove();
        clientBoard.minotaurDoubleMove(myMove.getOldPosition().getX(), myMove.getOldPosition().getY(),
                otherMove.getOldPosition().getX(), otherMove.getOldPosition().getY(),
                otherMove.getNewPosition().getX(), otherMove.getNewPosition().getY());
        checkTurnActiveMove();
    }

    private void fireApolloMove(DoubleMoveMessage answer, ClientBoard clientBoard) {
        Move myMove = answer.getMyMove();
        Move otherMove = answer.getOtherMove();
        clientBoard.apolloDoubleMove(myMove.getOldPosition().getX(), myMove.getOldPosition().getY(),
                otherMove.getOldPosition().getX(), otherMove.getOldPosition().getY());
        checkTurnActiveMove();
    }

    private void fireEndTurn(Answer answer) {
        modelView.setTurnActive(false);
        modelView.setTurnPhase(0);
        modelView.setActiveWorker(0);
        modelView.deactivateInput();
        view.firePropertyChange("boardUpdate", null, null);
        view.firePropertyChange("end", null, answer.getMessage());
    }

    private void fireSelectSpaces(SelectSpacesMessage answer) {
        modelView.setSelectSpaces(answer.getMessage());
        modelView.setMoveSelected(true);
        modelView.setBuildSelected(true);//TODO  NON SONO SICURO CHE RESETTANDO FUNZIONI
        modelView.activateInput();
        view.firePropertyChange("select", null, null);
    }

    private void fireSelectWorker() {
        modelView.setTurnActive(true);
        modelView.activateInput();
        view.firePropertyChange(FIRST_BOARD_UPDATE, null, null);
        view.firePropertyChange("selectWorker", null, null);
    }

    private void checkTurnActiveBuild() {
        if(modelView.isTurnActive()){
            modelView.setTurnPhase(modelView.getTurnPhase()+1);
            modelView.setBuildSelected(false);
            modelView.activateInput();
        }
    }

    private void checkTurnActiveMove() {
        if (modelView.isTurnActive()) {
            modelView.setTurnPhase(modelView.getTurnPhase() + 1);
            modelView.setMoveSelected(false);
            modelView.activateInput();
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
            if(((ChallengerMessages)answer).getChosenGod()!=null){
                modelView.setGod(((ChallengerMessages)answer).getChosenGod());
                modelView.setGodDesc(((ChallengerMessages)answer).getGodDesc());
                return;
            }
            view.firePropertyChange(initial, null, "GodRequest");
        } else if (answer instanceof WorkerPlacement) {
            modelView.setTurnActive(true);
            view.firePropertyChange(initial, null, "WorkerPlacement");
        }
        else if(answer instanceof WorkersRequestMessage){
            fireSelectWorker();
        }
        else if(answer instanceof SetWorkersMessage) {
            SetWorkersMessage message = (SetWorkersMessage) answer;
            modelView.getBoard().setColor(message.getWorker1().getX(), message.getWorker1().getY(), message.getMessage());
            modelView.getBoard().setWorkerNum(message.getWorker1().getX(), message.getWorker1().getY(), 1);
            modelView.getBoard().setColor(message.getWorker2().getX(), message.getWorker2().getY(), message.getMessage());
            modelView.getBoard().setWorkerNum(message.getWorker2().getX(), message.getWorker2().getY(), 2);
            modelView.setTurnActive(false);
            view.firePropertyChange(FIRST_BOARD_UPDATE, null, null);
        }
        else if(answer instanceof MatchStartedMessage) {
            modelView.setGamePhase(1);
        }
    }

    /**
     * Handles the answer received from the server. It calls the client interface passing values relying on the type
     * of answer the server has sent.
     */

    public void answerHandler(){
        Answer answer = modelView.getServerAnswer();
        if(modelView.getGamePhase()==0) {
            initialGamePhase(answer);
        }
        else if(modelView.getGamePhase()==1) {
            fullGamePhase(answer);
        }
        if(answer instanceof WinMessage) {
            view.firePropertyChange("win", null, null);
        }
        else if(answer instanceof PlayerLostMessage) {
            if(((PlayerLostMessage) answer).getLoser().equalsIgnoreCase(modelView.getPlayerName())){
                view.firePropertyChange("singleLost", null, null);
            }
            else{
                fireOtherPlayerLost((PlayerLostMessage) answer);
            }
        }
        else if(answer instanceof LoseMessage) {
            view.firePropertyChange("lose", null, ((LoseMessage)answer).getWinner());
        }
        if(answer instanceof CustomMessage) {
            fireCustomMessage(answer);
        }
        else if(answer instanceof GameError) {
            fireGameError(answer);
        }
        else if(answer instanceof ConnectionMessage) {
            if(cli!=null) {
                fireClosedConnectionCli(answer);
            }
            else if(gui!=null) {
                //TODO
            }
        }
    }

    private void fireOtherPlayerLost(PlayerLostMessage answer) {
        modelView.unregisterPlayer(answer.getLoserColor());
        view.firePropertyChange("otherLost", null,  answer.getLoser());
    }

    private void fireClosedConnectionCli(Answer answer) {
        view.firePropertyChange("connectionClosed", null, answer.getMessage());
        cli.toggleActiveGame(false);
    }

    private void fireGameError(Answer answer) {
        modelView.activateInput();
        view.firePropertyChange("gameError", null, answer);
    }

    private void fireCustomMessage(Answer answer) {
        view.firePropertyChange("customMessage", null, answer.getMessage());
        modelView.setCanInput(((CustomMessage) answer).canInput());
    }


}
