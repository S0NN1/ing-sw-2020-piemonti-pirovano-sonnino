package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * PlayerTest class tests Player class.
 *
 * @author Luca Pirovano
 * @see Player
 */
class PlayerTest {

  Player testPlayer;
  private static final String nickname = "player";
  private static final int clientID = 0;

  /** Method setup setups test. */
  @BeforeEach
  void setup() {
    testPlayer = new Player(nickname, clientID);
  }

  /** Method nicknameTest tests nickname's getter. */
  @Test
  @DisplayName("Nickname/client ID getter test")
  void nicknameTest() {
    assertEquals(nickname, testPlayer.getNickname());
    assertEquals(clientID, testPlayer.getClientID());
  }

  /** Method colorTest tests setColor. */
  @Test
  @DisplayName("Color setting/getting test")
  void colorTest() {
    testPlayer.setColor(PlayerColors.GREEN);
    assertEquals(PlayerColors.GREEN, testPlayer.getColor());
  }

  /** Method cardAdditionTest test the addition of a single card to the player. */
  @Test
  @DisplayName("God Card addition test")
  void cardAdditionTest() {
    testPlayer.setColor(PlayerColors.RED);
    testPlayer.setCard(Card.ATLAS, null, null);
    assertTrue(
        testPlayer
            .getWorkers()
            .get(0)
            .getClass()
            .toString()
            .toUpperCase()
            .contains(Card.ATLAS.toString()));
  }

  /** Method playerWorkerCreation tests player and relatives workers creation. */
  @Test
  @DisplayName("Player and worker creation test")
  void playerWorkerCreation() {
    for (Card card : Card.values()) {
      Player player = new Player("test", 1);
      player.setColor(PlayerColors.RED);
      if (card.equals(Card.ATHENA)) {
        player.setCard(Card.ATHENA, null, null);
      } else {
        player.addWorker(card, null, null);
        assertTrue(
            player
                .getWorkers()
                .get(0)
                .getClass()
                .toString()
                .toUpperCase()
                .contains(card.toString()));
      }
    }
  }
}
