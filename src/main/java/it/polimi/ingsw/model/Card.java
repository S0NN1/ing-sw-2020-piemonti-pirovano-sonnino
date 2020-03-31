package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.exceptions.CardNotChosenException;
import it.polimi.ingsw.exceptions.DuplicateGodException;

import java.io.*;
import java.util.ArrayList;

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
     * Add the card selected by the challenger to chosen god cards list.
     * @param card the selected card.
     * @throws DuplicateGodException if the chosen card is already inside the deck of active god power cards.
     */
    public static void alreadyAdded(Card card) throws DuplicateGodException {
        if(chosen.contains(card)) {
            throw new DuplicateGodException();
        }
        chosen.add(card);
    }

    /**
     * Parse the input of the player, when he is choosing his god-card.
     * @param input the text input entered by the player.
     * @return the selected card, if present.
     * @throws CardNotChosenException if the card was not chosen by the challenger.
     */
    public static Card parseChoice(String input) throws CardNotChosenException {
        Card card = Enum.valueOf(Card.class, input.toUpperCase());
        if (!chosen.remove(card)) {
            throw new CardNotChosenException();
        }
        return card;
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
