package it.polimi.ingsw.model;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class CardTest tests Card class.
 * @author Luca Pirovano
 * @see Card
 */
class CardTest {

    /**
     * Method parseTest tests input parsing and checks the correct working of the class.
     * @throws IllegalArgumentException when the received string is not an element of the enumeration class.
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
     * Method listTest tries the listing of the gods, which is used during the challenger phase.
     */
    @Test
    @DisplayName("God listing test")
    void listTest() {
        List<String> result;
        result = Card.godsName();
        for(int i=0; i<result.size(); i++) {
            assertEquals(result.get(i).toUpperCase(), Card.values()[i].toString());
        }
    }

    /**
     * Method descriptionTest checks the correct description deploying of the selected god.
     * @throws FileNotFoundException when file is invalid.
     */
    @Test
    @DisplayName("God description parsing test")
    void descriptionTest() throws FileNotFoundException {
        Gson gson = new Gson();

        God[] gods = gson.fromJson(new FileReader("src/main/resources/json/gods.json"), God[].class);
        for (Card card : Card.values()) {
            for (God god : gods) {
                if (god.getName().equalsIgnoreCase(card.name())) {
                    assertTrue(Objects.requireNonNull(card.godsDescription()).contains(god.getDesc()));
                }
            }
        }
    }
}