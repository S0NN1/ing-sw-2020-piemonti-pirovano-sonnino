package it.polimi.ingsw.model.player;

public class WorkerForTest extends Worker {
    /**
     * Constructor
     *
     * @param color player color
     */
    public WorkerForTest(PlayerColors color) {
        super(color);
    }

    @Override
    public void setPhases() {
        setNormalPhases();
    }
}
