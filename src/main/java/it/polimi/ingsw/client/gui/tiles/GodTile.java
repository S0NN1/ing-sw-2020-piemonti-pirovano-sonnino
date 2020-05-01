package it.polimi.ingsw.client.gui.tiles;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GodTile extends VBox {
    private ImageView god;
    public GodTile(String path) {
        super();
        this.setPadding(new Insets(2));
        this.setSpacing(5);
        this.getStylesheets().add(getClass().getResource("/css/god.css").toExternalForm());
        //this.getStyleClass().add("bg");
        this.setAlignment(Pos.BOTTOM_CENTER);
        StackPane stackPane = new StackPane();
        ImageView imageView = new ImageView(new Image(getClass().getResource("/graphics/bgs/fg_panel2.png").toExternalForm()));
        god = new ImageView();
        god.setImage(new Image(path));
        stackPane.getChildren().addAll(god, imageView);
        this.getChildren().addAll(stackPane);
        Button desc = new Button("DESCRIPTION");
        Button buttonType = new Button("ADD");
        buttonType.getStylesheets().add(getClass().getResource("/css/god.css").toExternalForm());
        buttonType.getStyleClass().add("button");
        this.getChildren().add(buttonType);
    }
}
