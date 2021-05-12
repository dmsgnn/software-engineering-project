package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

public class EndTurn implements ClientMessage{
    @Override
    public void handleMessage(Controller controller) {

    }

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
