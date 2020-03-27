package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

public class CardObservable<T> {
    private List<CardObserver<T>> observers = new ArrayList<>();

    public void addObservers(CardObserver<T> observer) {
        observers.add(observer);
    }

    public void notify(String cmd, Object arg) {
        for (CardObserver<T> observer : observers) {
            observer.update(cmd, arg);
        }
    }
}
