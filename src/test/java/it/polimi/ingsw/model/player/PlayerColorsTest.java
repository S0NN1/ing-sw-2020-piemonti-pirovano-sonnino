package it.polimi.ingsw.model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Luca Pirovano
 */
class PlayerColorsTest {

    /**
     * Initialization for a test: reset of the selectable colors.
     */
    @BeforeEach
    void setUp() {
        PlayerColors.reset();
    }

    /**
     * This test tries the basic logic of the color selection, verifying the selection and reset procedure.
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
     * This test tries if an already selected has been correctly monitored.
     */
    @Test
    @DisplayName("isChosen attribute validity test")
    void isChosenTest() {
        PlayerColors.choose(PlayerColors.GREEN);
        assertTrue(PlayerColors.isChosen(PlayerColors.GREEN));
    }

    /**
     * This test tries the parsing of the input.
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