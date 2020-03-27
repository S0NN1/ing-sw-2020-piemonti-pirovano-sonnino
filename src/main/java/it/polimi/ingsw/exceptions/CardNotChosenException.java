package it.polimi.ingsw.exceptions;

public class CardNotChosenException extends  Exception {
    public String getMessage() {
        return "This card has not been chosen by the challenger!";
    }
}
