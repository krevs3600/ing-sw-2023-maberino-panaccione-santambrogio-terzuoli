module PSP47{
    requires javafx.fxml;
    requires javafx.controls;
    requires json.simple;
    requires java.rmi;


    opens it.polimi.ingsw.client.view.FXML;
    exports it.polimi.ingsw.network;
}
