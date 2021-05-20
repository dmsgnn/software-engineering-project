package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

public class StartingResourceDone implements ServerMessage{
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.startingResDone();
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
