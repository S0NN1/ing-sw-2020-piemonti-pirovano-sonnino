package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Card;

/**
 * @author Alice Piemonti
 */
public class Player {
    private final String nickname;
    private final PlayerColors color;
    private boolean isActive;
    private boolean isFirstPlayer;
    private boolean isDead;
    private boolean canMove, canLevelUp, canBuild;
    private Worker worker1, worker2;
    private Card card;

    public Player(String nickname, PlayerColors color) {
        this.nickname = nickname;
        this.color = color;
    }

    public String getNickname(){
        return this.nickname;
    }

    public PlayerColors getColor() {
        return this.color;
    }

    public void addWorker(Card card){
        APOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, HEPHAESTUS, MINOTAUR, PAN, PROMETHEUS;
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

    public void setCard(Card godCard){
        this.card = godCard;
        addWorker(card);
    }


}
