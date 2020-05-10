package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Disconnect;
import it.polimi.ingsw.client.messages.actions.ChallengerPhaseAction;
import it.polimi.ingsw.client.messages.actions.WorkerSetupMessage;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.Card;


/**
 * Check the correctness of the input received from the ActionParser, returning either true or false after his check.
 * @author Luca Pirovano, Nicolò Sonnino
 */
public class InputChecker {
    private final ConnectionSocket connection;
    private static final String GOD_NOT_FOUND = "Not existing god with your input's name.";
    private static final String RED = Constants.ANSI_RED;
    private static final String RST = Constants.ANSI_RESET;
    private ModelView modelView;


    public InputChecker(ConnectionSocket connection, ModelView modelView) {
        this.connection = connection;
        this.modelView=modelView;
    }

    /**
     * Validates a "GODDESC <god-name>" message type.
     * @param in the user input under array representation.
     * @return true if the input is valid and sent to the server, false otherwise.
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
     * @param in the user input under array representation.
     * @return true if the input is valid and sent to the server, false otherwise.
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
     * Validates a "CHOOSE <god-name>" message type.
     * @param in the user input under array representation.
     * @return true if the input is valid and sent to the server, false otherwise.
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
     * Validates a "STARTER <player-number>" starting player message type.
     * @param in the user input under array representation.
     * @return true if the input is valid and sent to the server, false otherwise.
     */
    public ChallengerPhaseAction starter(String[] in){
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
     * Validates a "SET <x1> <y1> <x2> <y2>" worker placement message type.
     * @param in the user input under array representation.
     * @return true if the input is valid and sent to the server, false otherwise.
     */
    public WorkerSetupMessage set(String[] in) {
        WorkerSetupMessage action;
        try {
            action = new WorkerSetupMessage(in);
        } catch (NumberFormatException e) {
            System.out.println(RED + "Unknown input, please try again!" + RST);
            return null;
        }
        return action;
    }

    /**
     * Handles the program quit statement, terminating the local service with exit code 0.
     */
    public void quit() {
        connection.send(new Disconnect());
        System.err.println("Disconnected from the server.");
        System.exit(0);
    }

    /**
     * Check if build action is possible
     * @param turnPhase int
     * @param x int
     * @param y int
     * @param activeWorker int
     * @return boolean
     */
    public boolean build(int turnPhase,int x, int y, int activeWorker){
        Couple w=findworker(activeWorker, modelView.getColor());
        if(turnPhase==2||modelView.getGod().toUpperCase().equals("ATLAS")||modelView.getGod().toUpperCase().equals("DEMETER")|| modelView.getGod().toUpperCase().equals("PROMETHEUS")) {
            if(x<0||x>=5||y<0||y>=5|| x>=w.getX()+2 || x<=w.getX()+2|| y>=w.getY()+2 || y<=w.getY()+2){
                return false;
            }
            else{
                return modelView.getBoard().getGrid()[x][y].getColor() == null && modelView.getBoard().getGrid()[x][y].getLevel() != 4 && !modelView.getBoard().getGrid()[x][y].isDome();
            }
        }
        else return false;
    }

    /**
     * Check if move is possible
     * @param turnPhase int
     * @param x int
     * @param y int
     * @param activeWorker int
     * @return boolean
     */
    public boolean move(int turnPhase, int x, int y, int activeWorker){
        Couple w=findworker(activeWorker, modelView.getColor());
        if(turnPhase == 1 || modelView.getGod().toUpperCase().equals("PROMETHEUS") || modelView.getGod().toUpperCase().equals("ARTEMIS")){
            if(x<0||x>=5||y<0||y>=5|| x>=w.getX()+2 || x<=w.getX()+2|| y>=w.getY()+2 || y<=w.getY()+2){
                return false;
        }
        else{
            if(modelView.getBoard().getGrid()[x][y].getColor()!=null){
                return modelView.getGod().toUpperCase().equals("APOLLO") || (modelView.getGod().toUpperCase().equals("MINOTAUR") && !modelView.getBoard().getGrid()[x][y].getColor().equals(modelView.getColor()));
            }
            else{
                return modelView.getBoard().getGrid()[x][y].getLevel() != 4 && modelView.getBoard().getGrid()[x][y].getLevel() - modelView.getBoard().getGrid()[w.getX()][w.getY()].getLevel() < 2;
            }
        }
        }
        else return false;

    }
    
    private Couple findworker(int activeWorker, String color){
        Couple couple = null;
        for(int i =0;i<5;i++){
            for (int j = 0; j < 5; j++) {
                if(modelView.getBoard().getGrid()[i][j].getWorkerNum()==activeWorker && modelView.getBoard().getGrid()[i][j].getColor().equals(color)){
                    couple.setX(i);
                    couple.setY(j);
                    return couple;
                }
            }
        }
        return null;
    }
}
