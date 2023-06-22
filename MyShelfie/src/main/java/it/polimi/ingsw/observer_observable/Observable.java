package it.polimi.ingsw.observer_observable;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<EventMessage> {


    private boolean changed = false;
    private final List<Observer> observers;



    public Observable() {
        observers = new ArrayList<>();
    }
    public synchronized void addObserver(Observer obs) {
        if (obs == null)
            throw new NullPointerException();



        observers.add(obs);
    }

    public synchronized void deleteObserver(Observer obs) {
        observers.remove(obs);
    }
    public void notifyObservers() {
        notifyObservers(null);
    }



    public void notifyObservers(EventMessage message) {

        synchronized (this) {
            /* We don't want the Observer doing callbacks into
             * arbitrary code while holding its own Monitor.
             * The code where we extract each Observable from
             * the Vector and store the state of the Observer
             * needs synchronization, but notifying observers
             * does not (should not). The worst result of any
             * potential race-condition here is that:
             * 1) a newly-added Observer will miss a
             * notification in progress
             * 2) a recently unregistered Observer will be
             * wrongly notified when it doesn't care
             */
            if (!changed)
                return;
            clearChanged();
        }


        for (Observer observer : observers) {

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

