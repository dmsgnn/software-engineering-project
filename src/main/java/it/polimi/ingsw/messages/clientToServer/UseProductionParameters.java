package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.PingManager;

import java.util.ArrayList;
import java.util.HashMap;

public class UseProductionParameters implements ClientMessage{

    private final HashMap<Resource,Integer> warehouseDepotRes;
    private final HashMap<Resource,Integer> cardDepotRes;
    private final HashMap<Resource,Integer> strongboxRes;
    private final ArrayList<Resource> boardResGain;
    private final ArrayList<Integer> indexLeaderSlot;
    private final ArrayList<Resource> leaderCardGain;
    private final ArrayList<Integer> indexDevSlot;

    public UseProductionParameters(HashMap<Resource, Integer> warehouseDepotRes, HashMap<Resource, Integer> cardDepotRes, HashMap<Resource,
            Integer> strongboxRes, ArrayList<Resource> boardResGain, ArrayList<Resource> leaderCardGain, ArrayList<Integer> indexDevSlot, ArrayList<Integer> indexLeaderSlot) {
        this.warehouseDepotRes = warehouseDepotRes;
        this.cardDepotRes = cardDepotRes;
        this.strongboxRes = strongboxRes;
        this.boardResGain = boardResGain;
        this.leaderCardGain = leaderCardGain;
        this.indexDevSlot = indexDevSlot;
        this.indexLeaderSlot = indexLeaderSlot;
    }

    @Override
    public void handleMessage(Controller controller) {

    }

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
