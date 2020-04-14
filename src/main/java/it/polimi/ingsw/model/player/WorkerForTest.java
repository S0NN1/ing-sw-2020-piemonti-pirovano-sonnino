package it.polimi.ingsw.model.player;

/**
 * @author Alice Piemonti
 */
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
