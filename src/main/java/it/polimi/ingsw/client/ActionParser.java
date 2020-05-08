package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Message;
import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.UserAction;
import it.polimi.ingsw.constants.Constants;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *  Handles the user input, transforming it in a message for the server. In this case, we decided to make a fatter client,
 *  in order to pre-check the correctness of the requests and then have minor server flooding.
 * @author Luca Pirovano
 */
public class ActionParser implements PropertyChangeListener {
    private final ConnectionSocket connection;
    private final ModelView modelView;
    private static final String RED = Constants.ANSI_RED;
    private static final String RST = Constants.ANSI_RESET;
    private final InputChecker inputChecker;

    public ActionParser(ConnectionSocket connection, ModelView modelView) {
        this.connection = connection;
        this.modelView = modelView;
        inputChecker = new InputChecker(connection);
    }

    /**
     * Synchronized action method; it's called when a player inserts a command in the CLI interface, and manages his
     * selection before sending it to the server through the socket. It also performs an initial check about the
     * rightness of the captured command.
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
                case "QUIT" -> {
                    inputChecker.quit();
                    return true;
                }
                default -> {
                    System.out.println(RED + "Unknown input, please try again!" + RST);
                    return false;
                }
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(RED + "Input error; try again!" + RST);
            return false;
        }
        if(sendMessage!=null) {
            connection.send(sendMessage);
            return true;
        }
        return false;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(!modelView.getCanInput()) {
            System.out.println(RED + "Error: not your turn!" + RST);
            return;
        }
        if(action(evt.getNewValue().toString())) {
            modelView.untoggleInput();
        }
        else {
            modelView.toggleInput();
        }
    }
}
