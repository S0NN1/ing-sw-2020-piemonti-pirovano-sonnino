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

    public String getName() {
        return name;
    }

    public String getProperty() {
        return property;
    }

    public String getDesc() {
        return desc;
    }
}
