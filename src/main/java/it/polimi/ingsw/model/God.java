package it.polimi.ingsw.model;

/**
 * God class is a container used for importing the gods information from the relative JSON file.
 *
 * @see Card
 * @author Luca Pirovano, Nicolò Sonnino
 */
public class God {
  private final String name;
  private final String property;
  private final String desc;

  /**
   * Constructor God creates a new God instance.
   *
   * @param name of type String - the name of the God.
   * @param property of type String - god's title.
   * @param desc of type String - god's desc.
   */
  public God(String name, String property, String desc) {
    this.name = name;
    this.property = property;
    this.desc = desc;
  }

  /**
   * Method getName returns the name of this God object.
   *
   * @return the name (type String) of this God object.
   */
  public String getName() {
    return name;
  }

  /**
   * Method getProperty returns the property of this God object.
   *
   * @return the property (type String) of this God object.
   */
  public String getProperty() {
    return property;
  }

  /**
   * Method getDesc returns the desc of this God object.
   *
   * @return the desc (type String) of this God object.
   */
  public String getDesc() {
    return desc;
  }
}
