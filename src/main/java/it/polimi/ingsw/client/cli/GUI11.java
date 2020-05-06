package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.gui.tiles.GodTile;
import it.polimi.ingsw.model.Card;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GUI11 extends Application {

    Button button;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage godDetails = new Stage();
        GodTile godTile = new GodTile(Card.PROMETHEUS, godDetails, null);
        Scene scene = new Scene(godTile);
        godDetails.setScene(scene);
        godDetails.setResizable(false);
        godDetails.showAndWait();

        /*primaryStage.setTitle("Title yee");
        button = new Button();
        button.setText("button ye");

        StackPane pane = new StackPane();
        pane.getChildren().add(button);

        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }
}
