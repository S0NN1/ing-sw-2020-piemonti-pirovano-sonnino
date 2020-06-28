package it.polimi.ingsw.client.gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class ResizeHandler handles windows auto resizing.
 *
 * @author Nicolò Sonino
 */
public class ResizeHandler {
  private final ArrayList<Node> nodes = new ArrayList<>();
  private final ArrayList<ArrayList<Double>> properties = new ArrayList<>();
  private ChangeListener<Number> widthListener;
  private ChangeListener<Number> heightListener;
  private double rootWidth;
  private double rootHeight;

  /**
   * Constructor ResizeHandler creates a new ResizeHandler instance.
   *
   * @param pane of type Pane
   */
  public ResizeHandler(Pane pane) {
    calculateResize(pane, pane);
  }

  /**
   * Method calculateResize calculates resizing.
   *
   * @param pane of type Pane - the root pane.
   * @param parent of type Pane - the original root pane.
   */
  public void calculateResize(Pane pane, Pane parent) {
    if (pane.equals(parent)) {
      properties.clear();
      nodes.clear();
      rootWidth = pane.getWidth();
      rootHeight = pane.getHeight();
    }
    double fontSize = 0;
    for (Node node : parent.getChildren()) {
      nodes.add(node);
      Bounds bounds = node.getBoundsInLocal();
      if (node instanceof Labeled) {
        fontSize = ((Labeled) node).getFont().getSize() / pane.getWidth();
      }
      if (node instanceof TextField) {
        fontSize = ((TextField) node).getFont().getSize() / pane.getWidth();
      }
      properties.add(
          new ArrayList<>(
              Arrays.asList(
                  bounds.getWidth() / pane.getWidth(),
                  bounds.getHeight() / pane.getHeight(),
                  node.getLayoutX() / pane.getWidth(),
                  node.getLayoutY() / pane.getHeight(),
                  fontSize)));
      if (node instanceof Pane && !(node instanceof GridPane)) {
        calculateResize(pane, (Pane) node);
      }
    }
    widthListener =
        (observableValue, oldSceneWidth, newSceneWidth) -> {
          if (oldSceneWidth.intValue() <= 0) {
            return;
          }
          for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) instanceof ImageView) {
              ((ImageView) nodes.get(i))
                  .setFitWidth(properties.get(i).get(0) * newSceneWidth.intValue());
              nodes.get(i).setLayoutX(properties.get(i).get(2) * newSceneWidth.intValue());
            } else if (nodes.get(i) instanceof AnchorPane
                && nodes.get(i).getId() != null
                && nodes.get(i).getId().equalsIgnoreCase("#gridPane")) {
              double offset;
              nodes.get(i).setScaleX(newSceneWidth.doubleValue() / rootWidth);
              offset =
                  ((nodes.get(i).getScaleX() - 1) / 2)
                      * properties.get(i).get(0)
                      * newSceneWidth.doubleValue();
              (nodes.get(i))
                  .setLayoutX(offset + properties.get(i).get(2) * newSceneWidth.doubleValue());
            } else if (nodes.get(i) instanceof Region) {
              ((Region) nodes.get(i))
                  .setPrefWidth(properties.get(i).get(0) * newSceneWidth.intValue());
              nodes.get(i).setLayoutX(properties.get(i).get(2) * newSceneWidth.intValue());
              if (nodes.get(i) instanceof Labeled || nodes.get(i) instanceof TextField) {
                int fontSizeTemp = (int) (properties.get(i).get(4) * newSceneWidth.intValue());
                nodes.get(i).setStyle("-fx-font-size: " + fontSizeTemp + "px;");
              }
            }
          }
        };
    heightListener =
        (observableValue, oldSceneHeight, newSceneHeight) -> {
          if (oldSceneHeight.intValue() <= 0) {
            return;
          }
          for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) instanceof ImageView) {
              ((ImageView) nodes.get(i))
                  .setFitHeight(properties.get(i).get(1) * newSceneHeight.intValue());
              nodes.get(i).setLayoutY(properties.get(i).get(3) * newSceneHeight.intValue());
            } else if (nodes.get(i) instanceof AnchorPane
                && nodes.get(i).getId() != null
                && nodes.get(i).getId().equalsIgnoreCase("#gridPane")) {
              double offset;
              nodes.get(i).setScaleY(newSceneHeight.doubleValue() / rootHeight);
              offset =
                  ((nodes.get(i).getScaleY() - 1) / 2)
                      * properties.get(i).get(1)
                      * newSceneHeight.doubleValue();
              (nodes.get(i))
                  .setLayoutY(offset + properties.get(i).get(3) * newSceneHeight.doubleValue());
            } else if (nodes.get(i) instanceof Region) {
              ((Region) nodes.get(i))
                  .setPrefHeight(properties.get(i).get(1) * newSceneHeight.intValue());
              nodes.get(i).setLayoutY(properties.get(i).get(3) * newSceneHeight.intValue());
            }
          }
        };
  }

  /**
   * Method getWidthListener returns the widthListener of this ResizeHandler object.
   *
   * @return the widthListener (type ChangeListener&lt;Number&gt;) of this ResizeHandler object.
   */
  public ChangeListener<Number> getWidthListener() {
    return widthListener;
  }

  /**
   * Method getHeightListener returns the heightListener of this ResizeHandler object.
   *
   * @return the heightListener (type ChangeListener&lt;Number&gt;) of this ResizeHandler object.
   */
  public ChangeListener<Number> getHeightListener() {
    return heightListener;
  }
}
