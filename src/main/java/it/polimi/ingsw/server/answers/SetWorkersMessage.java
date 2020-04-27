package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.server.answers.Answer;

public class SetWorkersMessage implements Answer {

    private Couple worker1;
    private Couple worker2;
    private String color;

    public SetWorkersMessage(String playerColor, int workerRow1, int workerCol1, int workerRow2, int workerCol2 ){
        worker1 = new Couple(workerRow1, workerCol1);
        worker2 =new Couple(workerRow2,workerCol2);
        color = playerColor;
    }

    @Override
    public String getMessage() {
        return color;
    }

    public Couple getWorker1(){
        return worker1;
    }
    public Couple getWorker2(){
        return worker2;
    }
}
