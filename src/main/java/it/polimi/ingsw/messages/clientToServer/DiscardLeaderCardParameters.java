package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

public class DiscardLeaderCardParameters implements ClientMessage {
    private final String id;
    private final String username;

    public DiscardLeaderCardParameters(String id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public void handleMessage(Controller controller) { controller.discardLeaderCard(id,username);}

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
