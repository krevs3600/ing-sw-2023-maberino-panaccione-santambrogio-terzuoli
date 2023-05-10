package it.polimi.ingsw.observer_observable;
import it.polimi.ingsw.observer_observable.Observable;
import it.polimi.ingsw.network.EventMessage;

public interface Observer<Observable, EventMessage>{

    void update(Observable o, EventMessage eventMessage);
}
