package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

/**
 * this class tells the client that the leader card setup has completed
 */
public class LeaderCardSetupDone implements ServerMessage{

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.leaderCardsDone();
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
