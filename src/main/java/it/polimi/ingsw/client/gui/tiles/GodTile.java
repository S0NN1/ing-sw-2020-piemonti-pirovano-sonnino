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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GodTile extends HBox {
    private StackPane mainPane;
    private ImageView god;
    private ImageView container;
    private Button close;
    private HBox buttons;
    private Label label1;
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


        ImageView god = new ImageView(getClass().getResource("/graphics/gods/" + card.toString() + ".png").toExternalForm());
        Pane godPane = new Pane();
        godPane.getChildren().add(god);
        god.setLayoutX(3);
        god.setLayoutY(100);
        god.setFitHeight(344);
        god.setPreserveRatio(true);
        mainPane.getChildren().add(godPane);

        container = new ImageView(getClass().getResource("/graphics/bgs/versus_bg1.png").toExternalForm());
        container.setFitWidth(400);
        container.setPreserveRatio(true);
        mainPane.getChildren().add(container);

        //nameBackground
        Pane imageBluePane = new Pane();
        ImageView img1 = new ImageView(getClass().getResource("/graphics/bgs/god_name.png").toExternalForm());
        img1.setFitHeight(77);
        img1.setFitWidth(245);
        img1.setLayoutX(17);
        img1.setLayoutY(31);
        imageBluePane.getChildren().add(img1);
        mainPane.getChildren().add(imageBluePane);

        //godName
        label1 = new Label(card.toString());
        label1.getStylesheets().addAll(styleSheet);
        label1.getStyleClass().addAll("name");
        Pane godName = new Pane(label1);
        label1.setLayoutX(68);
        label1.setLayoutY(51);
        mainPane.getChildren().add(godName);

        //description box
        VBox desc = new VBox();
        desc.setPadding(new Insets(41,0,0,275));
        Label label = new Label("Description:");
        godName.getChildren().add(desc);
        label.setFont(new Font("Arial Bold", 24));

        Label descText = new Label();
        descText.setFont(new Font("Arial", 20));
        descText.setMaxWidth(134);
        descText.setText("Your Turn: If your Worker does not move up, it may build both before and after moving");
        descText.setWrapText(true);
        desc.getChildren().addAll(label,descText);
        desc.setSpacing(10);

        add = new Button("ADD");
        add.getStylesheets().addAll(styleSheet);
        add.getStyleClass().addAll("button");
        add.setOnMouseClicked(mouseEvent -> add(card));


        /*desc = new Button("DESC");
        desc.getStylesheets().addAll(styleSheet);
        desc.getStyleClass().addAll("button");
        desc.setOnMouseClicked(mouseEvent -> desc(card));*/

        close = new Button("CLOSE");
        close.getStylesheets().addAll(styleSheet);
        close.getStyleClass().addAll("button");
        close.setOnMouseClicked(mouseEvent -> close());

        buttons = new HBox();
        buttons.getChildren().addAll(add, close);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        mainPane.getChildren().add(buttons);
        buttons.setPadding(new Insets(0,0,45,0));

        this.getChildren().add(mainPane);
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
