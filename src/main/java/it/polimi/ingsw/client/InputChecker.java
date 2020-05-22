package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Disconnect;
import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.WorkerSetupMessage;
import it.polimi.ingsw.client.messages.actions.turnactions.StartTurnAction;
import it.polimi.ingsw.client.messages.actions.workeractions.*;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.Card;

import java.util.Objects;


/**
 * InputChecker class checks the correctness of the input received from the ActionParser, returning either true or
 * false.
 *
 * @author Luca Pirovano, Nicolò Sonnino
 * @version 2.0.0
 */
public class InputChecker {
    public static final String ERR_NONEXISTENT_UNREACHABLE = "Non-existent or unreachable cell, operation not " +
            "permitted!";
    public static final String ERR_CELL_OCCUPIED = "Cell already occupied, operation not permitted!";
    public static final String ERR_INCORRECT_ACTION = "Incorrect action, wrong turn phase!";
    public static final String ERR_WORKER_NOT_SELECTED = "Worker not selected, operation not permitted!";
    public static final String CELL_WITH_DOME = "Cell with dome, operation not permitted!";
    private static final String GOD_NOT_FOUND = "Not existing god with your input's name.";
    private static final String RED = Constants.ANSI_RED;
    private static final String RST = Constants.ANSI_RESET;
    private final ConnectionSocket connection;
    private final ModelView modelView;


    /**
     * Constructor InputChecker creates a new InputChecker instance.
     *
     * @param connection of type ConnectionSocket the socket used for the connection between client and server.
     * @param modelView of type ModelView the structure, stored into the client, containing simple logic of the model.
     */
    public InputChecker(ConnectionSocket connection, ModelView modelView) {
        this.connection = connection;
        this.modelView = modelView;
    }


    /**
     * Method desc validates a "GODDESC <god-name>" message type.
     *
     * @param in of type String[] the user input under array representation.
     * @return ChallengerPhaseAction the correct ChallengerPhaseAction, null otherwise.
     */

    public ChallengerPhaseAction desc(String[] in) {
        ChallengerPhaseAction challengerPhaseAction;
        try {
            challengerPhaseAction = new ChallengerPhaseAction("DESC", Card.parseInput(in[1]));
        } catch (IllegalArgumentException e) {
            System.out.println(RED + GOD_NOT_FOUND + RST);
            return null;
        }
        return challengerPhaseAction;
    }

    /**
     * Validates an "ADDGOD <god-name>" message type.
     *
     * @param in of type String[] the user input under array representation.
     * @return ChallengerPhaseAction the correct ChallengerPhaseAction, null otherwise.
     */
    public ChallengerPhaseAction addGod(String[] in) {
        ChallengerPhaseAction action;
        try {
            action = new ChallengerPhaseAction("ADD", Card.parseInput(in[1]));
        } catch (IllegalArgumentException e) {
            System.out.println(RED + GOD_NOT_FOUND + RST);
            return null;
        }
        return action;
    }


    /**
     * Method choose validates a "CHOOSE <god-name>" message type.
     *
     * @param in of type String[] the user input under array representation.
     * @return ChallengerPhaseAction the correct ChallengerPhaseAction, null otherwise.
     */

    public ChallengerPhaseAction choose(String[] in) {
        ChallengerPhaseAction action;
        try {
            action = new ChallengerPhaseAction("CHOOSE", Card.parseInput(in[1]));
        } catch (IllegalArgumentException e) {
            System.out.println(RED + GOD_NOT_FOUND + RST);
            return null;
        }
        return action;
    }

  /**
   * Method starter validates a "STARTER <player-number>" message type.
   *
   * @param in of type String[] the user input under array representation.
   * @return ChallengerPhaseAction the correct ChallengerPhaseAction, null otherwise.
   */
  public ChallengerPhaseAction starter(String[] in) {
        ChallengerPhaseAction action;
        try {
            int startingPlayer = Integer.parseInt(in[1]);
            action = new ChallengerPhaseAction(startingPlayer);
        } catch (NumberFormatException e) {
            System.out.println(RED + "Error: it must be a numeric value, please try again." + RST);
            return null;
        }
        return action;
    }

    /**
     * Method set validates a "SET <x1> <y1> <x2> <y2>" worker placement message type.
     *
     * @param in of type String[] the user input under array representation.
     * @return ChallengerPhaseAction the correct ChallengerPhaseAction, null otherwise.
     */
    public WorkerSetupMessage set(String[] in) {
        WorkerSetupMessage action;
        try {
            action = new WorkerSetupMessage(in);
            int row1 = Integer.parseInt(in[1]);
            int column1 = Integer.parseInt(in[2]);
            int row2 = Integer.parseInt(in[3]);
            int column2 = Integer.parseInt(in[4]);
            if (row1 < Constants.GRID_MIN_SIZE || row1 >= Constants.GRID_MAX_SIZE || column1 < Constants.GRID_MIN_SIZE
                    || column1 >= Constants.GRID_MAX_SIZE || row2 < Constants.GRID_MIN_SIZE ||
                    row2 >= Constants.GRID_MAX_SIZE || column2 < Constants.GRID_MIN_SIZE ||
                    column2 >= Constants.GRID_MAX_SIZE) {
                System.err.println(ERR_NONEXISTENT_UNREACHABLE);
                return null;
            } else return action;
        } catch (NumberFormatException e) {
            System.out.println(RED + "Unknown input, please try again!" + RST);
            return null;
        }
    }

    /**
     * Method quit handles the program quit statement, terminating the local service with exit code 0.
     */
    public void quit() {
        connection.send(new Disconnect());
        System.err.println("Disconnected from the server.");
        System.exit(0);
    }

    /**
     * Method build checks if build action is permitted.
     *
     * @param turnPhase of type int the number indicating turn's phase.
     * @param row of type int the row of the selected cell.
     * @param column of type int the column of the selected cell.
     * @param activeWorker of type int the number indicating which worker was selected by player at the start of the
     *                     turn.
     * @return BuildAction the correct BuildAction, null otherwise.
     */
    public BuildAction build(int turnPhase, int row, int column, int activeWorker) {
        if (activeWorker == 0) {
            System.err.println(ERR_WORKER_NOT_SELECTED);
            return null;
        }
        Couple w = findWorker(activeWorker, modelView.getColor());
        BuildAction build = new BuildAction(row, column);
        if (turnPhase == 2 || Constants.getBuildPhaseGods().contains(modelView.getGod().toUpperCase())) {
            return getBuildAction(row, column, w, build);
        } else {
            System.err.println(ERR_INCORRECT_ACTION);
            return null;
        }
    }


    /**
     * Method build checks if select build action is permitted.
     *
     * @param turnPhase of type int the number indicating turn's phase.
     * @param activeWorker of type int the number indicating which worker was selected by player at the start of the
     *                     turn.
     * @return SelectBuildAction the correct SelectBuildAction, null otherwise.
     */
    public SelectBuildAction build(int turnPhase, int activeWorker) {
        if (activeWorker == 0) {
            System.err.println(ERR_WORKER_NOT_SELECTED);
            return null;
        } else if (turnPhase == 1 || Constants.getBuildPhaseGods().contains(modelView.getGod().toUpperCase())) {
            modelView.setBuildSelected(true);
            return new SelectBuildAction();
        } else {
            System.err.println(ERR_INCORRECT_ACTION);
            return null;
        }
    }

    public AtlasBuildAction atlasBuild(int turnPhase, int row, int column, int activeWorker) {
        if (activeWorker == 0) {
            System.err.println(ERR_WORKER_NOT_SELECTED);
            return null;
        }
        if (modelView.getGod().equalsIgnoreCase("ATLAS")) {
            Couple w = findWorker(activeWorker, modelView.getColor());
            AtlasBuildAction build = new AtlasBuildAction(row, column, true);
                if (turnPhase == 2) {
                    return (AtlasBuildAction) getBuildAction(row, column, w, build);
                }
                    else{
                        System.err.println(ERR_INCORRECT_ACTION);
                        return null;
                    }
                }
             else{
                System.err.println("Current god isn't ATLAS, operation not permitted!");
                return null;
            }
        }

    /**
     * Check if move is possible
     *
     * @param turnPhase    int
     * @param row            int
     * @param column            int
     * @param activeWorker int
     * @return moveAction
     */
    public MoveAction move(int turnPhase, int row, int column, int activeWorker) {
        if (activeWorker == 0) {
            System.err.println(ERR_WORKER_NOT_SELECTED);
            return null;
        }
        Couple worker = findWorker(activeWorker, modelView.getColor());
        MoveAction move = new MoveAction(row, column);
        if (turnPhase == 1 || Constants.getMovePhaseGods().contains(modelView.getGod().toUpperCase())) {
            if (isUnreachable(row, column, worker)) {
                System.out.println(RED + ERR_NONEXISTENT_UNREACHABLE + RST);
                return null;
            } else {
                if (modelView.getBoard().getGrid()[row][column].getColor() != null) {
                    return canMoveToOccupiedCell(move);
                } else {
                    return canReachCell(row, column, worker, move);
                }
            }
        } else {
            System.err.println(ERR_INCORRECT_ACTION);
            return null;
        }

    }

    private MoveAction canReachCell(int row, int column, Couple worker, MoveAction move) {
        if (modelView.getBoard().getGrid()[row][column].isDome()) {
            System.out.println(RED + CELL_WITH_DOME + RST);
            return null;
        } else {
            assert worker != null;
            if (modelView.getBoard().getGrid()[row][column].getLevel() -
                    modelView.getBoard().getGrid()[worker.getX()][worker.getY()].getLevel() >= 2) {
                System.out.println(RED + "Trying to move up to unreachable level, operation not permitted!" + RST);
                return null;
            } else return move;
        }
    }

    private MoveAction canMoveToOccupiedCell(MoveAction move) {
        if (!Constants.getMoveToCellOccupiedGods().contains(modelView.getGod().toUpperCase())) {
            System.out.println(RED + ERR_CELL_OCCUPIED + RST);
            return null;
        } else return move;
    }

    private BuildAction getBuildAction(int row, int column, Couple worker, BuildAction build) {
        if (isUnreachable(row, column, worker)) {
            System.out.println(RED + ERR_NONEXISTENT_UNREACHABLE + RST);
            return null;
        } else {
            if (modelView.getBoard().getGrid()[row][column].getColor() != null) {
                System.out.println(RED + ERR_CELL_OCCUPIED + RST);
                return null;
            } else {
                if (modelView.getBoard().getGrid()[row][column].getLevel() == 4 ||
                        modelView.getBoard().getGrid()[row][column].isDome()) {
                    System.out.println(RED + CELL_WITH_DOME + RST);
                    return null;
                } else {
                    return build;
                }
            }
        }
    }


    private boolean isUnreachable(int row, int column, Couple worker) {
        return row < Constants.GRID_MIN_SIZE || row >= Constants.GRID_MAX_SIZE || column < Constants.GRID_MIN_SIZE ||
                column >= Constants.GRID_MAX_SIZE || row >= Objects.requireNonNull(worker).getX() + 2 ||
                row <= worker.getX() - 2 || column >= worker.getY() + 2 || column <= worker.getY() - 2;
    }

    public SelectMoveAction move(int turnPhase, int activeWorker) {
        if (activeWorker == 0) {
            System.err.println(ERR_WORKER_NOT_SELECTED);
            return null;
        } else if (turnPhase == 0 || Constants.getMovePhaseGods().contains(modelView.getGod().toUpperCase())) {
            modelView.setMoveSelected(true);
            return new SelectMoveAction();
        } else {
            System.err.println(ERR_INCORRECT_ACTION);
            return null;
        }
    }

    private Couple findWorker(int activeWorker, String color) {
        Couple couple;
        for (int row = 0; row < Constants.GRID_MAX_SIZE; row++) {
            for (int column = 0; column < Constants.GRID_MAX_SIZE; column++) {
                if (modelView.getBoard().getGrid()[row][column].getWorkerNum() == activeWorker &&
                        modelView.getBoard().getGrid()[row][column].getColor().equals(color)) {
                    couple = new Couple(row, column);
                    return couple;
                }
            }
        }
        return null;
    }

    public StartTurnAction selectWorker(String[] in) {
        if(modelView.getTurnPhase()==0) {
            String var;
            if (Integer.parseInt(in[1]) == 1) {
                var = "worker1";
            } else if (Integer.parseInt(in[1]) == 2) {
                var = "worker2";
            } else {
                System.err.println("Non-existent worker, operation not permitted!");
                return null;
            }
            return new StartTurnAction(var);
        }
        System.err.println("You cannot change your worker after an action!");
        return null;
    }
}
