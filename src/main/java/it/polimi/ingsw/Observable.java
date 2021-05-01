package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
    List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    public void notify(T event) {
        for (Observer<T> observer : observers) {
            observer.update(event);
        }
    }

}
