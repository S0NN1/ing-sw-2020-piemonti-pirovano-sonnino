package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;

public class Athena extends Worker {
    public Athena(PlayerColors color) {
        super(color);
    }

    @Override
    public void setPhases() {
        setNormalPhases();
    }
}
