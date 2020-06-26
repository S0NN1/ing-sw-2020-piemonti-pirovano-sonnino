package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Deck represents the cards' deck of the god chosen by the challenger.
 *
 * @author Luca Pirovano
 */
public class Deck {
  private final ArrayList<Card> cards = new ArrayList<>();
  private final Game game;

  /**
   * Constructor Deck creates a new Deck instance.
   *
   * @param game of type Game - the game model.
   */
  public Deck(Game game) {
    this.game = game;
  }

  /**
   * Method setCard adds a card chosen by the challenger to the deck.
   *
   * @param card of type Card - the chosen card.
   * @return int - the request status.
   * @throws OutOfBoundException when the current size of the array is equals to the number of
   *     players.
   */
  public int setCard(Card card) throws OutOfBoundException {
    if (cards.size() == game.getActivePlayers().size()) {
      throw new OutOfBoundException();
    } else if (cards.contains(card)) {
      return 0;
    }
    cards.add(card);
    if (cards.size() == game.getActivePlayers().size()) {
      return 2;
    }
    return 1;
  }

  /**
   * Method getCards returns the cards of this Deck object.
   *
   * @return the cards (type List&lt;Card&gt;) of this Deck object.
   */
  public List<Card> getCards() {
    return cards;
  }

  /**
   * Method chooseCard removes a card afterwards a player god-power choice.
   *
   * @param card of type Card - the card selected by the player.
   * @param client of type VirtualClient - the virtual client associated to the user.
   * @return boolean true if everything goes fine, boolean false otherwise.
   */
  public boolean chooseCard(Card card, VirtualClient client) {
    if (!cards.contains(card)) {
      return false;
    }
    game.getCurrentPlayer().setCard(card, client);
    cards.remove(card);
    return true;
  }

  /**
   * Method chooseCard removes a card afterwards a player god-power choice in case of Athena
   * selection.
   *
   * @param card of type Card - the selected card.
   * @param client of type VirtualClient - the client which has selected Athena.
   * @param controller of type TurnController - the game controller.
   * @return boolean true if everything is ok, boolean false otherwise.
   */
  public boolean chooseCard(Card card, VirtualClient client, TurnController controller) {
    if (!cards.contains(card)) {
      return false;
    }
    game.getCurrentPlayer().setCard(card, client, controller);
    cards.remove(card);
    return true;
  }
}
