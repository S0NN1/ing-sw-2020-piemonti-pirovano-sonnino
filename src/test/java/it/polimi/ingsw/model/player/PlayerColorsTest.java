package it.polimi.ingsw.model.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerColorsTest {

    @BeforeEach
    void setUp() {
        PlayerColors.reset();
    }

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

    @Test
    @DisplayName("isChosen attribute validity test")
    void isChosenTest() {
        PlayerColors.choose(PlayerColors.GREEN);
        assertTrue(PlayerColors.isChosen(PlayerColors.GREEN));
    }

    @Test
    @DisplayName("Input parsing test")
    void inputParsing() {
        assertEquals(PlayerColors.GREEN, PlayerColors.parseInput("green"));
        assertEquals(PlayerColors.RED, PlayerColors.parseInput("Red"));
        assertEquals(PlayerColors.BLUE, PlayerColors.parseInput("BlUe"));
        assertThrows(IllegalArgumentException.class, () -> PlayerColors.parseInput("pino"));
    }

}