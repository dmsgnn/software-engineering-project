package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

/**
 * this class tells the client that the game is about to end
 */
public class EndGameMessage implements ServerMessage{

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.endGame();
    }

    @Override
    public void handleMessage(PingReceiver pingManager) { }
}
