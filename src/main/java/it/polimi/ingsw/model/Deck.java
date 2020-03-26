package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();

    public void setCard(Card card) {
        cards.add(card);
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
    public void removeCard(Card card) {
        cards.remove(card);
    }
}
