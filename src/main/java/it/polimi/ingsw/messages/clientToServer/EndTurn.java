package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

/**
 * this class communicates to the controller a client's choice to finish the turn
 */
public class EndTurn implements ClientMessage{
    @Override
    public void handleMessage(Controller controller) {
        controller.endTurn();

    }

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
