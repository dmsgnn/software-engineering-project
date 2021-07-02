package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

/**
 * this class gives the server the parameters to be able to play a leader card
 */
public class PlayLeaderCardParameters implements ClientMessage {
    private final String id;
    private final String username;

    public PlayLeaderCardParameters(String id, String username) {
        this.id = id;
        this.username = username;
    }


    @Override
    public void handleMessage(Controller controller) {
        controller.playLeaderCard(id,username);
    }

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
