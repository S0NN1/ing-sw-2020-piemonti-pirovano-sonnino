package it.polimi.ingsw.client.gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ResizeHandler {
  private Pane pane;
  private ArrayList<Node> nodes = new ArrayList<>();
  private ArrayList<ArrayList<Double>> properties = new ArrayList<>();
  private ChangeListener<Number> widthListener;
  private ChangeListener<Number> heightListener;

  public ResizeHandler(Pane pane) {
    double fontSize = 0;
    this.pane = pane;
    for (Node node : pane.getChildren()) {
      nodes.add(node);
      Bounds bounds = node.getBoundsInLocal();
      if(node instanceof Labeled){
        fontSize = ((Labeled) node).getFont().getSize()/pane.getWidth();
      }
      if(node instanceof TextField){
        fontSize = ((TextField) node).getFont().getSize()/pane.getWidth();
      }
      properties.add(
          new ArrayList<>(
              Arrays.asList(
                  bounds.getWidth() / pane.getWidth(),
                  bounds.getHeight() / pane.getHeight(),
                  node.getLayoutX() / pane.getWidth(),
                  node.getLayoutY() / pane.getHeight(),
                      fontSize)));
      if (node instanceof Pane) {
        for (Node children : ((Pane) node).getChildren()) {
          nodes.add(children);
          Bounds bounds1 = children.getBoundsInLocal();
          if(children instanceof Labeled){
            fontSize = ((Labeled) children).getFont().getSize()/pane.getWidth();
          }
          if(children instanceof TextField){
            fontSize = ((TextField) children).getFont().getSize()/pane.getWidth();
          }
          properties.add(
              new ArrayList<>(
                  Arrays.asList(
                      bounds1.getWidth() / pane.getWidth(),
                      bounds1.getHeight() / pane.getHeight(),
                      children.getLayoutX() / pane.getWidth(),
                      children.getLayoutY() / pane.getHeight(),
                          fontSize)));
        }
      }
      if (node instanceof Pane) {
        for (Node children : ((Pane) node).getChildren()) {
          nodes.add(children);
          Bounds bounds1 = children.getBoundsInLocal();
          if(children instanceof Labeled){
            fontSize = ((Labeled) children).getFont().getSize()/pane.getWidth();
          }
          if(children instanceof TextField){
            fontSize = ((TextField) children).getFont().getSize()/pane.getWidth();
          }
          properties.add(
                  new ArrayList<>(
                          Arrays.asList(
                                  bounds1.getWidth() / pane.getWidth(),
                                  bounds1.getHeight() / pane.getHeight(),
                                  children.getLayoutX() / pane.getWidth(),
                                  children.getLayoutY() / pane.getHeight(),
                                  fontSize)));
          if (children instanceof Pane) {
            for (Node grandChildren : ((Pane) children).getChildren()) {
              nodes.add(grandChildren);
              Bounds bounds2 = grandChildren.getBoundsInLocal();
              if(grandChildren instanceof Labeled){
                fontSize = ((Labeled) grandChildren).getFont().getSize()/pane.getWidth();
              }
              if(grandChildren instanceof TextField){
                fontSize = ((TextField) grandChildren).getFont().getSize()/pane.getWidth();
              }
              properties.add(
                      new ArrayList<>(
                              Arrays.asList(
                                      bounds2.getWidth() / pane.getWidth(),
                                      bounds2.getHeight() / pane.getHeight(),
                                      grandChildren.getLayoutX() / pane.getWidth(),
                                      grandChildren.getLayoutY() / pane.getHeight(),
                                      fontSize)));
            }
          }
        }
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
            }
            else if(nodes.get(i) instanceof Rectangle){
              ((Rectangle) nodes.get(i)).setWidth(properties.get(i).get(0) * newSceneWidth.intValue());
              ((Rectangle) nodes.get(i)).setLayoutX(properties.get(i).get(2) * newSceneWidth.intValue());
            }
            else {
              ((Region) nodes.get(i))
                  .setPrefWidth(properties.get(i).get(0) * newSceneWidth.intValue());
              nodes.get(i).setLayoutX(properties.get(i).get(2) * newSceneWidth.intValue());
              if(nodes.get(i) instanceof Labeled || nodes.get(i) instanceof TextField ){
                int fontSizeTemp = (int) ( properties.get(i).get(4)*newSceneWidth.intValue());
                nodes.get(i).setStyle("-fx-font-size: " + fontSizeTemp + "px;");
              }
              }
            }

          /*setupGame.setPrefWidth(originalSetupGameWidth * newSceneWidth.intValue());
          setupGame.setLayoutX(originalSetupGamePosX * newSceneWidth.intValue());
          double kekka = (double) 50 / 960;
          int kek = (int) (kekka * newSceneWidth.intValue());
          setupGame.setStyle("-fx-font-size: " + kek + "px;");
          title.setFitWidth(originalTitleWidth * newSceneWidth.intValue());
          title.setLayoutX(originalTitlePosX * newSceneWidth.intValue());*/
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
            }
            else if(nodes.get(i) instanceof Rectangle){
              ((Rectangle) nodes.get(i)).setHeight(properties.get(i).get(0) * newSceneHeight.intValue());
              ((Rectangle) nodes.get(i)).setLayoutY(properties.get(i).get(2) * newSceneHeight.intValue());
            }
            else {
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
