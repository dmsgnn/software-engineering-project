package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

public class StartTurn implements ServerMessage{
    private final String name;

    public StartTurn(String name) {
        this.name = name;
    }


    @Override
    public void handleMessage(ClientView clientView) {


    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
