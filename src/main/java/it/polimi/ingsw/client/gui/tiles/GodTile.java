package it.polimi.ingsw.client.gui.tiles;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * GodTile class displays gods and their panel.
 * @author Alice Piemonti
 * @see HBox
 */
public class GodTile extends HBox {
    private final StackPane mainPane;
    private Pane fourthLayer;
    private final Stage stage;
    private final GUI gui;
    private boolean value;
    private final String styleSheet;
    private final Card card;
    private final boolean isChoosing;

    /**
     * Constructor GodTile creates a new GodTile instance.
     *
     * @param card of type Card  - the god's name and power.
     * @param stage of type Stage - the window's content.
     * @param gui of type GUI - GUI reference.
     * @param isChoosing of type boolean true if player is choosing god power, false otherwise.
     */
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

    /**
     * Method createFirstLayer sets god's image layer.
     */
    private void createFirstLayer(){
        ImageView god = new ImageView(getClass().getResource("/graphics/gods/" + card.toString() + ".png").
                toExternalForm());
        Pane firstLayer = new Pane();
        firstLayer.getChildren().add(god);
        god.setLayoutX(3);
        god.setLayoutY(100);
        god.setFitHeight(344);
        god.setPreserveRatio(true);
        mainPane.getChildren().add(firstLayer);
    }

    /**
     * Method createSecondLayer sets the stone fragment's layer.
     */
    private void createSecondLayer(){
        ImageView secondLayer = new ImageView(getClass().getResource("/graphics/bgs/versus_bg1.png").
                toExternalForm());
        secondLayer.setFitWidth(400);
        secondLayer.setPreserveRatio(true);
        mainPane.getChildren().add(secondLayer);
    }

    /**
     * Method createThirdLayer sets god's name background and description layer.
     */
    private void createThirdLayer(){
        Pane thirdLayer = new Pane();
        ImageView img1 = new ImageView(getClass().getResource("/graphics/bgs/god_name.png").toExternalForm());
        img1.setFitHeight(77);
        img1.setFitWidth(245);
        img1.setLayoutX(10);
        img1.setLayoutY(31);
        thirdLayer.getChildren().add(img1);
        mainPane.getChildren().add(thirdLayer);

        Label descText = new Label(card.godsDescription());
        thirdLayer.getChildren().add(descText);
        descText.setFont(new Font("Constantia", 11));
        descText.setMaxWidth(110);
        descText.setMaxHeight(USE_PREF_SIZE);
        if(descText.getMaxHeight() > 150 ) {
            descText.setFont(new Font("Constantia", 2));
        }
        descText.setMaxHeight(150);
        descText.setLineSpacing(0);
        descText.setWrapText(true);
        descText.setLayoutX(275);
        descText.setLayoutY(90);

        ImageView powerBackground = new ImageView(getClass().getResource("/graphics/gods/power/panel_hero.png").toExternalForm());
        thirdLayer.getChildren().add(powerBackground);
        powerBackground.setFitHeight(75);
        powerBackground.setFitWidth(145);
        powerBackground.setLayoutX(261);
        powerBackground.setLayoutY(405);
    }

    /**
     * Method createFourthLayer sets god's name and power icon layer.
     */
    private void createFourthLayer(){
        Label label1 = new Label(card.toString());
        label1.getStylesheets().addAll(styleSheet);
        label1.getStyleClass().addAll("name");
        fourthLayer = new Pane(label1);
        label1.setLayoutX(55);
        label1.setLayoutY(50);
        mainPane.getChildren().add(fourthLayer);

        ImageView power = new ImageView(getClass().getResource("/graphics/gods/power/" + card.toString() + ".png" ).toExternalForm());
        fourthLayer.getChildren().add(power);
        power.setFitHeight(38);
        power.setPreserveRatio(true);
        power.setLayoutX(283);
        power.setLayoutY(420);
    }

    /**
     * Method createButtons creates buttons.
     */
    private void createButtons(){
        final String button = "button";
        //add button
        Button add;
        if(isChoosing) {
            add = new Button("CHOOSE");
            add.getStylesheets().addAll(styleSheet);
            add.getStyleClass().addAll(button);
            add.setOnMouseClicked(mouseEvent -> choose(card));
        }
        else {
            add = new Button("ADD");
            add.getStylesheets().addAll(styleSheet);
            add.getStyleClass().addAll(button);
            add.setOnMouseClicked(mouseEvent -> add(card));
        }

        //close button
        Button close = new Button("CLOSE");
        close.getStylesheets().addAll(styleSheet);
        close.getStyleClass().addAll(button);
        close.setOnMouseClicked(mouseEvent -> close());

        HBox buttons = new HBox();
        buttons.getChildren().addAll(add, close);
        buttons.setSpacing(10);
        fourthLayer.getChildren().add(buttons);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        buttons.setLayoutX(isChoosing ? 55 : 80);
        buttons.setLayoutY(500);
    }

    /**
     * Method add sets addGod button.
     *
     * @param card of type Card - the god's card.
     */
    public void add(Card card) {
        gui.getListeners().firePropertyChange("action", null, "ADDGOD " + card.toString());
        value = true;
        stage.close();
    }

    /**
     * Method choose sets chooseGod button.
     *
     * @param card of type Card  - the god's card.
     */
    public void choose(Card card) {
        gui.getListeners().firePropertyChange("action", null, "CHOOSE " + card.toString());
        value = true;
        stage.close();
    }

    /**
     * Method close closes input and window.
     */
    public void close() {
        gui.getModelView().activateInput();
        value = false;
        stage.close();
    }

    /**
     * Method getValue returns the value of this GodTile object.
     *
     *
     *
     * @return the value (type boolean) of this GodTile object.
     */
    public boolean getValue() {
        return value;
    }
}
