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
  /**
   * Constructor Dome creates a new Dome instance.
   *
   * @param width of type double - the grid's width.
   * @param height of type double - the grid's height.
   */
  public Dome(double width, double height) {
    setRadiusX(width * 0.174);
    setRadiusY(height * 0.174);
    setFill(Color.BLUE);
    setStroke(Color.BLACK);
  }
}
