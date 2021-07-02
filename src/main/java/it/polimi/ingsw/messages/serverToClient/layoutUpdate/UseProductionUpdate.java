package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

/**
 * this class sends an update to the client after a use production has been made
 */
public class UseProductionUpdate implements ServerMessage{

    private final String nickname;
    private final Map<Integer, ArrayList<Resource>> warehouse;
    private final Map<Resource, Integer> strongbox;

    public UseProductionUpdate(String nickname, Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox) {
        this.nickname = nickname;
        this.warehouse = warehouse;
        this.strongbox = strongbox;
    }

    @Override
    public void handleMessage(ClientView clientView) {
       clientView.useProductionUpdate(nickname, warehouse, strongbox);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
