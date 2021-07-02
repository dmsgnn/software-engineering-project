package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

/**
 * this class sends an update to the client after a play leader card has been made
 */
public class PlayLeaderCardUpdate implements ServerMessage {

    private final String nickname;
    private final String id;
    private final Map<Integer, ArrayList<Resource>> warehouse;

    public PlayLeaderCardUpdate(String nickname, String id, Map<Integer, ArrayList<Resource>> warehouse) {
        this.nickname = nickname;
        this.id = id;
        this.warehouse = warehouse;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.playLeaderCardUpdate(nickname, id, warehouse);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
