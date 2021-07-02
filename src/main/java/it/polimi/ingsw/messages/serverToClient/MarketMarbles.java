package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.client.PingReceiver;

/**
 * this class sends the market to the client
 */
public class MarketMarbles implements ServerMessage{
    private MarbleColors[][] marbles;
    private MarbleColors freeMarble;

    public MarketMarbles(MarbleColors[][] marbles, MarbleColors freeMarble) {
        this.marbles = marbles;
        this.freeMarble = freeMarble;
    }

    @Override
    public void handleMessage(ClientView clientView) { clientView.marketSetup(marbles, freeMarble); }

    @Override
    public void handleMessage(PingReceiver pingManager) { }
}
