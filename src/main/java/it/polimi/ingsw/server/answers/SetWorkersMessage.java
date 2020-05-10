package it.polimi.ingsw.server.answers;

import it.polimi.ingsw.constants.Couple;

public class SetWorkersMessage implements Answer {

    private final Couple worker1;
    private final Couple worker2;
    private final String color;

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
