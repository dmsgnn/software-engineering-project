package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

/**
 * this class tells the client that a player has logged out
 */
public class Disconnection implements ServerMessage {
    private String username;

    public Disconnection(String username) {
        this.username = username;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.playerDisconnected(username);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
