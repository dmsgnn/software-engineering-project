package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

import java.io.Serializable;

/**
 * this interface is used for communication between client and server of serializable objects
 */
public interface ClientMessage extends Serializable {
    void handleMessage(Controller controller);
    void handleMessage(PingManager pingSender);
}
