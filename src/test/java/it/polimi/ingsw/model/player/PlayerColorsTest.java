package it.polimi.ingsw.model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PlayerColorTest class tests PlayerColor class.
 * @author Luca Pirovano
 * @see PlayerColors
 */
class PlayerColorsTest {

    /**
     * Method setup initializes the test: resets of the selectable colors.
     */
    @BeforeEach
    void setup() {
        PlayerColors.reset();
    }

    /**
     * Method colorResetTest tries the basic logic of the color selection, verifying the selection and reset procedure.
     */
    @Test
    @DisplayName("Color choose and reset validity test")
    void colorResetTest() {
        assertEquals(3, PlayerColors.notChosen().size());
        PlayerColors.choose(PlayerColors.RED);
        PlayerColors.choose(PlayerColors.GREEN);
        assertEquals(1, PlayerColors.notChosen().size());
        PlayerColors.reset();
        assertEquals(3, PlayerColors.notChosen().size());
    }

    /**
     * Method isChosenTest tries if an already selected has been correctly monitored.
     */
    @Test
    @DisplayName("isChosen attribute validity test")
    void isChosenTest() {
        PlayerColors.choose(PlayerColors.GREEN);
        assertTrue(PlayerColors.isChosen(PlayerColors.GREEN));
    }

    /**
     * Method inputParsing tries the parsing of the input.
     */
    @Test
    @DisplayName("Input parsing test")
    void inputParsing() {
        assertEquals(PlayerColors.GREEN, PlayerColors.parseInput("green"));
        assertEquals(PlayerColors.RED, PlayerColors.parseInput("Red"));
        assertEquals(PlayerColors.BLUE, PlayerColors.parseInput("BlUe"));
        assertThrows(IllegalArgumentException.class, () -> PlayerColors.parseInput("pino"));
    }

}