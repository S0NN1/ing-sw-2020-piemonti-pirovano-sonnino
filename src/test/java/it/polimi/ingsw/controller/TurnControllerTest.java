package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.turnactions.EndTurnAction;
import it.polimi.ingsw.client.messages.actions.turnactions.StartTurnAction;
import it.polimi.ingsw.client.messages.actions.workeractions.BuildAction;
import it.polimi.ingsw.client.messages.actions.workeractions.MoveAction;
import it.polimi.ingsw.client.messages.actions.workeractions.SelectBuildAction;
import it.polimi.ingsw.client.messages.actions.workeractions.SelectMoveAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.Action;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.gods.advancedgods.Charon;
import it.polimi.ingsw.model.player.gods.simplegods.Apollo;
import it.polimi.ingsw.model.player.gods.simplegods.Prometheus;
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
    final Prometheus prometheus = new Prometheus(PlayerColors.BLUE);
    final Apollo apollo = new Apollo(PlayerColors.RED);
    final Charon charon = new Charon(PlayerColors.GREEN);
    final PlayerStub piro = new PlayerStub("piro", 1);
    final PlayerStub ali = new PlayerStub("ali", 2);
    final PlayerStub sonny = new PlayerStub("sonny", 3);
    final Socket socket = new Socket();
    final ServerStub server = new ServerStub();
    final GameHandlerStub handler = new GameHandlerStub(server);
    final SocketClientConnectionStub socketClientConnectionStub = new SocketClientConnectionStub(socket, server);
    final VirtualClient virtualClient = new VirtualClient(1,"piro", socketClientConnectionStub,handler);
    final VirtualClient virtualClient2 = new VirtualClient(2,"ali", socketClientConnectionStub,handler);
    final VirtualClient virtualClient3 = new VirtualClient(3,"sonny", socketClientConnectionStub,handler);
    final HashMap<Integer, VirtualClient> idMapID = new HashMap<>(){
        {
            put(1, virtualClient);
            put(2, virtualClient2);
            put(3, virtualClient3);
        }
    };
    final GameBoardStub board = new GameBoardStub();
    final GameStub game = new GameStub();
    final ActionControllerStub actionControllerStub = new ActionControllerStub(board);
    final ControllerStub controllerStub = new ControllerStub(game, handler);
    final TurnController turnController = new TurnController(new Controller(game, handler), handler, actionControllerStub);
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
    PropertyChangeEvent evt11;
    PropertyChangeEvent evt12;
    PropertyChangeEvent evt13;


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
        sonny.getWorkers().add(charon);
        sonny.getWorkers().add(charon);
        sonny.getWorkers().get(0).setPhases();
        sonny.getWorkers().get(1).setPhases();
        server.setIdMapID(idMapID);
        piro.setColor(PlayerColors.BLUE);
        ali.setColor(PlayerColors.RED);
        sonny.setColor(PlayerColors.GREEN);
        actionControllerStub.setPhase(0);
        controllerStub.getModel().getActivePlayers().add(piro);
        controllerStub.getModel().getActivePlayers().add(ali);
        controllerStub.getModel().getActivePlayers().add(sonny);
        controllerStub.getModel().setCurrentPlayer(piro);
        game.setCurrentPlayer(piro);
        evt1 = new PropertyChangeEvent(1, null, null, new StartTurnAction("start"));
        evt2 = new PropertyChangeEvent(2, null, null, new StartTurnAction("worker1"));
        evt3 = new PropertyChangeEvent(3, null, null, new StartTurnAction("worker2"));
        evt4 = new PropertyChangeEvent(4, null, null, new EndTurnAction());
        evt5 = new PropertyChangeEvent(5, null, null, new SelectBuildAction());
        evt6 = new PropertyChangeEvent(6, null, null, new SelectMoveAction(Action.SELECT_MOVE));
        evt7 = new PropertyChangeEvent(7, null, null, new MoveAction(1, 1));
        evt8 = new PropertyChangeEvent(8, null, null, new BuildAction(1, 1));
        evt9 = new PropertyChangeEvent(9, null, null, "AthenaMovedUp");
        evt10 = new PropertyChangeEvent(10, null, null, "AthenaNormalMove");
        evt11 = new PropertyChangeEvent(6, null, null, new SelectMoveAction(Action.BUILD));
        evt12 = new PropertyChangeEvent(6, null, null, new SelectBuildAction(Action.SELECT_REMOVE));
        evt13 = new PropertyChangeEvent(6, null, null, new StartTurnAction("worker1"));


    }

    @Test
    public void endTurnActionTest(){
        Assertions.assertTrue(evt4.getNewValue() instanceof EndTurnAction);
        actionControllerStub.setPhase(7);
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
        controllerStub.getModel().getCurrentPlayer().getWorkers().get(0).setBlocked(false);
        controllerStub.getModel().getCurrentPlayer().getWorkers().get(1).setBlocked(false);
        actionControllerStub.setPhase(0);
        controllerStub.getModel().setCurrentPlayer(piro);
        game.setCurrentPlayer(piro);
        turnController.propertyChange(evt13);
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
        Assertions.assertTrue(evt11.getNewValue() instanceof SelectMoveAction);
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
        actionControllerStub.setPhase(3);
        turnController.propertyChange(evt11);
    }
    @Test
    public void unusedWorkerTest(){
        game.getCurrentPlayer().getWorkers().get(0).setBlocked(false);
        game.getCurrentPlayer().getWorkers().get(1).setBlocked(false);
        controllerStub.getModel().setCurrentPlayer(ali);
        game.setCurrentPlayer(ali);
        actionControllerStub.setWorker(ali.getWorkers().get(0));
        Assertions.assertEquals(ali.getWorkers().get(0), actionControllerStub.getWorker());
        actionControllerStub.setPhase(1);
        turnController.propertyChange(evt12);
    }
    @Test
    public void endGameTest(){
        actionControllerStub.setPhase(0);
        game.setCurrentPlayer(ali);
        controllerStub.getModel().setCurrentPlayer(ali);
        ali.getWorkers().get(0).setBlocked(true);
        ali.getWorkers().get(0).setPhases();
        Assertions.assertEquals(Action.SELECT_MOVE, ali.getWorkers().get(0).getPhase(0).getAction());
        ali.getWorkers().get(1).setBlocked(true);
        ali.getWorkers().get(1).setPhases();
        turnController.propertyChange(evt2);
    }

    public static class GameStub extends Game {

    }

    public static class ActionControllerStub extends ActionController {
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
            return action.getMessage() == Action.SELECT_MOVE;
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

    public static class GameBoardStub extends GameBoard {

    }

    public static class ControllerStub extends Controller{

        public ControllerStub(Game model, GameHandler gameHandler) {
            super(model, gameHandler);
        }
    }
    public static class PlayerStub extends Player{

        public PlayerStub(String nickname, int clientID) {
            super(nickname, clientID);
        }
    }
    public static class ServerStub extends Server {
        private HashMap <Integer, VirtualClient>idMapID;
        public ServerStub(){
            this.idMapID = null;
        }
        public void setIdMapID(HashMap idMapID){
            this.idMapID = idMapID;
        }
        @Override
        public VirtualClient getClientByID(int id) {
            return idMapID.get(id);
        }
    }
    public static class SocketClientConnectionStub extends SocketClientConnection{

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