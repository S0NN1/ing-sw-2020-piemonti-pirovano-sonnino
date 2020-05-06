package it.polimi.ingsw.client.gui.tiles;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class GodTile extends HBox {
    private final StackPane mainPane;
    private Pane fourthLayer;
    private final Stage stage;
    private final GUI gui;
    private boolean value;
    private final String styleSheet;
    private final Card card;
    private final boolean isChoosing;

    public GodTile(Card card, Stage stage, GUI gui, boolean isChoosing) {
        this.gui = gui;
        this.stage = stage;
        this.isChoosing = isChoosing;
        styleSheet = getClass().getResource("/css/god.css").toExternalForm();
        this.setPadding(new Insets(5));
        this.setSpacing(10);
        this.card = card;

        mainPane = new StackPane();
        mainPane.setMaxWidth(400);
        this.getChildren().add(mainPane);

        createFirstLayer();
        createSecondLayer();
        createThirdLayer();
        createFourthLayer();
        createButtons();
    }

    private void createFirstLayer(){    //set the image of the god
        ImageView god = new ImageView(getClass().getResource("/graphics/gods/" + card.toString() + ".png").toExternalForm());
        Pane firstLayer = new Pane();
        firstLayer.getChildren().add(god);
        god.setLayoutX(3);
        god.setLayoutY(100);
        god.setFitHeight(344);
        god.setPreserveRatio(true);
        mainPane.getChildren().add(firstLayer);
    }

    private void createSecondLayer(){   //set the "stone frame"
        ImageView secondLayer = new ImageView(getClass().getResource("/graphics/bgs/versus_bg1.png").toExternalForm());
        secondLayer.setFitWidth(400);
        secondLayer.setPreserveRatio(true);
        mainPane.getChildren().add(secondLayer);
    }

    private void createThirdLayer(){
        //set name blu background
        Pane thirdLayer = new Pane();
        ImageView img1 = new ImageView(getClass().getResource("/graphics/bgs/god_name.png").toExternalForm());
        img1.setFitHeight(77);
        img1.setFitWidth(245);
        img1.setLayoutX(10);
        img1.setLayoutY(31);
        thirdLayer.getChildren().add(img1);
        mainPane.getChildren().add(thirdLayer);

        //god description
        Label descText = new Label();
        thirdLayer.getChildren().add(descText);
        //descText.setFont(new Font("Constantia", 11));
        descText.setMaxWidth(110);
        descText.setMaxHeight(150);
        descText.autosize();
        descText.setText(card.godsDescription());
        descText.setLineSpacing(0);
        descText.setWrapText(true);
        descText.setLayoutX(275);
        descText.setLayoutY(90);

        //power stone background
        ImageView powerBackground = new ImageView(getClass().getResource("/graphics/gods/power/background.png").toExternalForm());
        thirdLayer.getChildren().add(powerBackground);
        powerBackground.setFitHeight(71);
        powerBackground.setPreserveRatio(true);
        powerBackground.setLayoutX(268);
        powerBackground.setLayoutY(350);
    }

    private void createFourthLayer(){
        //godName
        Label label1 = new Label(card.toString());
        label1.getStylesheets().addAll(styleSheet);
        label1.getStyleClass().addAll("name");
        fourthLayer = new Pane(label1);
        label1.setLayoutX(55);
        label1.setLayoutY(50);
        mainPane.getChildren().add(fourthLayer);

        //power image
        ImageView power = new ImageView(getClass().getResource("/graphics/gods/power/" + card.toString() + ".png" ).toExternalForm());
        fourthLayer.getChildren().add(power);
        power.setFitHeight(51);
        power.setPreserveRatio(true);
        power.setLayoutX(276);
        power.setLayoutY(357);
    }

    private void createButtons(){
        //add button
        Button add;
        if(isChoosing) {
            add = new Button("CHOOSE");
            add.getStylesheets().addAll(styleSheet);
            add.getStyleClass().addAll("button");
            add.setOnMouseClicked(mouseEvent -> choose(card));
        }
        else {
            add = new Button("ADD");
            add.getStylesheets().addAll(styleSheet);
            add.getStyleClass().addAll("button");
            add.setOnMouseClicked(mouseEvent -> add(card));
        }

        //close button
        Button close = new Button("CLOSE");
        close.getStylesheets().addAll(styleSheet);
        close.getStyleClass().addAll("button");
        close.setOnMouseClicked(mouseEvent -> close());

        HBox buttons = new HBox();
        buttons.getChildren().addAll(add, close);
        buttons.setSpacing(10);
        fourthLayer.getChildren().add(buttons);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        buttons.setLayoutX(isChoosing ? 55 : 80);
        buttons.setLayoutY(500);
    }

    public void add(Card card) {
        gui.getObservers().firePropertyChange("action", null, "ADDGOD " + card.toString());
        value = true;
        stage.close();
    }

    public void choose(Card card) {
        gui.getObservers().firePropertyChange("action", null, "CHOOSE " + card.toString());
        value = true;
        stage.close();
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
