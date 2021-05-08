package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

public class UseProductionUpdate implements ServerMessage{

    private String nickname;
    private int playerFaith;
    private Map<Integer, ArrayList<Resource>> warehouse;
    private Map<Resource, Integer> strongbox;

    public UseProductionUpdate(String nickname, int playerFaith, Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox) {
        this.nickname = nickname;
        this.playerFaith = playerFaith;
        this.warehouse = warehouse;
        this.strongbox = strongbox;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.useProductionUpdate(nickname, playerFaith, warehouse, strongbox);
    }
}
