package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

public class PlayerNumberRequest implements ServerMessage{
    private final int max;

    public PlayerNumberRequest() {
        max=4;
    }


    @Override
    public void handleMessage(ClientView clientView) { clientView.numOfPlayers(max); }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
