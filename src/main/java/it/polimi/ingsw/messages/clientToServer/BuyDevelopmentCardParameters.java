package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;

import java.util.HashMap;

public class BuyDevelopmentCardParameters implements ClientMessage {
    private final Color color;
    private final int level;
    private final int devCardSlot;
    private final HashMap<Resource,Integer> warehouseDepotRes;
    private final HashMap<Resource,Integer> cardDepotRes;
    private final HashMap<Resource,Integer> strongboxRes;

    public BuyDevelopmentCardParameters(Color color, int level, int devCardSlot, HashMap<Resource, Integer> warehouseDepotRes,
                                        HashMap<Resource, Integer> cardDepotRes, HashMap<Resource, Integer> strongboxRes) {
        this.color = color;
        this.level = level;
        this.devCardSlot = devCardSlot;
        this.warehouseDepotRes = warehouseDepotRes;
        this.cardDepotRes = cardDepotRes;
        this.strongboxRes = strongboxRes;
    }

    @Override
    public void handleMessage(Controller controller) {
        controller.buyDevelopmentCard(warehouseDepotRes,strongboxRes,cardDepotRes,color,level,devCardSlot); }
}
