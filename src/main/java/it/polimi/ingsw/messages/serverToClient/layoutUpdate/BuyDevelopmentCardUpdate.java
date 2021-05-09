package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

public class BuyDevelopmentCardUpdate implements ServerMessage {

    private String nickname;
    private String id;
    private int slot;

    private String gridId;
    private int row;
    private int column;

    private Map<Integer, ArrayList<Resource>> warehouse;
    private Map<Resource, Integer> strongbox;

    public BuyDevelopmentCardUpdate(String nickname, String id, int slot, String gridId, int row, int column,
                                    Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox) {
        this.nickname = nickname;
        this.id = id;
        this.slot = slot;
        this.gridId = gridId;
        this.row = row;
        this.column = column;

        this.warehouse = warehouse;
        this.strongbox = strongbox;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.buyCardUpdate(nickname, id, slot, gridId, row, column, warehouse, strongbox);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
