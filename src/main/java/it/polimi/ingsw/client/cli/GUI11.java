package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Cell;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.player.PlayerColors;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI11 extends Application {

    Button button;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Stage godDetails = new Stage();
        GodTile godTile = new GodTile(Card.MINOTAUR, godDetails, null, false);
        Scene scene = new Scene(godTile);
        godDetails.setScene(scene);
        godDetails.setResizable(false);
        godDetails.showAndWait();*/

        GUI gui = new GUI();
        Cell cell = gui.getModelView().getBoard().getGrid()[0][1];
        cell.setColor(Constants.ANSI_RED);
        cell.addLevel();
        cell = gui.getModelView().getBoard().getGrid()[4][3];
        cell.setColor(Constants.ANSI_YELLOW);
        cell.addLevel();
        cell.addLevel();

        primaryStage.setTitle("Title yee");
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/mainScene.fxml"));
        Scene scene = new Scene(fxml.load());
        primaryStage.setScene(scene);
        MainGuiController controller = fxml.getController();

        controller.setGui(gui);
        controller.showBoard();
        primaryStage.show();

    }
}
