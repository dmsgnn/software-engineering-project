package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

/**
 * this class sends an update to the client after a market action has been performed
 */
public class MarketActionUpdate implements ServerMessage {

    private final String nickname;
    private final Map<Integer, ArrayList<Resource>> warehouse;
    private final ArrayList<MarbleColors> newMarbles;
    private final MarbleColors newFreeMarble;
    private final int pos;
    private final boolean rowOrCol;

    public MarketActionUpdate(String nickname, Map<Integer, ArrayList<Resource>> warehouse,
                              ArrayList<MarbleColors> newMarbles, MarbleColors newFreeMarble, int pos, boolean rowOrCol) {
        this.nickname = nickname;
        this.warehouse = warehouse;
        this.newMarbles = newMarbles;
        this.newFreeMarble = newFreeMarble;
        this.pos = pos;
        this.rowOrCol = rowOrCol;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.marketActionUpdate(nickname, warehouse,newMarbles, newFreeMarble, pos, rowOrCol);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
