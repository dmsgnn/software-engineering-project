package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

public class EndGameMessage implements ServerMessage{

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.endGame();
    }

    @Override
    public void handleMessage(PingReceiver pingManager) { }
}
