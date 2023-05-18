module PSP47{
    requires javafx.fxml;
    requires javafx.controls;
    requires json.simple;
    requires java.rmi;


    opens it.polimi.ingsw.client.view.FXML;
    exports it.polimi.ingsw.network;
    exports it.polimi.ingsw;
    exports it.polimi.ingsw.network.MessagesToServer.requestMessage;
    exports it.polimi.ingsw.model.ModelView;
    exports it.polimi.ingsw.network.eventMessages;
}
