package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.MarbleColors;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

public class MarketActionUpdate implements ServerMessage {

    private String nickname;
    private int playerFaith;
    private Map<Integer, ArrayList<Resource>> warehouse;
    private Map<Resource, Integer> strongbox;
    private ArrayList<MarbleColors> newMarbles;
    private MarbleColors newFreeMarble;
    private int pos;
    private boolean rowOrCol;

    public MarketActionUpdate(String nickname, int playerFaith, Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox,
                              ArrayList<MarbleColors> newMarbles, MarbleColors newFreeMarble, int pos, boolean rowOrCol) {
        this.nickname = nickname;
        this.playerFaith = playerFaith;
        this.warehouse = warehouse;
        this.strongbox = strongbox;
        this.newMarbles = newMarbles;
        this.newFreeMarble = newFreeMarble;
        this.pos = pos;
        this.rowOrCol = rowOrCol;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.marketActionUpdate(nickname, playerFaith, warehouse, strongbox, newMarbles, newFreeMarble, pos, rowOrCol);
    }
}
