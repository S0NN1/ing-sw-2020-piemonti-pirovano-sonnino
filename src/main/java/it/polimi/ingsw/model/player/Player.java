package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.player.gods.advancedgods.*;
import it.polimi.ingsw.model.player.gods.simplegods.*;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alice Piemonti
 */
public class Player {
    private final String nickname;
    private final int clientID;
    private PlayerColors color;
    private final ArrayList<Worker> workers;
    private Card card;

    /**
     * constructor
     *
     * @param nickname player's univocal name
     */
    public Player(String nickname, int clientID) {
        this.nickname = nickname;
        color = null;
        this.clientID = clientID;
        workers = new ArrayList<>();
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    /**
     * get Player's nickname
     *
     * @return univocal Nickname
     */
    public String getNickname() {
        return this.nickname;
    }

    public int getClientID() {
        return clientID;
    }

    /**
     * get Player's color
     *
     * @return univocal color
     */
    public PlayerColors getColor() {
        return this.color;
    }

    public void setColor(PlayerColors color) {
        if (this.color == null) {
            this.color = color;
        }
    }

    /**
     * create two instances of worker related to the card received
     *
     * @param card god card
     */
    public void addWorker(Card card, VirtualClient client) {
        WorkerCreator creator = new WorkerCreator();
        workers.add(creator.getWorker(card, color));
        workers.add(creator.getWorker(card, color));
        workers.forEach(n -> n.createListeners(client));
    }

    /**
     * set player's card attribute
     *
     * @param godCard god card
     */
    public void setCard(Card godCard, VirtualClient client) {
        this.card = godCard;
        addWorker(card, client);
    }

    public void setCard(Card godCard, VirtualClient client, TurnController controller) {
        this.card = godCard;
        workers.add(new Athena(color, controller));
        workers.add(new Athena(color, controller));
        workers.forEach(n -> n.createListeners(client));
    }

    public Card getCard() {
        return card;
    }
}
