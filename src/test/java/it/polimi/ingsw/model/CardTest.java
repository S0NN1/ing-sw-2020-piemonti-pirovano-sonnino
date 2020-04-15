package it.polimi.ingsw.model;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Luca Pirovano
 */
class CardTest {

    /**
     * Input parsing test, check the correct working of the class.
     * @throws IllegalArgumentException if the received string is not an element of the enumeration class.
     */
    @Test
    @DisplayName("Input parsing test")
    void parseTest() throws IllegalArgumentException{
        for(Card card:Card.values()) {
            assertEquals(Card.parseInput(card.toString()), card);
        }
        assertThrows(IllegalArgumentException.class, () -> Card.parseInput("someStuffs"));
    }

    /**
     * This tests tries the listing of the gods, which is used during the challenger phase.
     */
    @Test
    @DisplayName("God listing test")
    void listTest() {
        List<String> result = new ArrayList<>();
        result = Card.godsName();
        for(int i=0; i<result.size(); i++) {
            assertEquals(result.get(i).toUpperCase(), Card.values()[i].toString());
        }
    }

    /**
     * This test checks the correct description deploying of the selected god.
     */
    @Test
    @DisplayName("God description parsing test")
    void descriptionTest() throws FileNotFoundException {
        Gson gson = new Gson();

        God[] gods = gson.fromJson(new FileReader("src/main/resources/json/gods.json"), God[].class);
        for (Card card : Card.values()) {
            for (God god : gods) {
                if (god.getName().equalsIgnoreCase(card.name())) {
                    assertTrue(card.godsDescription().contains(god.getDesc()));
                }
            }
        }
    }
}