package it.polimi.ingsw.client.gui.tiles;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class GodTile extends HBox {
    private StackPane mainPane;
    private ImageView god;
    private ImageView container;
    private Button close;
    private VBox buttons;
    private Label name;
    private Button add, desc;
    private Stage stage;
    private GUI gui;
    private boolean value;

    public GodTile(Card card, Stage stage, GUI gui) {
        this.gui = gui;
        this.stage = stage;
        String styleSheet = getClass().getResource("/css/god.css").toExternalForm();
        this.setPadding(new Insets(5));
        this.setSpacing(10);
        mainPane = new StackPane();
        Image img = new Image(getClass().getResource("/graphics/gods/" + card.toString() + ".png").toExternalForm());
        god = new ImageView(img);
        container = new ImageView(getClass().getResource("/graphics/bgs/bg_gods.png").toExternalForm());
        mainPane.getChildren().addAll(god, container);
        mainPane.setAlignment(Pos.CENTER);
        this.getChildren().addAll(mainPane);
        buttons = new VBox();

        name = new Label(card.toString());
        name.getStylesheets().addAll(styleSheet);
        name.getStyleClass().addAll("name");

        add = new Button("ADD");
        add.getStylesheets().addAll(styleSheet);
        add.getStyleClass().addAll("button");
        add.setOnMouseClicked(mouseEvent -> add(card));

        desc = new Button("DESC");
        desc.getStylesheets().addAll(styleSheet);
        desc.getStyleClass().addAll("button");
        desc.setOnMouseClicked(mouseEvent -> desc(card));

        close = new Button("CLOSE");
        close.getStylesheets().addAll(styleSheet);
        close.getStyleClass().addAll("button");
        close.setOnMouseClicked(mouseEvent -> close());

        buttons.getChildren().addAll(name, add, desc, close);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);
        this.getChildren().addAll(buttons);
    }

    public void add(Card card) {
        gui.getObservers().firePropertyChange("action", null, "ADDGOD " + card.toString());
        value = true;
        stage.close();
    }

    public void desc(Card card) {
        gui.getObservers().firePropertyChange("action", null, "GODDESC " + card.toString());
        value = false;
        gui.getModelView().toggleInput();
    }

    public void close() {
        gui.getModelView().toggleInput();
        value = false;
        stage.close();
    }

    public boolean getValue() {
        return value;
    }
}
