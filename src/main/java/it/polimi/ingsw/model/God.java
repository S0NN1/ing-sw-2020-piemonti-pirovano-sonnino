package it.polimi.ingsw.model;

/**
 * This class is a box for importing the gods information from the relative JSON file.
 * @see it.polimi.ingsw.model.Card for more information.
 * @author Luca Pirovano, Nicolò Sonnino
 */
public class God {
    private String name;
    private String property;
    private String desc;

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
