package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;

import java.util.HashMap;

public class BuyDevelopmentCardParameters implements ClientMessage {
    private Color color;
    private int level;
    private int devCardSlot;
    private HashMap<Resource,Integer> warehouseDepotRes;
    private HashMap<Resource,Integer> cardDepotRes;
    private HashMap<Resource,Integer> strongboxRes;

    public BuyDevelopmentCardParameters(Color color, int level, int devCardSlot, HashMap<Resource, Integer> warehouseDepotRes, HashMap<Resource, Integer> cardDepotRes, HashMap<Resource, Integer> strongboxRes) {
        this.color = color;
        this.level = level;
        this.devCardSlot = devCardSlot;
        this.warehouseDepotRes = warehouseDepotRes;
        this.cardDepotRes = cardDepotRes;
        this.strongboxRes = strongboxRes;
    }

    @Override
    public void handleMessage(Controller controller) { }
}
