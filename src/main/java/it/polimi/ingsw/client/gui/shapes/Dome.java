package it.polimi.ingsw.client.gui.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Dome class is the gui representation of block dome.
 *
 * @author Alice Piemonti
 * @see Ellipse
 */
public class Dome extends Ellipse {
  /** Constructor Dome creates a new Dome instance. */
  public Dome(double width, double height) {
    setStyle("-fx-background-color: blue");
    setRadiusX(width * 0.174);
    setRadiusY(width * 0.174);
    setStroke(Color.BLUE);
  }
}
