package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.server.answers.*;
import it.polimi.ingsw.server.answers.turn.EndTurnMessage;
import it.polimi.ingsw.server.answers.turn.ModifiedTurnMessage;
import it.polimi.ingsw.server.answers.turn.StartTurnMessage;
import it.polimi.ingsw.server.answers.turn.WorkersRequestMessage;
import it.polimi.ingsw.server.answers.worker.*;
import javafx.application.Platform;

import java.beans.PropertyChangeSupport;

/**
 * ActionHandler class handles the answers from the server notifying the correct part of the GUI or
 * CLI through property change listeners.
 *
 * @author Luca Pirovano, Alice Piemonti, Nicolò Sonnino
 */
public class ActionHandler {

  public static final String FIRST_BOARD_UPDATE = "firstBoardUpdate";
  public static final String BOARD_UPDATE = "boardUpdate";
  private static final String MAIN_SCENE_FXML = "mainScene.fxml";
  public static final String MODIFIED_TURN_NO_UPDATE = "modifiedTurnNoUpdate";
  public static final String SELECT = "select";
  private final ModelView modelView;
  private final PropertyChangeSupport view = new PropertyChangeSupport(this);
  private CLI cli;
  private GUI gui;

  /**
   * Constructor of the ActionHandler in case players are using the CLI.
   *
   * @param cli of type CLI - the command line interface reference.
   * @param modelView of type ModelView - the structure, stored into the client, containing simple
   *     logic of the model.
   */
  public ActionHandler(CLI cli, ModelView modelView) {
    this.cli = cli;
    view.addPropertyChangeListener(cli);
    this.modelView = modelView;
  }

  /**
   * Constructor of the ActionHandler in case players are using the GUI.
   *
   * @param gui of type GUI - the graphical user interface reference.
   * @param modelView of type ModelView - the structure, stored into the client, containing simple
   *     logic of the model.
   */
  public ActionHandler(GUI gui, ModelView modelView) {
    this.gui = gui;
    view.addPropertyChangeListener(gui);
    this.modelView = modelView;
  }

  /**
   * Method fullGamePhase handles an answer of the full game phase (like a move, build or god-use
   * action).
   *
   * @param answer of type Answer - the answer received from the server.
   */
  public void fullGamePhase(Answer answer) {
    ClientBoard clientBoard = modelView.getBoard();
    if (answer instanceof SelectSpacesMessage) {
      if (((SelectSpacesMessage) answer).getMessage() == null) {
        modelView.activateInput();
        view.firePropertyChange("noPossibleMoves", null, null);
      } else {
        fireSelectSpaces((SelectSpacesMessage) answer);
      }
      return;
    }
    if (answer instanceof ModifiedTurnMessage) {
      modifiedTurnAction((ModifiedTurnMessage) answer);
    } else if (answer instanceof WorkersRequestMessage) {
      fireSelectWorker();
    } else if (answer instanceof EndTurnMessage) {
      fireEndTurn(answer);
    } else {
      if (answer instanceof MoveMessage) {
        updateClientBoardMove(answer, clientBoard);
        if (Constants.getDoubleMoveGods().contains(modelView.getGod())
            && modelView.getTurnPhase() == 1) {
          view.firePropertyChange(BOARD_UPDATE, new boolean[] {true, true, false}, null);
        } else if (modelView.isTurnActive()
            && Constants.getSpecialBuildGods().contains(modelView.getGod())) {
          view.firePropertyChange(BOARD_UPDATE, new boolean[] {false, true, false, true}, null);
        } else if (modelView.isTurnActive() && modelView.getTurnPhase() == 0) {
          view.firePropertyChange(BOARD_UPDATE, new boolean[] {true, false, false, false}, null);
        } else view.firePropertyChange(BOARD_UPDATE, new boolean[] {false, true, false}, null);
      } else if (answer instanceof BuildMessage) {
        Couple message = ((BuildMessage) answer).getMessage();
        boolean dome = ((BuildMessage) answer).getDome();
        clientBoard.build(
            message.getRow(), message.getColumn(), dome, ((BuildMessage) answer).getAction());
        checkTurnActive();
        fireBuildMenu();
      } else if (answer instanceof DoubleMoveMessage) {
        String message = ((DoubleMoveMessage) answer).getMessage();
        defineDoubleMove((DoubleMoveMessage) answer, clientBoard, message);
        view.firePropertyChange(BOARD_UPDATE, new boolean[] {false, true, false}, null);
      }
    }
  }

  /**
   * Method fireBuildMenu defines booleans used for the method updateCLI in CLI.java based on the
   * type of god.
   */
  private void fireBuildMenu() {
    if (Constants.getDoubleBuildGods().contains(modelView.getGod())
        && modelView.getTurnPhase() == 3) {
      view.firePropertyChange(BOARD_UPDATE, new boolean[] {false, false, true}, null);
    } else if (Constants.getDoubleBuildGods().contains(modelView.getGod())) {
      view.firePropertyChange(BOARD_UPDATE, new boolean[] {false, true, true}, null);
    } else if (Constants.getAlternatePhaseGods().contains(modelView.getGod())
        && modelView.getTurnPhase() == 1) {
      view.firePropertyChange(BOARD_UPDATE, new boolean[] {true, false, false}, null);
    } else if (Constants.getEndActionGods().contains(modelView.getGod())) {
      view.firePropertyChange(BOARD_UPDATE, new boolean[] {false, false, true, false}, null);
    } else {
      view.firePropertyChange(BOARD_UPDATE, new boolean[] {false, false, true}, null);
    }
  }

  /**
   * Method modifiedTurnAction handles messages from gods with turn phases different from normal
   * ones and fires commands to CLI.
   *
   * @param answer of type ModifiedTurnMessage - the answer received from the server.
   */
  private void modifiedTurnAction(ModifiedTurnMessage answer) {
    if (answer.getAction() == null) {
      modelView.activateInput();
      view.firePropertyChange(
          BOARD_UPDATE, new boolean[] {true, true, false}, answer); // PROMETHEUS ACTION
    } else if (answer.getAction().equals(Action.SELECT_MOVE)) {
      modelView.activateInput();
      view.firePropertyChange(
          MODIFIED_TURN_NO_UPDATE, new boolean[] {true, true, false}, answer); // DOUBLE MOVE
    } else if (answer.getAction().equals(Action.SELECT_BUILD)) {
      modelView.activateInput();
      view.firePropertyChange(
          MODIFIED_TURN_NO_UPDATE, new boolean[] {false, true, true}, answer); // DOUBLE BUILD
    } else if (answer.getAction().equals(Action.SELECT_FORCE_WORKER)) {
      modelView.activateInput();
      view.firePropertyChange(BOARD_UPDATE, new boolean[] {true, false, false, true}, answer);
    } else if (answer.getAction().equals(Action.SELECT_REMOVE)) {
      modelView.activateInput();
      view.firePropertyChange(
          MODIFIED_TURN_NO_UPDATE, new boolean[] {false, false, true, true}, answer);
    }
  }

  /**
   * Method defineDoubleMove defines the type of the move message received based on gods with the
   * ability to move other ones.
   *
   * @param answer of type DoubleMoveMessage - the answer received from the server.
   * @param clientBoard of type ClientBoard - the clientBoard.
   * @param message of type String - the message forwarded to CLI.
   */
  private void defineDoubleMove(DoubleMoveMessage answer, ClientBoard clientBoard, String message) {
    if (message.equals("ApolloDoubleMove")) {
      fireApolloMove(answer, clientBoard);
    } else if (message.equals("MinotaurDoubleMove")) {
      fireMinotaurMove(answer, clientBoard);
    }
  }

  /**
   * Method updateClientBoardMove updates clientBoard after receiving a move answer.
   *
   * @param answer of type Answer - the answer received from the server.
   * @param clientBoard of type ClientBoard - the clientBoard.
   */
  private void updateClientBoardMove(Answer answer, ClientBoard clientBoard) {
    Move message = (Move) answer.getMessage();
    clientBoard.move(
        message.getOldPosition().getRow(),
        message.getOldPosition().getColumn(),
        message.getNewPosition().getRow(),
        message.getNewPosition().getColumn());
    checkTurnActive();
  }

  /**
   * Method fireMinotaurMove updates the clientBoard after receiving a Minotaur's move answer.
   *
   * @param answer of type DoubleMoveMessage - the answer received from the server.
   * @param clientBoard of type ClientBoard - the clientBoard.
   */
  private void fireMinotaurMove(DoubleMoveMessage answer, ClientBoard clientBoard) {
    Move myMove = answer.getMyMove();
    Move otherMove = answer.getOtherMove();
    clientBoard.minotaurDoubleMove(
        myMove.getOldPosition().getRow(),
        myMove.getOldPosition().getColumn(),
        otherMove.getOldPosition().getRow(),
        otherMove.getOldPosition().getColumn(),
        otherMove.getNewPosition().getRow(),
        otherMove.getNewPosition().getColumn());
    checkTurnActive();
  }

  /**
   * Method fireApolloMove updates the clientBoard after receiving a Apollo's move answer.
   *
   * @param answer of type DoubleMoveMessage - the answer received from the server.
   * @param clientBoard of type ClientBoard - the clientBoard.
   */
  private void fireApolloMove(DoubleMoveMessage answer, ClientBoard clientBoard) {
    Move myMove = answer.getMyMove();
    Move otherMove = answer.getOtherMove();
    clientBoard.apolloDoubleMove(
        myMove.getOldPosition().getRow(),
        myMove.getOldPosition().getColumn(),
        otherMove.getOldPosition().getRow(),
        otherMove.getOldPosition().getColumn());
    checkTurnActive();
  }

  /**
   * Method fireEndTurn updates the clientBoard after receiving an end turn answer.
   *
   * @param answer of type Answer - the answer received from the server.
   */
  private void fireEndTurn(Answer answer) {
    modelView.setTurnActive(false);
    modelView.setTurnPhase(0);
    modelView.setActiveWorker(0);
    modelView.deactivateInput();
    if (cli != null) {
      view.firePropertyChange(FIRST_BOARD_UPDATE, null, null);
    }
    view.firePropertyChange("end", null, answer);
  }

  /**
   * Method fireSelectSpaces fires selected spaces from the answer to modelView and updates CLI.
   *
   * @param answer of type SelectSpacesMessage - the answer received from the server.
   */
  private void fireSelectSpaces(SelectSpacesMessage answer) {
    modelView.setSelectSpaces(answer.getMessage());
    modelView.activateInput();
    if (answer.getAction().equals(Action.SELECT_BUILD)) {
      checkTurnActive();
      view.firePropertyChange(SELECT, new boolean[] {false, true, false}, null);
    } else if (answer.getAction().equals(Action.SELECT_MOVE)) {
      checkTurnActive();
      view.firePropertyChange(SELECT, new boolean[] {true, false, false}, null);
    } else if (answer.getAction().equals(Action.SELECT_FORCE_WORKER)) {
      checkTurnActive();
      modelView.setGodPowerActive(true);
      view.firePropertyChange(SELECT, new boolean[] {false, false, false, true}, null);
      return;
    } else if (answer.getAction().equals(Action.SELECT_REMOVE)) {
      checkTurnActive();
      modelView.setGodPowerActive(true);
      view.firePropertyChange(SELECT, new boolean[] {false, false, false, true}, null);
    }

    modelView.setTurnPhase(modelView.getTurnPhase() + 1);
  }

  /** Method fireSelectWorker updates CLI after a StartTurnMessage. */
  private void fireSelectWorker() {
    modelView.setTurnActive(true);
    modelView.setCurrentPlayer(modelView.getPlayerName());
    modelView.activateInput();
    if (cli != null) {
      view.firePropertyChange(FIRST_BOARD_UPDATE, null, null);
    }
    view.firePropertyChange("selectWorker", null, null);
  }

  /** Method checkTurnActive checks if turn is active. */
  private void checkTurnActive() {
    if (modelView.isTurnActive()) {
      modelView.activateInput();
    }
  }

  /**
   * Method initialGamePhase handles the server answers of the initial game phase and notifies the
   * view relying on the server request through a property change listener.
   *
   * @param answer of type Answer - the answer received from the server.
   */
  public void initialGamePhase(Answer answer) {
    String initial = "initialPhase";
    if (answer instanceof RequestPlayersNumber) {
      view.firePropertyChange(initial, null, "RequestPlayerNumber");
    } else if (answer instanceof ColorMessage) {
      if (((ColorMessage) answer).getMessage() != null) {
        view.firePropertyChange(initial, null, "RequestColor");
      } else {
        modelView.setColor(((ColorMessage) answer).getColor());
      }
    } else if (answer instanceof ChallengerMessages) {
      if (((ChallengerMessages) answer).getChosenGod() != null) {
        modelView.setGod(((ChallengerMessages) answer).getChosenGod());
        modelView.setGodDesc(((ChallengerMessages) answer).getGodDesc());
        return;
      }
      view.firePropertyChange(initial, null, "GodRequest");
    } else if (answer instanceof WorkerPlacement) {
      modelView.setTurnActive(true);
      view.firePropertyChange(initial, null, "WorkerPlacement");
    } else if (answer instanceof WorkersRequestMessage) {
      fireSelectWorker();
    } else if (answer instanceof SetWorkersMessage) {
      SetWorkersMessage message = (SetWorkersMessage) answer;
      modelView
          .getBoard()
          .setColor(
              message.getWorker1().getRow(),
              message.getWorker1().getColumn(),
              message.getMessage());

      modelView
          .getBoard()
          .setWorkerNum(message.getWorker1().getRow(), message.getWorker1().getColumn(), 1);
      modelView
          .getBoard()
          .setColor(
              message.getWorker2().getRow(),
              message.getWorker2().getColumn(),
              message.getMessage());
      modelView
          .getBoard()
          .setWorkerNum(message.getWorker2().getRow(), message.getWorker2().getColumn(), 2);

      modelView.setTurnActive(false);
      if (cli != null) {
        view.firePropertyChange(FIRST_BOARD_UPDATE, null, null);
      } else if (gui != null) {
        Platform.runLater(
            () -> {
              ((MainGuiController) gui.getControllerFromName(MAIN_SCENE_FXML))
                  .setWorker(message.getWorker1().getRow(), message.getWorker1().getColumn());
              ((MainGuiController) gui.getControllerFromName(MAIN_SCENE_FXML))
                  .setWorker(message.getWorker2().getRow(), message.getWorker2().getColumn());
            });
      }
    } else if (answer instanceof MatchStartedMessage) {
      modelView.setPlayerMapColor(((MatchStartedMessage) answer).getPlayerMapColor());
      modelView.setPlayerMapGod(((MatchStartedMessage) answer).getPlayerMapGod());
      view.firePropertyChange("matchStarted", null, null);
      modelView.setGamePhase(1);
    }
  }

  /**
   * Method answerHandler handles the answer received from the server. It calls the client interface
   * passing values relying on the type of answer the server has sent.
   */
  public void answerHandler() {
    Answer answer = modelView.getServerAnswer();
    if (modelView.getGamePhase() == 0) {
      initialGamePhase(answer);
    }
    if (answer instanceof WinMessage) {
      view.firePropertyChange("win", null, null);
      return;
    } else if (answer instanceof StartTurnMessage) {
      modelView.setCurrentPlayer(answer.getMessage().toString());
      view.firePropertyChange("newPlayerTurn", new boolean[] {false, false, false, false}, null);
    } else if (answer instanceof PlayerLostMessage) {
      if (((PlayerLostMessage) answer).getLoser().equalsIgnoreCase(modelView.getPlayerName())) {
        view.firePropertyChange("singleLost", null, null);
      } else {
        fireOtherPlayerLost((PlayerLostMessage) answer);
      }
    } else if (answer instanceof LoseMessage) {
      view.firePropertyChange("lose", null, ((LoseMessage) answer).getWinner());
    }
    if (answer instanceof CustomMessage) {
      fireCustomMessage(answer);
    } else if (answer instanceof GameError) {
      fireGameError(answer);
    } else if (answer instanceof ConnectionMessage) {
      if (cli != null) {
        fireClosedConnectionCli(answer);
      }
    } else if (modelView.getGamePhase() == 1) {
      fullGamePhase(answer);
    }
  }

  /**
   * Method fireOtherPlayerLost unregisters loser from modelView and updates CLI after receiving a
   * message with the loser.
   *
   * @param answer of type PlayerLostMessage - the answer received from the server.
   */
  private void fireOtherPlayerLost(PlayerLostMessage answer) {
    if (gui != null) {
      gui.removeWorkers(answer.getLoserColor());
    }
    view.firePropertyChange("otherLost", null, answer.getLoser());
  }

  /**
   * Method fireClosedConnectionCli closes connection to client.
   *
   * @param answer of type Answer - close connection answer from the server.
   */
  private void fireClosedConnectionCli(Answer answer) {
    view.firePropertyChange("connectionClosed", null, answer);
    cli.toggleActiveGame(false);
  }

  /**
   * Method fireGameError prints an error.
   *
   * @param answer of type Answer - the answer from the server.
   */
  private void fireGameError(Answer answer) {
    modelView.activateInput();
    view.firePropertyChange("gameError", null, answer);
  }

  /**
   * Method fireCustomMessage fires customMessage.
   *
   * @param answer of type Answer - the custom message answer from the server.
   */
  private void fireCustomMessage(Answer answer) {
    view.firePropertyChange("customMessage", null, answer.getMessage());
    modelView.setCanInput(((CustomMessage) answer).canInput());
  }
}
