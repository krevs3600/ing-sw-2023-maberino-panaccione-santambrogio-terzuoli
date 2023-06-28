package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.observer_observable.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;


/**
 * <h1>Class RMIorSocketController</h1>
 *this class represents the controller of the fxml scene where
 * the player chooses type of connection
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/23/2023
 */
public class RMIorSocketController extends Observable<EventMessage> {
    public String resource = "RMIorSocket_scene.fxml";
    @FXML
    public RadioButton RMI_button;
    @FXML
    public RadioButton TCP_button;
    @FXML
    public Button okButton;

    private GUI gui;

    private boolean RMI = false;

    private boolean TCP = false;


    /**
     * Getter method
     * @return the {@link RMIorSocketController#RMI} present in the {@link RMIorSocketController}
     * which is  a boolean that is "true" if the player has chosen the RMI connection type, false otherwise
     *
     */

    public boolean isRMI() {
        return RMI;
    }

    /**
     * Getter method
     * @return the {@link RMIorSocketController#TCP} present in the {@link RMIorSocketController}
     * which is  a boolean that is "true" if the player has chosen the socket connection type, false otherwise
     *
     */

    public boolean isTCP(){
        return TCP;
    }

    /**
     *
     This method is used to create the connection  as soon as the players confirms his choice by pressing the {@link RMIorSocketController RMIbutton}
     or the {@link RMIorSocketController TCPbutton} in the
     {@code RMIorSocket_scene}
     * @param event the click on the {@link RMIorSocketController RMI_button} or on the {@link RMIorSocketController TCP_button}
     */

    public void createConnection(MouseEvent event){
        if (RMI_button.isSelected()) {
            RMI = true;
            TCP = false;
            gui.createConnection();
        } else if (TCP_button.isSelected()){
            RMI = false;
            TCP = true;
            gui.createConnection();
        } else {
            gui.showPopup("Please select an option");
        }
    }

    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */



    public void setGui(GUI gui) {
        this.gui=gui;
    }
    public void goBack(MouseEvent event){
        gui.goBackToPreviousScene(resource);
    }
}
