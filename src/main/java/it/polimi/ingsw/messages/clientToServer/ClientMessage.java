package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;

import java.io.Serializable;

public interface ClientMessage extends Serializable {
    void handleMessage(Controller controller);
   // void handleMessage(PingManager pingManager);
}
