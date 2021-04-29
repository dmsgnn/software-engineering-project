package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.model.gameboard.marble.Marbles;

public class MarketMarbles implements ServerMessage{
    private Marbles[][] marbles;
    private Marbles freeMarble;

    public MarketMarbles(Marbles[][] marbles, Marbles freeMarble) {
        this.marbles = marbles;
        this.freeMarble = freeMarble;
    }

    @Override
    public void handleMessage(ClientView clientView) { }
}
