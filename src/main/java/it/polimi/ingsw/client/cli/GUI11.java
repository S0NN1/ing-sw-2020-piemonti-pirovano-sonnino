package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Cell;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.controller.Controller;
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
        primaryStage.setTitle("Title yee");
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/mainScene.fxml"));
        Scene scene = new Scene(fxml.load());
        primaryStage.setScene(scene);
        MainGuiController controller = fxml.getController();

        controller.setGui(gui);
        /*
        Cell cell = gui.getModelView().getBoard().getGrid()[0][1];
        cell.setColor(Constants.ANSI_BLUE);
        cell.addLevel();
        cell = gui.getModelView().getBoard().getGrid()[4][3];
        cell.setColor(Constants.ANSI_YELLOW);
        cell.addLevel();
        cell.addLevel();

        cell.setColor(null);
        gui.getModelView().getBoard().getGrid()[4][2].setColor(Constants.ANSI_BLUE);
        controller.setWorker(4,2,Constants.ANSI_BLUE);

        gui.getModelView().getBoard().move(4,2,4,3);
        controller.move(4,2,4,3);


        gui.getModelView().getBoard().getGrid()[0][2].setColor(Constants.ANSI_RED);
*/
        //add block
        controller.addBlock(2,2,1);
        controller.addBlock(2,1,1);
        controller.addBlock(2,1,2);

        //apollo move method
        /*gui.getModelView().getBoard().getGrid()[2][2].addLevel();

        gui.getModelView().getBoard().setColor(2,2,Constants.ANSI_BLUE);
        controller.setWorker(2,2,Constants.ANSI_BLUE);
        gui.getModelView().getBoard().setColor(2,1, Constants.ANSI_RED);
        controller.setWorker(2,1,Constants.ANSI_RED);

        gui.getModelView().getBoard().apolloDoubleMove(2,2,2,1);
        controller.apolloDoubleMove(2,2,2,1);*/

        //minotaur move method
        gui.getModelView().getBoard().setColor(2,2,Constants.ANSI_BLUE);
        controller.setWorker(2,2,Constants.ANSI_BLUE);
        gui.getModelView().getBoard().setColor(2,1, Constants.ANSI_RED);
        controller.setWorker(2,1,Constants.ANSI_RED);

        gui.getModelView().getBoard().minotaurDoubleMove(2,2,2,1,2,0);
        controller.minotaurDoubleMove(2,2,2,1,2,0);

        controller.addDome(2,2);
        controller.addDome(2,4);

        primaryStage.show();

    }
}
