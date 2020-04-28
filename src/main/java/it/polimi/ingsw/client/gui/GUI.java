package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.client.gui.controllers.GUIController;
import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    private static final String SETUP = "setup.fxml";
    private Scene currentScene;

    public GUI() {
        this.modelView = new ModelView(this);
        actionHandler = new ActionHandler(this, modelView);
        activeGame = true;
    }

    @Override
    public void start(Stage stage) throws Exception {
        setup();
    }

    public void setup() {
        List<String> fxmlist = new ArrayList<>(Arrays.asList(GUI, MENU, SETUP));
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
        guiController = (MainGuiController)nameMAPcontroller.get(GUI);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
