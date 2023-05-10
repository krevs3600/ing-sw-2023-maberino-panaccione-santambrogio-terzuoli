package it.polimi.ingsw.network;

public interface Server {

    void register(Client client);

    void update(Client client, EventMessage eventMessage);
}
