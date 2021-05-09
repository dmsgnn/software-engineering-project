package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

import java.io.Serializable;

public interface ClientMessage extends Serializable {
    void handleMessage(Controller controller);
    void handleMessage(PingManager pingSender);
}
