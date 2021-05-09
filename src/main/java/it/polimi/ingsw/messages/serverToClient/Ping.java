package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

public class Ping implements ServerMessage{

    @Override
    public void handleMessage(ClientView clientView) {
    }

    @Override
    public void handleMessage(PingReceiver ping) {
        ping.receivePing();
    }
}
