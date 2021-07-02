package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

/**
 * this class informs the client about the start of a player's turn
 */
public class StartTurn implements ServerMessage{
    private final String name;

    public StartTurn(String name) {
        this.name = name;
    }


    @Override
    public void handleMessage(ClientView clientView) {
        clientView.turnNotification(name);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
