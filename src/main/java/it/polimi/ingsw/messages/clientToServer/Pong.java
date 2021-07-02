package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

/**
 * this class is used to verify that the client responds to the ping
 */
public class Pong implements ClientMessage{

    @Override
    public void handleMessage(Controller controller) {
    }

    @Override
    public void handleMessage(PingManager pingSender){
        pingSender.setPing(true);
    }
}
