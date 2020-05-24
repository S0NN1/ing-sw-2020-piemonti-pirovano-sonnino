package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ActionParser;
import it.polimi.ingsw.client.Cell;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.controllers.MainGuiController;
import it.polimi.ingsw.client.gui.shapes.Worker;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.PlayerColors;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GUI11 extends Application {

    Button button;
    int count;
    MainGuiController controller;
    private ArrayList<Couple> buildList;

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
        controller = fxml.getController();

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
        /*controller.addBlock(2,2,1);
        controller.addBlock(2,1,1);
        controller.addBlock(2,1,2);*/

        //apollo move method
        /*gui.getModelView().getBoard().getGrid()[2][2].addLevel();

        gui.getModelView().getBoard().setColor(2,2,Constants.ANSI_BLUE);
        controller.setWorker(2,2,Constants.ANSI_BLUE);
        gui.getModelView().getBoard().setColor(2,1, Constants.ANSI_RED);
        controller.setWorker(2,1,Constants.ANSI_RED);

        gui.getModelView().getBoard().apolloDoubleMove(2,2,2,1);
        controller.apolloDoubleMove(2,2,2,1);*/

        //minotaur move method
        /*gui.getModelView().getBoard().setColor(2,2,Constants.ANSI_BLUE);
        controller.setWorker(2,2);
        gui.getModelView().getBoard().setColor(2,1, Constants.ANSI_RED);
        controller.setWorker(2,1);

        gui.getModelView().getBoard().minotaurDoubleMove(2,2,2,1,2,0);
        controller.minotaurDoubleMove(2,2,2,1,2,0);*/

        //dome
        /*controller.addDome(2,2);
        controller.addDome(2,4);*/

        //event
        /*gui.getModelView().getBoard().setColor(4,4, Constants.ANSI_RED);
        controller.setWorker(4,4);
        gui.getModelView().getBoard().setColor(3,4, Constants.ANSI_BLUE);
        controller.setWorker(3,4);

        gui.getModelView().getBoard().setColor(1,3, Constants.ANSI_CYAN);
        controller.setWorker(1,3);*/

        //gridPane test
        /*GridPane grid = new GridPane();
        controller.getMainAnchor().getChildren().add(grid);
        final int numCols = 5;
        final int numRows = 5 ;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(50);
            grid.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints(50);
            grid.getRowConstraints().add(rowConst);
        }
        controller.getGUI().getModelView().getBoard().setColor(2,3,Constants.ANSI_RED);
        grid.add(new AnchorPane(),2,3);
        grid.add(new Worker(3,2,controller),2,3);
        grid.setGridLinesVisible(true);
        for (Node node: grid.getChildren()) {
            //node.setStyle("-fx-background-color: yellow");
        }*/

        //controller.testDragAndDrop();

        /*gui.getModelView().getBoard().setColor(0,0, Constants.ANSI_CYAN);
        controller.setWorker(0,0);
        gui.getModelView().getBoard().move(0,0,0,1);
        controller.move(0,0,0,1);*/

        //highlight
        /*List<Couple> list = new ArrayList<Couple>();
        list.add(new Couple(1,1));
        list.add(new Couple(3,3));
        list.add(new Couple(0,1));
        list.add(new Couple(2,2));
        list.add(new Couple(2,1));
        controller.getGUI().getModelView().setSelectSpaces(list);
        controller.highlightCell();*/


        //test phases
        Button phase = new Button("next");
        phase.setOnAction(actionEvent -> { incrementCount();
        });
        controller.getCenterAnchor().getChildren().add(phase);

        gui.getModelView().getBoard().setColor(2,2,Constants.ANSI_BLUE);
        gui.getModelView().getBoard().setWorkerNum(2,2,1);
        controller.setWorker(2,2);

        gui.getModelView().getBoard().setColor(2,1, Constants.ANSI_RED);
        gui.getModelView().getBoard().setWorkerNum(2,1,1);
        controller.setWorker(2,1);

        gui.getModelView().getBoard().setColor(0,0, Constants.ANSI_CYAN);
        gui.getModelView().getBoard().setWorkerNum(0,0,1);
        controller.setWorker(0,0);

        gui.getModelView().getBoard().setColor(4,4,Constants.ANSI_BLUE);
        gui.getModelView().getBoard().setWorkerNum(4,4,2);
        controller.setWorker(4,4);

        gui.getModelView().getBoard().setColor(3,4, Constants.ANSI_RED);
        gui.getModelView().getBoard().setWorkerNum(3,4,2);
        controller.setWorker(3,4);

        gui.getModelView().getBoard().setColor(1,0, Constants.ANSI_CYAN);
        gui.getModelView().getBoard().setWorkerNum(1,0,2);
        controller.setWorker(1,0);

        controller.getGUI().getModelView().setColor(Constants.ANSI_RED);

        List<Couple> list = new ArrayList<Couple>();
        list.add(new Couple(2,3));
        list.add(new Couple(2,4));
        list.add(new Couple(3,3));
        list.add(new Couple(4,3));
        list.add(new Couple(4,4));
        controller.getGUI().getModelView().setSelectSpaces(list);

        controller.getGUI().getModelView().setActiveWorker(2);

        buildList = new ArrayList<Couple>();
        buildList.add(new Couple(2,2));
        buildList.add(new Couple(2,3));
        buildList.add(new Couple(2,4));
        buildList.add(new Couple(3,2));
        buildList.add(new Couple(3,4));
        buildList.add(new Couple(4,2));
        buildList.add(new Couple(4,3));
        buildList.add(new Couple(4,4));

        primaryStage.show();
    }
    public void incrementCount() {
        switch (count) {
            case 0 -> controller.selectWorker();
            case 1 -> controller.highlightCell(false);
            case 2 -> controller.normalCell();
            case 3 -> {
                controller.getGUI().getModelView().getBoard().move(3,4,3,3);
                controller.move(3,4,3,3);
            }
            case 4 -> controller.showActions(new boolean[] {true, false, true});
            case 5 -> {
                controller.getGUI().getModelView().setSelectSpaces(buildList);
                controller.highlightCell(true);
            }
            case 6-> controller.normalCell();
            case 7 -> {
                controller.getGUI().getModelView().getBoard().build(2,4,false);
                controller.build(2,4,false);
            }
            case 8 -> {
                controller.getGUI().getModelView().getBoard().build(2,4,false);
                controller.build(2,4,false);
            }
            case 9 -> {
                controller.getGUI().getModelView().getBoard().build(2,4,false);
                controller.build(2,4,false);
            }
            case 10 -> {
                controller.getGUI().getModelView().getBoard().build(2,4,false);
                controller.build(2,4,false);
            }
        }
        count ++;

    }
}
