package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

public class EndGameMessage implements ServerMessage{
    private String winner;

    public EndGameMessage(String winner) {
        this.winner = winner;
    }

    @Override
    public void handleMessage(ClientView clientView) { }

    @Override
    public void handleMessage(PingReceiver pingManager) { }
}
