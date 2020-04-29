package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.client.gui.controllers.GUIController;
import it.polimi.ingsw.client.gui.controllers.LoaderController;
import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import it.polimi.ingsw.server.answers.GameError;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GUI extends Application implements UI {

    private ConnectionSocket connection;
    private ActionParser actionParser;
    private ModelView modelView;
    private ActionHandler actionHandler;

    private boolean activeGame;
    private MainGuiController guiController;

    private HashMap<String, Scene> nameMAPscene = new HashMap<>();
    private HashMap<String, GUIController> nameMAPcontroller = new HashMap<>();

    private static final String GUI = "gui.fxml";
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

    public void setup() {
        List<String> fxmlist = new ArrayList<>(Arrays.asList(/*GUI,*/ MENU, LOADER, SETUP));
        try {
            for (String path : fxmlist) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                nameMAPscene.put(path, new Scene(loader.load()));
                GUIController controller = loader.getController();
                controller.setGui(this);
                nameMAPcontroller.put(path, controller);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentScene = nameMAPscene.get(MENU);
        //guiController = (MainGuiController)nameMAPcontroller.get(GUI);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getStage() {
        return stage;
    }

    public void centerApplication() {
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        stage.setX((screenSize.getWidth() - currentScene.getWidth())/2);
        stage.setY((screenSize.getHeight() - currentScene.getHeight())/2);
    }

    public void changeStage(String newScene) {
        currentScene = nameMAPscene.get(newScene);
        stage.setScene(currentScene);
        stage.show();
    }

    public void setConnection(ConnectionSocket connection) {
        this.connection = connection;
    }

    public ModelView getModelView() {
        return modelView;
    }

    public ActionHandler getActionHandler() {
        return actionHandler;
    }

    public GUIController getControllerFromName(String name) {
        return nameMAPcontroller.get(name);
    }

    public void errorHandling(GameError error) {}

    public void initialPhaseHandling(String cmd) {
    }

    public void test() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Message from the server");
        //alert.setContentText(evt.getNewValue().toString());
    }

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
                if(modelView.getGamePhase()==0) {
                    Platform.runLater(() -> {
                        LoaderController controller = (LoaderController)getControllerFromName(LOADER);
                        controller.display(evt.getNewValue().toString());
                    });
                }
            }
        }
    }
}
