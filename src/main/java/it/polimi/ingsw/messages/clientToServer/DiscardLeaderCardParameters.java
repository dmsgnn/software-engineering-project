package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;

public class DiscardLeaderCardParameters implements ClientMessage {
    private String id;

    public DiscardLeaderCardParameters(String id) {
        this.id = id;
    }

    @Override
    public void handleMessage(Controller controller) { }
}
