package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

/**
 * this class is used to verify that the client responds to the pong
 */
public class Ping implements ServerMessage{

    @Override
    public void handleMessage(ClientView clientView) {
    }

    @Override
    public void handleMessage(PingReceiver ping) {
        ping.receivePing();
    }
}
