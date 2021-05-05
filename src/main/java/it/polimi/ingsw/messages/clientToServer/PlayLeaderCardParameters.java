package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.model.Resource;

import java.util.HashMap;

public class PlayLeaderCardParameters implements ClientMessage {
    private String id;
    private HashMap<Resource,Integer> warehouseDepotRes;
    private HashMap<Resource,Integer> cardDepotRes;
    private HashMap<Resource,Integer> strongboxRes;

    public PlayLeaderCardParameters(String id, HashMap<Resource, Integer> warehouseDepotRes, HashMap<Resource, Integer> cardDepotRes, HashMap<Resource, Integer> strongboxRes) {
        this.id = id;
        this.warehouseDepotRes = warehouseDepotRes;
        this.cardDepotRes = cardDepotRes;
        this.strongboxRes = strongboxRes;
    }


    @Override
    public void handleMessage(Controller controller) {
        controller.playLeaderCard(id,warehouseDepotRes,cardDepotRes,strongboxRes);
    }
}
