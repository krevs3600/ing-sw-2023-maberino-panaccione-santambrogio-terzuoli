package it.polimi.ingsw.observer_observable;

public interface Observer<Observable, EventMessage>{

    void update(Observable o, EventMessage eventMessage);
}
