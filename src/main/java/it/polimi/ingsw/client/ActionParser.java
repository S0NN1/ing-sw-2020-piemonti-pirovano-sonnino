package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.client.messages.actions.turnactions.EndTurnAction;
import it.polimi.ingsw.constants.Constants;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * ActionParser class handles the user input, converting it in a message for the server. In this case, we decided to
 * make a thicker client, in order to pre-check the correctness of the requests and then have minor server flooding.
 *
 * @author Luca Pirovano, Nicolò Sonnino
 * @version 2.0.0
 * @see PropertyChangeListener
 */
public class ActionParser implements PropertyChangeListener {
    private static final String RED = Constants.ANSI_RED;
    private static final String RST = Constants.ANSI_RESET;
    private final ConnectionSocket connection;
    private final ModelView modelView;
    private final InputChecker inputChecker;

    /**
     * Constructor ActionParser creates a new ActionParser instance.
     *
     * @param connection of type ConnectionSocket - the socket used for the connection.
     * @param modelView of type ModelView the structure, stored into the client, containing simple logic of the model.
     */
    public ActionParser(ConnectionSocket connection, ModelView modelView) {
        this.connection = connection;
        this.modelView = modelView;
        inputChecker = new InputChecker(connection, modelView);
    }

    /**
     * Method checkBuild checks build input inserted from cli, if it contains no arguments it refers to a
     * SelectBuildAction otherwise to a BuildAction.
     *
     * @param in of type String[] - the input from the player.
     * @param turnPhase of type int - the number defining turn's phase.
     * @return UserAction - the correct action.
     */
    private UserAction checkBuild(String[] in, int turnPhase) {
        UserAction sendMessage;
        sendMessage = (in.length == 1) ? inputChecker.build(turnPhase, modelView.getActiveWorker())
                : inputChecker.build(turnPhase, Integer.parseInt(in[1]), Integer.parseInt(in[2]),
                modelView.getActiveWorker());
        return sendMessage;
    }

    /**
     * Method checkPlaceDome checks placedome input inserted from cli, if it contains no arguments it refers to a
     * SelectBuildAction otherwise to a BuildAction (ATLAS ONLY).
     *
     * @param in of type String[] - the input from the player.
     * @param turnPhase of type int - the number defining turn's phase.
     * @return UserAction - the correct action.
     */
    private UserAction checkPlaceDome(String[] in, int turnPhase) {
        UserAction sendMessage;
        sendMessage = (in.length == 1) ? inputChecker.build(turnPhase, modelView.getActiveWorker())
                : inputChecker.atlasBuild(turnPhase, Integer.parseInt(in[1]), Integer.parseInt(in[2]),
                modelView.getActiveWorker());
        if(sendMessage!=null && in.length== 1){
            modelView.setGodPowerActive(true);
        }
        return sendMessage;
    }

    /**
     * Method checkSelectWorker checks if selectworker input it's correct and set the chosen worker in modelView as
     * active.
     *
     * @param in of type String[] - the input from the player.
     * @return UserAction  -the correct action.
     */
    private UserAction checkSelectWorker(String[] in) {
        UserAction sendMessage;
        sendMessage = inputChecker.selectWorker(in);
        if (sendMessage != null) {
            modelView.setActiveWorker(Integer.parseInt(in[1]));
        }
        return sendMessage;
    }

    /**
     * Method checkPlaceDome checks move input inserted from cli, if it contains no arguments it refers to a
     * SelectMoveAction otherwise to a MoveAction.
     *
     * @param in of type String[] - the input from the player.
     * @param turnPhase of type int - the number defining turn's phase.
     * @return UserAction - the correct action.
     */
    private UserAction checkMove(String[] in, int turnPhase) {
        return (in.length == 1) ? inputChecker.move(turnPhase, modelView.getActiveWorker())
                : inputChecker.move(turnPhase, Integer.parseInt(in[1]), Integer.parseInt(in[2]),
                modelView.getActiveWorker());
    }

    /**
     * Synchronized action method, called when a player inserts a command in the CLI interface, manages its selection
     * before sending it to the server through the socket. It also performs an initial check of the rightness of the
     * captured command.
     * @param input of type String - the input from the player.
     * @return boolean true if sendMessage != null, false otherwise.
     */
    public synchronized boolean action(String input) {
        String[] in = input.split(" ");
        String command = in[0];
        UserAction sendMessage;
        try {
            switch (command.toUpperCase()) {
                case "GODLIST" -> {
                    connection.send(new ChallengerPhaseAction("LIST"));
                    return true;
                }
                case "GODDESC" -> sendMessage = inputChecker.desc(in);
                case "ADDGOD" -> sendMessage = inputChecker.addGod(in);
                case "CHOOSE" -> sendMessage = inputChecker.choose(in);
                case "STARTER" -> sendMessage = inputChecker.starter(in);
                case "SET" -> sendMessage = inputChecker.set(in);
                case "SELECTWORKER" -> sendMessage = checkSelectWorker(in);
                case "MOVE" -> sendMessage = checkMove(in, modelView.getTurnPhase());
                case "BUILD" -> sendMessage = checkBuild(in, modelView.getTurnPhase());
                case "PLACEDOME" -> sendMessage = checkPlaceDome(in, modelView.getTurnPhase());
                case "FORCEWORKER" -> sendMessage = checkForceWorker(in, modelView.getTurnPhase());
                case "REMOVELEVEL" -> sendMessage = checkRemoveLevel(in, modelView.getTurnPhase());
                case "END" -> sendMessage = new EndTurnAction();
                case "QUIT" -> {
                    inputChecker.quit();
                    return true;
                }
                default -> {
                    System.out.println(RED + "Unknown input, please try again!" + RST);
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(RED + "Input error; try again!" + RST);
            return false;
        } catch (NumberFormatException e) {
            System.err.println("Numeric value required, operation not permitted!");
            return false;
        }
        if (sendMessage != null) {
            connection.send(sendMessage);
            return true;
        }
        return false;
    }

    /**
     * Method checkForceWorker checks FORCEWORKER input.
     *
     * @param in of type String[] - the input from the player.
     * @param turnPhase of type int - correct turn phase.
     * @return UserAction  -the correct action.
     */
    private UserAction checkForceWorker(String[] in, int turnPhase) {
        return (in.length == 1) ? inputChecker.selectForceWorker(turnPhase, modelView.getActiveWorker())
                : inputChecker.forceWorker(turnPhase, Integer.parseInt(in[1]), Integer.parseInt(in[2]),
                modelView.getActiveWorker());
    }

    /**
     * Method checkRemoveLevel checks REMOVELEVEL input.
     *
     * @param in of type String[] - the input from the player.
     * @param turnPhase of type int - correct turn phase.
     * @return UserAction  -the correct action.
     */
    private UserAction checkRemoveLevel(String[] in, int turnPhase) {
        return (in.length == 1) ? inputChecker.selectRemoveLevel(turnPhase, modelView.getActiveWorker())
                : inputChecker.removeLevel(turnPhase, Integer.parseInt(in[1]), Integer.parseInt(in[2]),
                modelView.getActiveWorker());
    }

    /**
     * @see PropertyChangeListener#propertyChange(PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!modelView.getCanInput()) {
            System.out.println(RED + "Error: not your turn!" + RST);
        } else if (action(evt.getNewValue().toString())) {
            modelView.deactivateInput();
        } else {
            modelView.activateInput();
        }
    }
}