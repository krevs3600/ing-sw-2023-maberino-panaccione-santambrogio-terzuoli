package it.polimi.ingsw.observer_observable;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<EventMessage> {


    private boolean changed = false;
    private final List<Observer<EventMessage>> observers;



    public Observable() {
        observers = new ArrayList<>();
    }
    public synchronized void addObserver(Observer<EventMessage> obs) {
        if (obs == null)
            throw new NullPointerException();

        observers.add(obs);
    }

    public synchronized void deleteObserver(Observer<EventMessage> obs) {
        observers.remove(obs);
    }

    public void notifyObservers(EventMessage message) {

        synchronized (this) {
            if (!changed)
                return;
            clearChanged();
        }


        for (Observer<EventMessage> observer : observers) {
            observer.update(this, message);
        }
    }

    public synchronized void deleteAllObservers() {
        observers.clear();
    }
    public synchronized void setChanged() {
        changed = true;
    }
    protected synchronized void clearChanged() {
        changed = false;
    }
    public synchronized boolean hasChanged() {
        return changed;
    }


    public synchronized int countObservers() {
        return observers.size();
    }

}

