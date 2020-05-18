package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.client.gui.controllers.GUIController;
import it.polimi.ingsw.client.gui.controllers.LoaderController;
import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.server.answers.*;
import it.polimi.ingsw.server.answers.worker.BuildMessage;
import it.polimi.ingsw.server.answers.worker.DoubleMoveMessage;
import it.polimi.ingsw.server.answers.worker.MoveMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
 * The main GUI class. It starts the graphical user interface, mapping each scene to the specific phase. It's triggered
 * when an event change is reported by the server, calling the correct components in order to make the local modification.
 * @author Luca Pirovano
 */
public class GUI extends Application implements UI {

    private ConnectionSocket connection = null;
    private final PropertyChangeSupport observers = new PropertyChangeSupport(this);
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private final Logger logger = Logger.getLogger(getClass().getName());

    private final boolean activeGame;
    private MainGuiController guiController;

    /**
     * Maps each scene name to the effective scene object, in order to easily find it during scene changing operations.
     */
    private final HashMap<String, Scene> nameMAPscene = new HashMap<>();
    /**
     * Maps each scene controller's name to the effective controller object, in order to get the correct controller
     * for modifying operations.
     * @see it.polimi.ingsw.client.gui.controllers for more details.
     */
    private final HashMap<String, GUIController> nameMAPcontroller = new HashMap<>();

    private static final String MAINGUI = "mainScene.fxml";
    private static final String MENU = "MainMenu.fxml";
    private static final String LOADER = "loading.fxml";
    private static final String SETUP = "setup.fxml";

    private Scene currentScene;
    private Stage stage;

    public GUI() {
        this.modelView = new ModelView(this);
        actionHandler = new ActionHandler(this, modelView);
        activeGame = true;
    }

    /**
     * Set the title of the main stage and launch the window.
     */
    public void run() {
        stage.setTitle("Santorini");
        stage.setScene(currentScene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        setup();
        this.stage = stage;
        this.stage.setResizable(false);
        run();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    /**
     * GUI setup method. It creates all the stage phase, which will be updated in another methods; in particular:
     * - MENU: the game main menu, with Play and Quit buttons;
     * - SETUP: small windows in which each player will insert his nickname and the server IP and port;
     * - LOADER: the game loader, in which players will choose their color, god power and place their workers;
     * - GUI: the effective game GUI (island board).
     * Each stage scene is put inside an hashmap, which link their name to their fxml filename.
     */
    public void setup() {
        List<String> fxmlist = new ArrayList<>(Arrays.asList(MAINGUI, MENU, LOADER, SETUP));
        try {
            for (String path : fxmlist) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                nameMAPscene.put(path, new Scene(loader.load()));
                GUIController controller = loader.getController();
                controller.setGui(this);
                nameMAPcontroller.put(path, controller);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        currentScene = nameMAPscene.get(MENU);
    }

    /**
     * @return the actual stage configuration.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @return the property change support listeners, that will be necessary to fire some changes to the correct
     * GUI component.
     */
    public PropertyChangeSupport getObservers() {
        return observers;
    }

    /**
     * Center the application windows in the screen, relying on user's screen height and width, getting them
     * from the Screen java class.
     */
    public void centerApplication() {
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        stage.setX((screenSize.getWidth() - currentScene.getWidth())/2);
        stage.setY((screenSize.getHeight() - currentScene.getHeight())/2);
    }

    /**
     * Change the stage scene, relying on the ones declared during setup phase. When this method is call, the actual
     * stage scene is replaced from the parameter one.
     * @param newScene the scene to be put live.
     */
    public void changeStage(String newScene) {
        currentScene = nameMAPscene.get(newScene);
        stage.setScene(currentScene);
        stage.show();
    }

    /**
     * @return the connection to the server, which will be used to send messages or similar stuffs.
     */
    public ConnectionSocket getConnection() {
        return connection;
    }

    /**
     * Set the actual server connection
     * @param connection
     */
    public void setConnection(ConnectionSocket connection) {
        if(this.connection==null) {
            this.connection = connection;
        }
    }

    /**
     * @return the client ModelView, which contains information about the actual game state.
     */
    public ModelView getModelView() {
        return modelView;
    }

    /**
     * @return the client action handler.
     */
    public ActionHandler getActionHandler() {
        return actionHandler;
    }

    /**
     * Get a scene controller relying on his name, from the dedicated hashmap.
     * @param name the name of the controller to be retrieved.
     * @return the correct controller.
     */
    public GUIController getControllerFromName(String name) {
        return nameMAPcontroller.get(name);
    }

    /**
     * Handles the error received from the server, relying on their type.
     * @param error the game error received from the server.
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
     * Handles the first game phase, which contains the nicknames choosing, the challenger phase and the
     * workers placement.
     * @param cmd the command answer received from the server.
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
                Platform.runLater(() -> controller.workerPlacement(((WorkerPlacement)modelView.getServerAnswer()).getAvailableCoordinates()));
            }
            default -> {
                logger.log(Level.WARNING, "No action to be performed!");
            }
        }
    }

    /**
     * Handles all the custom message received from the server, making different things relying on their content.
     * @param msg the custom message.
     */
    public void customMessageHandling(String msg) {
        if(modelView.getGamePhase()==0) {
            if(msg.contains("Match starting") || msg.contains("The match has started")) {
                LoaderController controller = (LoaderController)getControllerFromName(LOADER);
                Platform.runLater(() -> controller.setText(msg));
                return;
            } else if(msg.contains("is the challenger")) {
                LoaderController controller = (LoaderController)getControllerFromName(LOADER);
                Platform.runLater(() -> controller.setText(msg.split(" ")[0] + " is the challenger\nHe's choosing gods power!"));
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
            Platform.runLater(() -> {
                LoaderController controller = (LoaderController)getControllerFromName(LOADER);
                controller.displayCustomMessage(msg);
            });
        }
    }

    /**
     * Listener's firing method; it handles all the changing reported from the server and makes things relying on
     * their value.
     * @param evt the property change event fired from the component.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
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
            case "noPossibleMoves" -> System.err.println("No possible moves!");
            case "select" -> showSpacesList();
            case "boardUpdate" -> checkAction(evt.getNewValue());
            /*case "firstBoardUpdate" -> firstUpdateCli();
            case "selectWorker" -> selectWorker();
            case "end" -> end((String)evt.getNewValue());
            case "win" -> {
                output.println(nameMapColor.get(RED) + "YOU WIN!" + nameMapColor.get(RST));
                System.exit(0);
            }
            case "lose" -> {
                output.println(nameMapColor.get(RED) + "YOU LOSE!" + nameMapColor.get(RST));
                output.println(nameMapColor.get(YELLOW) + "Player " + evt.getNewValue() + " has won." + nameMapColor.get(RST));
                System.exit(0);
            }
            case "singleLost" -> System.err.println("All workers blocked, YOU LOSE!");
            case "otherLost" -> otherPlayerLost(evt);
*/
            default -> {
                logger.log(Level.WARNING, "No actions to be performed");
            }
        }
    }

    private void checkAction(Object message) {
        MainGuiController controller = (MainGuiController) getControllerFromName(MAINGUI);
        if ( message instanceof MoveMessage) {
            Move move = (Move) ((MoveMessage) message).getMessage();
            controller.move(move.getOldPosition().getX(), move.getOldPosition().getY(), move.getNewPosition().getX(), move.getNewPosition().getY());
        }
        else if ( message instanceof BuildMessage) {
            boolean dome = ((BuildMessage) message).getDome();
            Couple build = ((BuildMessage) message).getMessage();
            controller.build(build.getX(), build.getY(), dome);
        }
        else if (message instanceof DoubleMoveMessage) {
            defineDoubleMove((DoubleMoveMessage) message, controller);
        }
    }

    private void defineDoubleMove(DoubleMoveMessage message, MainGuiController controller) {
        int oldRow1 = message.getMyMove().getOldPosition().getX();
        int oldCol1 = message.getMyMove().getOldPosition().getY();
        int oldRow2 = message.getMyMove().getNewPosition().getX();
        int oldCol2 = message.getMyMove().getNewPosition().getY();

        if (message.getMessage().equals("ApolloDoubleMove")) {
            controller.apolloDoubleMove(oldRow1, oldCol1, oldRow2, oldCol2);
        }
        else if (message.getMessage().equals("MinotaurDoubleMove")) {
            int newRow2 = message.getOtherMove().getNewPosition().getX();
            int newCol2 = message.getOtherMove().getNewPosition().getY();
            controller.minotaurDoubleMove(oldRow1, oldCol1, oldRow2, oldCol2, newRow2, newCol2);
        }
    }

    private void showSpacesList() {
        MainGuiController controller = (MainGuiController) getControllerFromName(MAINGUI);
        controller.highlightCell();
    }

    /**
     * Handles the connection closed event.
     * @param evt the event above.
     */
    private void connectionClosed(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Connection closed");
            alert.setHeaderText("Connection closed from the server");
            alert.setContentText(evt.getNewValue().toString());
            alert.showAndWait();
            System.exit(0);
        });
    }

    /**
     * Main class of the GUI, which is called from the "Santorini" launcher in case user decides to play with it.
     * @param args like a standard main :)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
