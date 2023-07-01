package it.polimi.ingsw.observer_observable;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Class Observable</h1>
 * The abstract class Observable is related to the Observer-Observable design pattern. Specifically it is an implemented
 * by {@link EventMessage}. When one happens, the Observer gets notified and can react correctly
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/10/2023
 */
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



        for (Observer<EventMessage> observer : getObservers()) {
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

    public synchronized List<Observer<EventMessage>> getObservers () {
        return observers;
    }
    public synchronized boolean hasChanged() {
        return changed;
    }


    public int countObservers() {
        return observers.size();
    }

}

