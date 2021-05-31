package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

public class Reconnection implements ServerMessage{

    private String username;

    public Reconnection(String username) {
        this.username = username;
    }


    @Override
    public void handleMessage(ClientView clientView) {

    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
