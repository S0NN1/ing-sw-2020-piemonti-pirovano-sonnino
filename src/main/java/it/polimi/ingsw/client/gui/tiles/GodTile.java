package it.polimi.ingsw.client.gui.tiles;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class GodTile extends HBox {
    private final StackPane mainPane;
    private final ImageView god;
    private final ImageView container;
    private VBox buttons;
    private Label name;
    private Button add, desc;

    public GodTile(String path) {
        super();
        String styleSheet = getClass().getResource("/css/god.css").toExternalForm();
        this.setPadding(new Insets(5));
        this.setSpacing(10);
        mainPane = new StackPane();
        Image img = new Image(path);
        god = new ImageView(img);
        container = new ImageView(getClass().getResource("/graphics/bgs/bg_gods.png").toExternalForm());
        mainPane.getChildren().addAll(god, container);
        mainPane.setAlignment(Pos.CENTER);
        this.getChildren().addAll(mainPane);
        buttons = new VBox();

        name = new Label("PROMETHEUS");
        name.getStylesheets().addAll(styleSheet);
        name.getStyleClass().addAll("name");

        add = new Button("ADD");
        add.getStylesheets().addAll(styleSheet);
        add.getStyleClass().addAll("button");
        add.setOnMouseClicked(mouseEvent -> add());

        desc = new Button("DESC");
        desc.getStylesheets().addAll(styleSheet);
        desc.getStyleClass().addAll("button");
        desc.setOnMouseClicked(mouseEvent -> desc());

        buttons.getChildren().addAll(name, add, desc);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);
        this.getChildren().addAll(buttons);
    }

    public void add() {
        System.out.println("KEK");
    }

    public void desc() {
        System.out.println("LMAO");
    }
}
