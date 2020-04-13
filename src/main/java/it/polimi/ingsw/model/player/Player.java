package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.player.gods.*;

import java.util.ArrayList;

/**
 * @author Alice Piemonti
 */
public class Player {
    private final String nickname;
    private final int clientID;
    private PlayerColors color;
    private boolean isActive;
    private boolean isFirstPlayer;
    private boolean isDead;
    private boolean canMove, canLevelUp, canBuild;
    private ArrayList<Worker> workers;
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

    public ArrayList<Worker> getWorkers() {
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
    public void addWorker(Card card) {
        switch (card) {
            case APOLLO:
                workers.add(new Apollo(color));
                workers.add(new Apollo(color));
                break;
            case ARTEMIS:
                workers.add(new Artemis(color));
                workers.add(new Artemis(color));
                break;
            case ATHENA:
                workers.add(new Athena(color));
                workers.add(new Athena(color));
                break;
            case ATLAS:
                workers.add(new Atlas(color));
                workers.add(new Atlas(color));
                break;
            case DEMETER:
                workers.add(new Demeter(color));
                workers.add(new Demeter(color));
                break;
            case HEPHAESTUS:
                workers.add(new Hephaestus(color));
                workers.add(new Hephaestus(color));
                break;
            case MINOTAUR:
                workers.add(new Minotaur(color));
                workers.add(new Minotaur(color));
                break;
            case PAN:
                workers.add(new Pan(color));
                workers.add(new Pan(color));
                break;
            case PROMETHEUS:
                workers.add(new Prometheus(color));
                workers.add(new Prometheus(color));
                break;
        }
    }

    /**
     * set player's card attribute
     *
     * @param godCard god card
     */
    public void setCard(Card godCard) {
        this.card = godCard;
        addWorker(card);
    }


}
