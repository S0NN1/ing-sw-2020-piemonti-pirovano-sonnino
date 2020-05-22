package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.Santorini;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This enumeration class contains the god list and parses the input for them.
 * It also contains an array of gods which are chosen by the challenger in the first phase of the game.
 * @author Luca Pirovano
 */
public enum Card {
    APOLLO, ARES, ARTEMIS, ATHENA, ATLAS, CHARON, DEMETER, HEPHAESTUS, HESTIA, MINOTAUR, PAN, PROMETHEUS, TRITON, ZEUS;

    /**
     * Parse the input of the challenger player to get a valid god.
     * @param input the text entered by the player.
     * @return the value of the enum choice if exists, otherwise throws an IllegalArgumentException.
     */
    public static Card parseInput(String input) {
        return Enum.valueOf(Card.class, input.toUpperCase());
    }

    /**
     * @return a list with all gods' name, parsed from the json file.
     */
    public static List<String> godsName() {
        Gson gson = new Gson();
        List<String> result = new ArrayList<>();
        Reader reader = new InputStreamReader(Santorini.class.getResourceAsStream("/json/gods.json"));
        God[] god = gson.fromJson(reader, God[].class);
        for(God god1:god) {
            result.add(god1.getName());
        }
        return result;
    }

    /**
     * Parse all gods' description from JSON file and return the selected god's description.
     * @return the god's description that the user wants to know.
     */
    public String godsDescription() {

        Gson gson = new Gson();
        Reader reader = new InputStreamReader(Santorini.class.getResourceAsStream("/json/gods.json"));
        God[] god = gson.fromJson(reader, God[].class);
        for(God god1:god) {
            if (god1.getName().equalsIgnoreCase(this.toString())) {
                return("Property: " + god1.getProperty() + "\nDescription: " + god1.getDesc());
            }
        }

        return null;
    }
}
