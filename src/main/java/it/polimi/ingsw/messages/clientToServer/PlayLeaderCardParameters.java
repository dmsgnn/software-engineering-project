package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.PingManager;

import java.util.HashMap;

public class PlayLeaderCardParameters implements ClientMessage {
    private String id;

    public PlayLeaderCardParameters(String id) {
        this.id = id;
    }


    @Override
    public void handleMessage(Controller controller) {
        controller.playLeaderCard(id);
    }

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
