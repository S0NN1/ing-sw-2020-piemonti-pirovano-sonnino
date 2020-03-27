package it.polimi.ingsw.observer;

public interface CardObserver<T> {
    public void update(String cmd, Object arg);
}
