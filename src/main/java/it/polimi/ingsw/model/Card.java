package it.polimi.ingsw.model;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This enumeration class contains the god list and parses the input for them.
 * It also contains an array of gods which are chosen by the challenger in the first phase of the game.
 * @author Luca Pirovano
 */
public enum Card {
    APOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, HEPHAESTUS, MINOTAUR, PAN, PROMETHEUS;

    private static ArrayList<Card> chosen = new ArrayList<>();

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
        try {
            God[] god = gson.fromJson(new FileReader("src/main/resources/json/gods.json"), God[].class);
            for(God god1:god) {
                result.add(god1.getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Parse all gods' description from JSON file and return the selected god's description.
     * @return the god's description that the user wants to know.
     */
    public String godsDescription() {

        Gson gson = new Gson();

        try {
            God[] god = gson.fromJson(new FileReader("src/main/resources/json/gods.json"), God[].class);
            for(God god1:god) {
                if (god1.getName().equalsIgnoreCase(this.toString())) {
                    return("Property: " + god1.getProperty() + "\nDescription: " + god1.getDesc());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Unexpected god");
    }
}
