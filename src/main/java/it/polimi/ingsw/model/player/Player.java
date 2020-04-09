package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.player.gods.*;

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
    private Worker worker1, worker2;
    private Card card;

    /**
     * constructor
     * @param nickname player's univocal name
     */
    public Player(String nickname, int clientID) {
        this.nickname = nickname;
        color = null;
        this.clientID = clientID;
    }

    public void setColor(PlayerColors color) {
        if(this.color==null) {
            this.color = color;
        }
    }

    /**
     * get Player's nickname
     * @return univocal Nickname
     */
    public String getNickname(){
        return this.nickname;
    }

    public int getClientID() {
        return clientID;
    }

    /**
     * get Player's color
     * @return univocal color
     */
    public PlayerColors getColor() {
        return this.color;
    }

    /**
     * create two instances of worker related to the card received
     * @param card god card
     */
    public void addWorker(Card card){
        switch(card){
            case APOLLO:
                worker1 = new Apollo(color);
                worker2 = new Apollo(color);
                break;
            case ARTEMIS:
                worker1 = new Artemis(color);
                worker2 = new Artemis(color);
                break;
            case ATHENA:
                worker1 = new Athena(color);
                worker2 = new Athena(color);
                break;
            case ATLAS:
                worker1 = new Atlas(color);
                worker2 = new Atlas(color);
                break;
            case DEMETER:
                worker1 = new Demeter(color);
                worker2 = new Demeter(color);
                break;
            case HEPHAESTUS:
                worker1 = new Hephaestus(color);
                worker2 = new Hephaestus(color);
                break;
            case MINOTAUR:
                worker1 = new Minotaur(color);
                worker2 = new Minotaur(color);
                break;
            case PAN:
                worker1 = new Pan(color);
                worker2 = new Pan(color);
                break;
            case PROMETHEUS:
                worker1 = new Prometheus(color);
                worker2 = new Prometheus(color);
                break;
        }
    }

    /**
     * set player's card attribute
     * @param godCard god card
     */
    public void setCard(Card godCard){
        this.card = godCard;
        addWorker(card);
    }


}
