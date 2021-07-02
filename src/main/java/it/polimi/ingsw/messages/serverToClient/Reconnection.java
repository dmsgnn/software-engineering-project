package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

/**
 * this class informs the client about a player's reconnection
 */
public class Reconnection implements ServerMessage{

    private final String username;

    public Reconnection(String username) {
        this.username = username;
    }


    @Override
    public void handleMessage(ClientView clientView) {
        clientView.playerReconnected(username);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
