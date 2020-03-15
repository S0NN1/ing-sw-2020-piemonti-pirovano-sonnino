package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Card;

public class Player {
    private String nickname;
    private PlayerColors color;
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


}
