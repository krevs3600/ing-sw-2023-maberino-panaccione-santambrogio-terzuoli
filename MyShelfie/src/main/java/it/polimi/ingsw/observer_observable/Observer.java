package it.polimi.ingsw.observer_observable;

/**
 * <h1>Interface Observer</h1>
 * The interface Observer is related to the Observer-Observable design pattern. Specifically it is an Observer of
 * of {@link EventMessage}, so that when it gets a notification that one happened, it can react correctly
 *
 * @author Francesco Maberino, Francesco Santambrogio
 * @version 1.0
 * @since 5/10/2023
 */
public interface Observer<EventMessage>{

    void update(Observable<EventMessage> o, EventMessage eventMessage);
}
