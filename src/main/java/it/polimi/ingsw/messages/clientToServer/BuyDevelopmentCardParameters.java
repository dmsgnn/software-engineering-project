package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.server.PingManager;

import java.util.HashMap;

/**
 * this class sends the parameters of a dev card to be activated to the controller
 */
public class BuyDevelopmentCardParameters implements ClientMessage {
    private final Color color;
    private final int level;
    private final int devCardSlot;
    private final HashMap<Resource,Integer> warehouseDepotRes;
    private final HashMap<Resource,Integer> cardDepotRes;
    private final HashMap<Resource,Integer> strongboxRes;
    private final String username;

    public BuyDevelopmentCardParameters(Color color, int level, int devCardSlot, HashMap<Resource, Integer> warehouseDepotRes,
                                        HashMap<Resource, Integer> cardDepotRes, HashMap<Resource, Integer> strongboxRes, String username) {
        this.color = color;
        this.level = level;
        this.devCardSlot = devCardSlot;
        this.warehouseDepotRes = warehouseDepotRes;
        this.cardDepotRes = cardDepotRes;
        this.strongboxRes = strongboxRes;
        this.username = username;
    }

    @Override
    public void handleMessage(Controller controller) {
        controller.buyDevelopmentCard(warehouseDepotRes,strongboxRes,cardDepotRes,color,level,devCardSlot,username);
    }

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
