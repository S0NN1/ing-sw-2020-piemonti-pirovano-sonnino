package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.shapes.Block;
import it.polimi.ingsw.client.gui.shapes.Dome;
import it.polimi.ingsw.client.gui.shapes.Worker;
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
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.Arrays;

public class ResizeHandler {
  private Pane pane;
  private ArrayList<Node> nodes = new ArrayList<>();
  private ArrayList<ArrayList<Double>> properties = new ArrayList<>();
  private ChangeListener<Number> widthListener;
  private ChangeListener<Number> heightListener;
  private double rootWidth;
  private double rootHeight;

  public ResizeHandler(Pane pane) {
    calculateResize(pane, pane);
  }

  public void calculateResize(Pane pane, Pane parent) {
    if (pane.equals(parent)) {
      properties.clear();
      nodes.clear();
      rootWidth = pane.getWidth();
      rootHeight = pane.getHeight();
    }
    double fontSize = 0;
    this.pane = pane;
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
            } else if (nodes.get(i) instanceof Worker) {
              nodes.get(i).setScaleX(properties.get(i).get(0) * newSceneWidth.intValue());
              (nodes.get(i)).setLayoutX(properties.get(i).get(2) * newSceneWidth.intValue());
            } else if (nodes.get(i) instanceof Block) {
              ((Block) nodes.get(i)).setWidth(properties.get(i).get(0) * newSceneWidth.intValue());
              (nodes.get(i)).setLayoutX(properties.get(i).get(2) * newSceneWidth.intValue());
            } else if (nodes.get(i) instanceof Dome) {
              ((Dome) nodes.get(i)).setRadiusX(properties.get(i).get(0) * newSceneWidth.intValue());
              (nodes.get(i)).setLayoutX(properties.get(i).get(2) * newSceneWidth.intValue());
            } else if (nodes.get(i) instanceof AnchorPane
                && nodes.get(i).getId() != null
                && nodes.get(i).getId().equalsIgnoreCase("#gridPane")) {
              double offset = 0;
              nodes.get(i).setScaleX(newSceneWidth.doubleValue() / rootHeight);
              offset =
                  ((nodes.get(i).getScaleX() - 1) / 2)
                      * properties.get(i).get(0)
                      * newSceneWidth.doubleValue();
              (nodes.get(i))
                  .setLayoutX(offset + properties.get(i).get(2) * newSceneWidth.doubleValue());
            } else if(nodes.get(i) instanceof Region){
              ((Region) nodes.get(i))
                  .setPrefWidth(properties.get(i).get(0) * newSceneWidth.intValue());
              // ((Region) nodes.get(i)).setPrefHeight(properties.get(i).get(0) *
              // newSceneWidth.intValue());
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
            } else if (nodes.get(i) instanceof Worker) {
              for (int j = 1; j < ((Worker) nodes.get(i)).getPoints().size(); j += 2) {
                ((Worker) nodes.get(i))
                    .getPoints()
                    .set(j, properties.get(i).get(1) * newSceneHeight.intValue());
              }
              //  (nodes.get(i)).prefHeight(properties.get(i).get(1) * newSceneHeight.intValue());
              // (nodes.get(i)).setLayoutY(properties.get(i).get(3) * newSceneHeight.intValue());
            } else if (nodes.get(i) instanceof Block) {
              //((Block) nodes.get(i)).setHeight(properties.get(i).get(1) * newSceneHeight.intValue());
              (nodes.get(i)).setLayoutY(properties.get(i).get(3) * newSceneHeight.intValue());
            } else if (nodes.get(i) instanceof Dome) {
              ((Dome) nodes.get(i))
                  .setRadiusY(properties.get(i).get(1) * newSceneHeight.intValue());
              (nodes.get(i)).setLayoutY(properties.get(i).get(3) * newSceneHeight.intValue());
            } else if (nodes.get(i) instanceof AnchorPane && nodes.get(i).getId()!=null&& nodes.get(i).getId().equalsIgnoreCase("#gridPane")) {
              double offset = 0;
              nodes.get(i).setScaleY(newSceneHeight.doubleValue() / rootHeight);
              offset =((nodes.get(i).getScaleY()-1)/2)*properties.get(i).get(1)*newSceneHeight.doubleValue();
              (nodes.get(i)).setLayoutY(offset + properties.get(i).get(3) * newSceneHeight.doubleValue());
            }else if (nodes.get(i) instanceof Region) {
              ((Region) nodes.get(i))
                  .setPrefHeight(properties.get(i).get(1) * newSceneHeight.intValue());
              nodes.get(i).setLayoutY(properties.get(i).get(3) * newSceneHeight.intValue());
            }
          }
        };
  }

  public ChangeListener<Number> getWidthListener() {
    return widthListener;
  }

  public ChangeListener<Number> getHeightListener() {
    return heightListener;
  }
}
