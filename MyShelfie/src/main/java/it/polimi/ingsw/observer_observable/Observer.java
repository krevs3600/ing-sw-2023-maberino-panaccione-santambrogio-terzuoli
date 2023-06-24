package it.polimi.ingsw.observer_observable;

public interface Observer<EventMessage>{

    void update(Observable<EventMessage> o, EventMessage eventMessage);
}
