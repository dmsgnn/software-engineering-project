package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

public class UseProductionParameters implements ClientMessage{

    private ArrayList<Integer> developmentCardSlotIndex;
    private ArrayList<Integer> leaderCardProdIndex;
    private ArrayList<Resource> leaderCardProdGain;
    private ArrayList<Resource> boardResources;
    private Map<Resource, Integer> warehouseResources;
    private Map<Resource, Integer> leaderDepotResources;
    private Map<Resource, Integer> strongboxResources;

    public UseProductionParameters(ArrayList<Integer> developmentCardSlotIndex, ArrayList<Integer> leaderCardProdIndex, ArrayList<Resource> leaderCardProdGain, ArrayList<Resource> boardResources,
                                   Map<Resource, Integer> warehouseResources, Map<Resource, Integer> leaderDepotResources, Map<Resource, Integer> strongboxResources) {
        this.developmentCardSlotIndex = developmentCardSlotIndex;
        this.leaderCardProdIndex = leaderCardProdIndex;
        this.leaderCardProdGain = leaderCardProdGain;
        this.boardResources = boardResources;
        this.warehouseResources = warehouseResources;
        this.leaderDepotResources = leaderDepotResources;
        this.strongboxResources = strongboxResources;
    }

    @Override
    public void handleMessage(Controller controller) {

    }
}
