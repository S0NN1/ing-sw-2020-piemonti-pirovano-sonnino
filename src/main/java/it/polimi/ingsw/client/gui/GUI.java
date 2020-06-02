package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.client.gui.controllers.GUIController;
import it.polimi.ingsw.client.gui.controllers.LoaderController;
import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.server.answers.*;
import it.polimi.ingsw.server.answers.turn.ModifiedTurnMessage;
import it.polimi.ingsw.server.answers.worker.BuildMessage;
import it.polimi.ingsw.server.answers.worker.DoubleMoveMessage;
import it.polimi.ingsw.server.answers.worker.MoveMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GUI class starts the graphical user interface, mapping each scene to the specific phase. It's triggered when an
 * event change is reported by the server, calling the correct components in order to make the local modification.
 * @author Luca Pirovano, Nicolò Sonnino
 * @see UI
 */
public class GUI extends Application implements UI {

    public static final String END_OF_THE_GAME = "End of the game";
    private ConnectionSocket connection = null;
    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private boolean activeGame;

    /**
     * Maps each scene name to the effective scene object, in order to easily find it during scene changing operations.
     */
    private final HashMap<String, Scene> nameMapScene = new HashMap<>();
    /**
     * Maps each scene controller's name to the effective controller object, in order to get the correct controller
     * for modifying operations.
     * @see it.polimi.ingsw.client.gui.controllers for more details.
     */
    private final HashMap<String, GUIController> nameMapController = new HashMap<>();

    private static final String MAIN_GUI = "mainScene.fxml";
    private static final String MENU = "MainMenu.fxml";
    private static final String LOADER = "loading.fxml";
    private static final String GODS = "godsMenu.fxml";
    private static final String SETUP = "setup.fxml";

    private Scene currentScene;
    private Stage stage;

    private boolean[] actionCheckers;


    /**
     * Constructor GUI creates a new GUI instance.
     */
    public GUI() {
        this.modelView = new ModelView(this);
        actionHandler = new ActionHandler(this, modelView);
        activeGame = true;
    }

    /**
     * Method run sets the title of the main stage and launches the window.
     */
    public void run() {
        stage.setTitle("Santorini");
        stage.setScene(currentScene);
        stage.show();
    }

    /** @see Application#start(Stage) */
    @Override
    public void start(Stage stage) throws Exception {
        setup();
        this.stage = stage;
        this.stage.setResizable(false);
        run();
    }

    /** @see Application#stop() */
    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    /**
     * Method setup creates all the stage phases which will be updated in other methods, in particular:
     * - MENU: the game's main menu with Play and Quit buttons;
     * - SETUP: small windows containing player's inserted nickname, the server IP and port;
     * - LOADER: the game loader containing player's chosen color, god power and places their workers;
     * - GUI: the effective game GUI (island board).
     * Each stage scene is put inside an hashmap, which links their name to their fxml filename.
     */
    public void setup() {
        List<String> fxmList = new ArrayList<>(Arrays.asList(MAIN_GUI, GODS, MENU, LOADER, SETUP));
        try {
            for (String path : fxmList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                nameMapScene.put(path, new Scene(loader.load()));
                GUIController controller = loader.getController();
                controller.setGui(this);
                nameMapController.put(path, controller);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        currentScene = nameMapScene.get(MENU);
    }


    /**
     * Method getStage returns the stage of this GUI object.
     *
     *
     *
     * @return the stage (type Stage) of this GUI object.
     */
    public Stage getStage() {
        return stage;
    }


    /**
     * Method getListeners returns the listeners of this GUI object.
     *
     *
     *
     * @return the listeners (type PropertyChangeSupport) of this GUI object.
     */
    public PropertyChangeSupport getListeners() {
        return listeners;
    }

    /**
     * Method centerApplication centers the application windows in the screen, based on user's screen height and width;
     * getting them from the Screen java class.
     */
    public void centerApplication() {
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        stage.setX((screenSize.getWidth() - currentScene.getWidth())/2);
        stage.setY((screenSize.getHeight() - currentScene.getHeight())/2);
    }

    /**
     * Method changeStage changes the stage scene based on the ones declared during setup phase.
     * On method call the actual stage scene is replaced from the parameter one.
     * @param newScene of type String - the scene displayed.
     */
    public void changeStage(String newScene) {
        currentScene = nameMapScene.get(newScene);
        stage.setScene(currentScene);
        stage.show();
    }


    /**
     * Method getConnection returns the connection of this GUI object.
     *
     *
     *
     * @return the connection (type ConnectionSocket) of this GUI object.
     */
    public ConnectionSocket getConnection() {
        return connection;
    }


    /**
     * Method setConnection sets the connection of this GUI object.
     *
     *
     *
     * @param connection the connection of this GUI object.
     *
     */
    public void setConnection(ConnectionSocket connection) {
        if(this.connection==null) {
            this.connection = connection;
        }
    }


    /**
     * Method getModelView returns the modelView of this GUI object.
     *
     *
     *
     * @return the modelView (type ModelView) of this GUI object.
     */
    public ModelView getModelView() {
        return modelView;
    }


    /**
     * Method getActionHandler returns the actionHandler of this GUI object.
     *
     *
     *
     * @return the actionHandler (type ActionHandler) of this GUI object.
     */
    public ActionHandler getActionHandler() {
        return actionHandler;
    }


    /**
     * Method getControllerFromName gets a scene controller based on inserted name from the dedicated hashmap.
     *
     * @param name of type String - player's name.
     * @return GUIController - scene controller.
     */
    public GUIController getControllerFromName(String name) {
        return nameMapController.get(name);
    }

    /**
     * Method errorHandling handles the error received from the server based on their type.
     * @param error of type GameError - the game error received from the server.
     */
    public void errorHandling(GameError error) {
        Platform.runLater(() -> {
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Game Error");
            errorDialog.setHeaderText("Error!");
            errorDialog.setContentText(error.getMessage());
            errorDialog.showAndWait();
        });
    }

    /**
     * Method initialPhaseHandling handles the first game phase, which contains the nicknames choosing,the challenger
     * phase and the workers placement.
     * @param cmd of type String  - the command answer received from the server.
     */
    public void initialPhaseHandling(String cmd) {
        switch (cmd) {
            case "RequestPlayerNumber" -> {
                Platform.runLater(() -> {
                    LoaderController controller = (LoaderController) getControllerFromName(LOADER);
                    controller.requestPlayerNumber(((RequestPlayersNumber) modelView.getServerAnswer()).getMessage());
                });
            }
            case "RequestColor" -> {
                Platform.runLater(() -> {
                    LoaderController controller = (LoaderController) getControllerFromName(LOADER);
                    controller.requestColor(((ColorMessage) modelView.getServerAnswer()).getRemaining());
                });
            }
            case "GodRequest" -> {
                ChallengerMessages req = (ChallengerMessages) modelView.getServerAnswer();
                Platform.runLater(() -> {
                    LoaderController controller = (LoaderController) getControllerFromName(LOADER);
                    controller.challengerPhase(req);
                });
            }
            case "WorkerPlacement" -> {
                LoaderController controller = (LoaderController)getControllerFromName(LOADER);
                Platform.runLater(() -> controller.workerPlacement(((WorkerPlacement)modelView.getServerAnswer()).
                        getAvailableCoordinates()));
            }
            default -> {
                logger.log(Level.WARNING, "No action to be performed!");
            }
        }
    }

    /**
     * Method customMessageHandling handles all the custom message received from the server and execute actions based
     * on their content.
     * @param msg of type String - the custom message.
     */
    public void customMessageHandling(String msg) {
        if(modelView.getGamePhase()==0) {
            if(msg.contains("Match starting") || msg.contains("The match has started")) {
                LoaderController controller = (LoaderController)getControllerFromName(LOADER);
                Platform.runLater(() -> controller.setText(msg));
            } else if(msg.contains("is the challenger")) {
                LoaderController controller = (LoaderController)getControllerFromName(LOADER);
                Platform.runLater(() -> controller.setText(msg.split(" ")[0] +
                        " is the challenger\nHe's choosing gods power!"));
            }
            else if(msg.contains("disconnected from the server")) {
                LoaderController controller = (LoaderController)getControllerFromName(LOADER);
                Platform.runLater(() -> controller.setText("WAITING FOR PLAYERS"));
            }
            else if (msg.contains("is choosing")) {
                LoaderController controller = (LoaderController)getControllerFromName(LOADER);
                Platform.runLater(() -> {
                    controller.setFontSize(30);
                    controller.setText(msg);});
            }
        }
    }

/** @see java.beans.PropertyChangeListener#propertyChange(PropertyChangeEvent) */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getOldValue()!=null) {
            actionCheckers = (boolean[])evt.getOldValue();
        } else actionCheckers = null;
        switch (evt.getPropertyName()) {
            case "gameError" -> {
                errorHandling((GameError)evt.getNewValue());
            }
            case "initialPhase" -> {
                initialPhaseHandling(evt.getNewValue().toString());
            }
            case "customMessage" -> {
                customMessageHandling(evt.getNewValue().toString());
            }
            case "connectionClosed" -> {
                connectionClosed(evt);
            }
            case "noPossibleMoves" -> noPossibleMoves();
            case "select" -> showSpacesList();
            case "boardUpdate" -> checkAction();
            case "selectWorker" -> selectWorker();
            case "newPlayerTurn" -> newPlayerTurn();
            case "modifiedTurnNoUpdate" -> modifiedTurnHandling();
            case "end" -> endTurn();

            case "win" -> winner();

            case "lose" -> loser(evt.getNewValue().toString());
            case "singleLost" -> singleLoser();
            case "otherLost" -> otherLoser(evt.getNewValue().toString());
            case "matchStarted" -> matchStarted();
            default -> {
                logger.log(Level.WARNING, "No actions to be performed");
            }
        }
    }

    /**
     * Method newPlayerTurn updates the GUI star icon with the new current player.
     */
    private void newPlayerTurn() {
        Platform.runLater(() -> {
            MainGuiController controller = (MainGuiController) getControllerFromName(MAIN_GUI);
            controller.updateTurnStatus();
        });
    }

    /**
     * Method modifiedTurnHandling handles ModifiedTurnMessages and show actions accordantly.
     */
    private void modifiedTurnHandling() {
        Platform.runLater(() -> {
            MainGuiController controller = (MainGuiController)getControllerFromName(MAIN_GUI);
            controller.showActions(actionCheckers);
        });
    }

    /**
     *  Method endTurn handles EndTurnMessages and show actions accordantly.
     */

    private void endTurn() {
        Platform.runLater(() -> {
            MainGuiController controller = (MainGuiController)getControllerFromName(MAIN_GUI);
            controller.endTurn();
        });
    }
    /**
     *  Method matchStarted handles StartTurnMessages and show actions accordantly.
     */

    private void matchStarted() {
        Platform.runLater(() -> {
            changeStage(MAIN_GUI);
            MainGuiController controller = (MainGuiController)getControllerFromName(MAIN_GUI);
            controller.init();
        });
    }

    /**
     *  Method otherLoser handles PlayerLostMessages (another player lost) and show actions accordantly.
     * @param loser of type String  - the loser's nickname.
     */
    private void otherLoser(String loser) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("A player left the game.");
            alert.setHeaderText("Lose condition active.");
            alert.setContentText("Player " + loser + "has loose!");
            alert.showAndWait();
        });
    }
    /**
     *  Method singleLoser handles PlayerLostMessages (client lost) and show actions accordantly.
     */
    private void singleLoser() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(END_OF_THE_GAME);
            alert.setHeaderText("YOU LOSE!");
            alert.setContentText("All of your workers are blocked, YOU LOSE!\nApplication will now close.");
            alert.showAndWait();
            System.exit(0);
        });
    }
    /**
     *  Method loser handles PlayerLostMessages (another player won) and show actions accordantly.
     * @param winner of Type String - the winner's nickname.
     */
    private void loser(String winner) {
        activeGame = false;
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(END_OF_THE_GAME);
            alert.setHeaderText("YOU LOSE!");
            alert.setContentText("The game has ended, player " + winner + " has won! \nApplication will now close.");
            alert.getButtonTypes().setAll(new ButtonType("OK"));
            alert.showAndWait();
            System.exit(0);
        });
    }

    /**
     * Method winner handles WinMessage and show actions accordantly.
     */
    private void winner() {
        activeGame = false;
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(END_OF_THE_GAME);
            alert.setHeaderText("YOU WIN!");
            alert.setContentText("The game has ended, you win!\nApplication will now close.");
            alert.getButtonTypes().setAll(new ButtonType("OK"));
            alert.showAndWait();
            System.exit(0);
        });
    }

    /**
     * Method noPossibleMoves handles noPossibleMoves error.
     */
    private void noPossibleMoves() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No move available!");
            alert.setContentText("No possible moves! \nPlease select another worker");
            alert.showAndWait();
            MainGuiController controller = (MainGuiController) getControllerFromName(MAIN_GUI);
            controller.selectWorker();
        });
    }

    /**
     * Method selectWorker handles worker's selection.
     */
    private void selectWorker() {
        Platform.runLater(() -> {
            MainGuiController controller = (MainGuiController) getControllerFromName(MAIN_GUI);
            controller.updateTurnStatus();
            controller.selectWorker();
        });
    }

    /**
     * Method checkAction checks Message type and calls controller's methods.
     */
    private void checkAction() {
        Platform.runLater(() -> {
            Answer message = modelView.getServerAnswer();
            MainGuiController controller = (MainGuiController) getControllerFromName(MAIN_GUI);
            if(message instanceof ModifiedTurnMessage){
                controller.showActions(actionCheckers);
                return;
            }
            else if (message instanceof MoveMessage) {
                Move move = ((MoveMessage) message).getMessage();
                controller.move(move.getOldPosition().getRow(), move.getOldPosition().getColumn(),
                        move.getNewPosition().getRow(), move.getNewPosition().getColumn());
            } else if (message instanceof BuildMessage) {
                Couple build = ((BuildMessage) message).getMessage();
                boolean dome = modelView.getBoard().getGrid()[build.getRow()][build.getColumn()].isDome();
                controller.build(build.getRow(), build.getColumn(), dome, ((BuildMessage) message).getAction());
            } else if (message instanceof DoubleMoveMessage) {
                defineDoubleMove((DoubleMoveMessage) message, controller);
            }
            controller.normalCell();
            if(modelView.isTurnActive()) {
                controller.showActions(actionCheckers);
                deselectWorkers(controller);
            }
        });
    }

    /**
     * Method defineDoubleMove defines type of DoubleMove.
     *
     * @param message of type DoubleMoveMessage - the message from the server.
     * @param controller of type MainGuiController - the MainGuiController reference.
     */
    private void defineDoubleMove(DoubleMoveMessage message, MainGuiController controller) {
        int oldRow1 = message.getMyMove().getOldPosition().getRow();
        int oldCol1 = message.getMyMove().getOldPosition().getColumn();
        int oldRow2 = message.getMyMove().getNewPosition().getRow();
        int oldCol2 = message.getMyMove().getNewPosition().getColumn();

        if (message.getMessage().equals("ApolloDoubleMove")) {
            controller.apolloDoubleMove(oldRow1, oldCol1, oldRow2, oldCol2);
        }
        else if (message.getMessage().equals("MinotaurDoubleMove")) {
            int newRow2 = message.getOtherMove().getNewPosition().getRow();
            int newCol2 = message.getOtherMove().getNewPosition().getColumn();
            controller.minotaurDoubleMove(oldRow1, oldCol1, oldRow2, oldCol2, newRow2, newCol2);
        }
    }

    /**
     * Method showSpacesList receives spaces from server and calls highlight cell method of the controller.
     */
    private void showSpacesList() {
        Platform.runLater(() -> {
            MainGuiController controller = (MainGuiController) getControllerFromName(MAIN_GUI);
            controller.highlightCell(actionCheckers[1]);
            deselectWorkers(controller);
        });
    }

    /**
     * Method deselectWorkers deselects workers.
     *
     * @param controller of type MainGuiController - MainGuiController reference.
     */
    private void deselectWorkers(MainGuiController controller) {
        for(int i=1; i<3; i++) {
            controller.getWorkerFromGrid(modelView.getBoard().getWorkerPosition(modelView.getColor(), i).getRow(),
                    modelView.getBoard().getWorkerPosition(modelView.getColor(), i).getColumn()).deselect();
        }
    }

    /**
     * Method connectionClosed closes client's connection.
     * @param evt of type PropertyChangeEvent - close connection event.
     */
    private void connectionClosed(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if(activeGame) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Connection closed");
                alert.setHeaderText("Connection closed from the server");
                if (evt.getNewValue() != null) {
                    alert.setContentText(evt.getNewValue().toString());
                }
                alert.showAndWait();
                System.exit(0);
            }
        });
    }

    /**
     * Main class of the GUI, which is called from the "Santorini" launcher in case user decides to play with it.
     * @param args of type String[] - parsed arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
