package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.turnactions.EndTurnAction;
import it.polimi.ingsw.client.messages.actions.turnactions.StartTurnAction;
import it.polimi.ingsw.client.messages.actions.workeractions.BuildAction;
import it.polimi.ingsw.client.messages.actions.workeractions.MoveAction;
import it.polimi.ingsw.client.messages.actions.workeractions.SelectBuildAction;
import it.polimi.ingsw.client.messages.actions.workeractions.SelectMoveAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.gods.Apollo;
import it.polimi.ingsw.model.player.gods.Prometheus;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import org.junit.jupiter.api.*;

import java.beans.PropertyChangeEvent;
import java.net.Socket;
import java.util.HashMap;

public class TurnControllerTest {
    Prometheus prometheus = new Prometheus(PlayerColors.BLUE);
    Apollo apollo = new Apollo(PlayerColors.RED);
    PlayerStub piro = new PlayerStub("piro", 1);
    PlayerStub ali = new PlayerStub("ali", 2);
    PlayerStub sonny = new PlayerStub("sonny", 3);
    Socket socket = new Socket();
    ServerStub server = new ServerStub();
    GameHandlerStub handler = new GameHandlerStub(server);
    SocketClientConnectionStub socketClientConnectionStub = new SocketClientConnectionStub(socket, server);
    VirtualClient virtualClient = new VirtualClient(1,"piro", socketClientConnectionStub,handler);
    VirtualClient virtualClient2 = new VirtualClient(2,"ali", socketClientConnectionStub,handler);
    VirtualClient virtualClient3 = new VirtualClient(3,"sonny", socketClientConnectionStub,handler);
    HashMap<Integer, VirtualClient> idMapID = new HashMap<>(){
        {
            put(1, virtualClient);
            put(2, virtualClient2);
            put(3, virtualClient3);
        }
    };
    GameBoardStub board = new GameBoardStub();
    GameStub game = new GameStub();
    ActionControllerStub actionControllerStub =new ActionControllerStub(board);
    ControllerStub controllerStub = new ControllerStub(game, handler);
    TurnControllerStub turnController = new TurnControllerStub(new Controller(game, handler), handler, actionControllerStub);
    PropertyChangeEvent evt1;
    PropertyChangeEvent evt2;
    PropertyChangeEvent evt3;
    PropertyChangeEvent evt4;
    PropertyChangeEvent evt5;
    PropertyChangeEvent evt6;
    PropertyChangeEvent evt7;
    PropertyChangeEvent evt8;
    PropertyChangeEvent evt9;
    PropertyChangeEvent evt10;


    /**
     * Test if messages are forwarded into the right condition
     */

    @BeforeEach
    public  void init(){
        piro.getWorkers().add(prometheus);
        piro.getWorkers().add(prometheus);
        piro.getWorkers().get(0).setPhases();
        piro.getWorkers().get(1).setPhases();
        ali.getWorkers().add(apollo);
        ali.getWorkers().add(apollo);
        ali.getWorkers().get(0).setPhases();
        ali.getWorkers().get(1).setPhases();
        server.setIdMapID(idMapID);
        piro.setColor(PlayerColors.BLUE);
        ali.setColor(PlayerColors.RED);
        sonny.setColor(PlayerColors.GREEN);
        actionControllerStub.phase=0;
        controllerStub.getModel().getActivePlayers().add(piro);
        controllerStub.getModel().getActivePlayers().add(ali);
        controllerStub.getModel().setCurrentPlayer(piro);
        game.setCurrentPlayer(piro);
        evt1 = new PropertyChangeEvent(1, null, null, new StartTurnAction("start"));
        evt2 = new PropertyChangeEvent(2, null, null, new StartTurnAction("worker1"));
        evt3 = new PropertyChangeEvent(3, null, null, new StartTurnAction("worker2"));
        evt4 = new PropertyChangeEvent(4, null, null, new EndTurnAction());
        evt5 = new PropertyChangeEvent(5, null, null, new SelectBuildAction());
        evt6 = new PropertyChangeEvent(6, null, null, new SelectMoveAction());
        evt7 = new PropertyChangeEvent(7, null, null, new MoveAction(1, 1));
        evt8 = new PropertyChangeEvent(8, null, null, new BuildAction(1, 1));
        evt9 = new PropertyChangeEvent(9, null, null, "AthenaMovedUp");
        evt10 = new PropertyChangeEvent(10, null, null, "AthenaNormalMove");

    }

    @Test
    public void endTurnActionTest(){
        Assertions.assertTrue(evt4.getNewValue() instanceof EndTurnAction);
        actionControllerStub.setPhase(5);
        turnController.propertyChange(evt4);
    }

    @Test
    @DisplayName("StartTurnActionTest")
    public void startTurnActionTest(){
        Assertions.assertTrue(evt1.getNewValue() instanceof StartTurnAction);
        Assertions.assertTrue(evt2.getNewValue() instanceof StartTurnAction);
        Assertions.assertTrue(evt3.getNewValue() instanceof StartTurnAction);
        turnController.propertyChange(evt1);
        controllerStub.getModel().getCurrentPlayer().getWorkers().get(0).setBlocked(true);
        turnController.propertyChange(evt3);
        controllerStub.getModel().getCurrentPlayer().getWorkers().get(0).setBlocked(false);
        turnController.propertyChange(evt3);
        actionControllerStub.setPhase(1);
        turnController.propertyChange(evt2);
    }
    @Test
    @DisplayName("Testing Athena moves")
    public void AthenaMovesTest(){
        Assertions.assertEquals("AthenaMovedUp", evt9.getNewValue());
        Assertions.assertEquals("AthenaNormalMove", evt10.getNewValue());
        turnController.propertyChange(evt9);
        turnController.propertyChange(evt10);
    }
    @Test
    @DisplayName("Testing all actions")
    public void actionsTest() {
        Assertions.assertTrue(evt5.getNewValue() instanceof SelectBuildAction);
        Assertions.assertTrue(evt6.getNewValue() instanceof SelectMoveAction);
        Assertions.assertTrue(evt7.getNewValue() instanceof MoveAction);
        Assertions.assertTrue(evt8.getNewValue() instanceof BuildAction);
        actionControllerStub.setPhase(2);
        game.setCurrentPlayer(ali);
        controllerStub.getModel().setCurrentPlayer(ali);
        game.getCurrentPlayer().getWorkers().get(0).setBlocked(false);
        game.getCurrentPlayer().getWorkers().get(1).setBlocked(false);
        actionControllerStub.setWorker(ali.getWorkers().get(0));
        turnController.propertyChange(evt5);
        actionControllerStub.setPhase(2);
        actionControllerStub.getWorker().setBlocked(true);
        turnController.propertyChange(evt5);
        turnController.propertyChange(evt6);
        turnController.propertyChange(evt7);
        turnController.propertyChange(evt8);
    }

    private class TurnControllerStub extends TurnController {

        private TurnControllerStub(Controller controller, GameHandler gameHandler, ActionControllerStub actionControllerStub) {
            super(controller, gameHandler, actionControllerStub);
        }


        @Override
        public void endTurn() {
            System.out.println(" Entered endAction ");
        }

        @Override
        public void sendBuildError() {
            System.out.println("Build error");
        }
        @Override
        public void sendMoveError() {
            System.out.println("Move error");
        }
    }


    public class GameStub extends Game {

    }

    public class ActionControllerStub extends ActionController {
        private Worker worker;

        public ActionControllerStub(GameBoard gameBoard) {
            super(gameBoard);
            phase = 0;
            worker = null;
        }

        @Override
        public Worker getWorker() {
            return worker;
        }

        public void setPhase(int phase){
            if(phase<0){
                this.phase= Integer.parseInt(null);
            }
            else this.phase = phase;
        }
        public void setWorker(Worker worker){
            this.worker=worker;
        }

        @Override
        public boolean readMessage(SelectMoveAction action) {
            System.out.println(" SelectMoveAction");
            return true;
        }

        @Override

        public boolean readMessage(SelectBuildAction action) {
            System.out.println(" SelectBuildAction");
            return true;
        }

        @Override


        public boolean readMessage(MoveAction action) {
            System.out.println(" MoveAction");
            return true;
        }

        @Override

        public boolean readMessage(BuildAction action) {
            System.out.println(" BuildAction");
            return true;
        }

        @Override
        public boolean startAction(Worker currentWorker) {
            System.out.println("Worker action");
            return true;
        }
    }

    public class GameHandlerStub extends GameHandler {

        public GameHandlerStub(Server server) {
            super(server);
        }

        @Override
        public void singleSend(Answer message, int id) {
            String print;
            if(message.getMessage()==null){
                print = "Start/End/Move/SelectBuild/BuildSelectMove action";
            }
            else print = message.getMessage().toString();
            System.out.println(print);
        }

        @Override
        public int getCurrentPlayerID() {
            return game.getCurrentPlayer().getClientID();
        }

        @Override
        public void sendAll(Answer message) {
            System.out.println(message.getMessage());
        }
    }

    public class GameBoardStub extends GameBoard {

    }

    public class ControllerStub extends Controller{

        public ControllerStub(Game model, GameHandler gameHandler) {
            super(model, gameHandler);
        }
    }
    public class PlayerStub extends Player{

        public PlayerStub(String nickname, int clientID) {
            super(nickname, clientID);
        }
    }
    public class ServerStub extends Server {
        private final HashMap <Integer, VirtualClient>idMapID;
        public ServerStub(){
            this.idMapID = null;
        }
        public void setIdMapID(HashMap idMapID){

        }
        @Override
        public VirtualClient getClientByID(int id) {
            return idMapID.get(id);
        }
    }
    public class SocketClientConnectionStub extends SocketClientConnection{

        /**
         * Constructor of the class: it instantiates an input/output stream from the socket received as parameters, and
         * add the main server to his attributes too.
         *
         * @param socket the socket which accepted the client connection.
         * @param server the main server class.
         */
        public SocketClientConnectionStub(Socket socket, Server server) {
            super(socket, server);
        }
        @Override
        public void close() {
            System.out.println("Connection closed to client");
        }
    }

}