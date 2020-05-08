package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Luca Pirovano
 */
class PlayerTest {

    Player testPlayer;
    private static final String nickname = "player";
    private static final int clientID = 0;

    @BeforeEach
    void setUp() {
        testPlayer = new Player(nickname, clientID);
    }

    @Test
    @DisplayName("Nickname/client ID getter test")
    void nicknameTest() {
        assertEquals(nickname, testPlayer.getNickname());
        assertEquals(clientID, testPlayer.getClientID());
    }

    @Test
    @DisplayName("Color setting/getting test")
    void colorTest() {
        testPlayer.setColor(PlayerColors.GREEN);
        assertEquals(PlayerColors.GREEN, testPlayer.getColor());
    }

    @Test
    @DisplayName("God Card addition test")
    void cardAdditionTest() {
        testPlayer.setColor(PlayerColors.RED);
        testPlayer.setCard(Card.ATLAS, null);
        assertTrue(testPlayer.getWorkers().get(0).getClass().toString().toUpperCase().contains(Card.ATLAS.toString()));
    }

    @Test
    @DisplayName("Player and worker creation test")
    void playerWorkerCreation() {
        for(Card card:Card.values()) {
            Player player = new Player("test", 1);
            player.setColor(PlayerColors.RED);
            if(card.equals(Card.ATHENA)) {
                player.setCard(Card.ATHENA, null);
            } else {
                player.addWorker(card, null);
                assertTrue(player.getWorkers().get(0).getClass().toString().toUpperCase().contains(card.toString()));
            }
        }
    }
}