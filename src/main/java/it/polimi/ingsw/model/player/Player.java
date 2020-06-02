package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.player.gods.advancedgods.*;
import it.polimi.ingsw.model.player.gods.simplegods.*;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alice Piemonti, Luca Pirovano
 */
public class Player {
    private final String nickname;
    private final int clientID;
    private PlayerColors color;
    private final ArrayList<Worker> workers;
    private Card card;


    /**
     * Constructor Player creates a new Player instance.
     *
     * @param nickname of type String the user nickname.
     * @param clientID of type int the user unique id.
     */
    public Player(String nickname, int clientID) {
        this.nickname = nickname;
        color = null;
        this.clientID = clientID;
        workers = new ArrayList<>();
    }

    /**
     * Method getWorkers returns the workers of this Player object.
     *
     * @return the workers (type List<Worker>) of this Player object.
     */
    public List<Worker> getWorkers() {
        return workers;
    }


    /**
     * Method getNickname returns the nickname of this Player object.
     *
     * @return the nickname (type String) of this Player object.
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Method getClientID returns the client ID of this Player object.
     *
     * @return the clientID (type int) of this Player object.
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * Method getColor returns the color of this Player object.
     *
     * @return the color (type PlayerColors) of this Player object.
     */
    public PlayerColors getColor() {
        return this.color;
    }

    /**
     * Method setColor sets the color of this Player object.
     *
     * @param color the color of this Player object.
     */
    public void setColor(PlayerColors color) {
        if (this.color == null) {
            this.color = color;
        }
    }

    /**
     * Method addWorker creates two instances of worker related to the card received from the deck.
     *
     * @param card of type Card the card chosen by the user.
     * @param client of type VirtualClient the user's virtual client representation.
     */
    public void addWorker(Card card, VirtualClient client) {
        WorkerCreator creator = new WorkerCreator();
        workers.add(creator.getWorker(card, color));
        workers.add(creator.getWorker(card, color));
        workers.forEach(n -> n.createListeners(client));
    }


    /**
     * Method setCard sets player's card attribute
     *
     * @param godCard of type Card the chosen card
     * @param client of type VirtualClient the user's virtual client representation.
     */
    public void setCard(Card godCard, VirtualClient client) {
        this.card = godCard;
        addWorker(card, client);
    }

    /**
     * Method setCard sets player's card attribute in case user has chosen the Athena god.
     *
     * @param godCard of type Card the Athena card.
     * @param client of type VirtualClient the user's virtual client representation.
     * @param controller of type TurnController the turn controller reference.
     */
    public void setCard(Card godCard, VirtualClient client, TurnController controller) {
        this.card = godCard;
        workers.add(new Athena(color, controller));
        workers.add(new Athena(color, controller));
        workers.forEach(n -> n.createListeners(client));
    }

    /**
     * Method getCard returns the card of this Player object.
     *
     * @return the card (type Card) of this Player object.
     */
    public Card getCard() {
        return card;
    }
}
