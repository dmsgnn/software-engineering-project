package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;

import java.util.ArrayList;
import java.util.Map;

public class BuyDevelopmentCardUpdate implements ServerMessage {

    private final String nickname;
    private final String id;
    private final int slot;

    private final String gridId;
    private final Color color;
    private final int level;

    private final Map<Integer, ArrayList<Resource>> warehouse;
    private final Map<Resource, Integer> strongbox;

    public BuyDevelopmentCardUpdate(String nickname, String id, int slot, String gridId, Color color, int level,
                                    Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox) {
        this.nickname = nickname;
        this.id = id;
        this.slot = slot;
        this.gridId = gridId;
        this.color = color;
        this.level = level;

        this.warehouse = warehouse;
        this.strongbox = strongbox;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.buyCardUpdate(nickname, id, slot, gridId, color, level, warehouse, strongbox);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
