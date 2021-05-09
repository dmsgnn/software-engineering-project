package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.server.PingManager;

public class DiscardLeaderCardParameters implements ClientMessage {
    private final String id;

    public DiscardLeaderCardParameters(String id) {
        this.id = id;
    }

    @Override
    public void handleMessage(Controller controller) { controller.discardLeaderCard(id);}

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
