package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.model.gameboard.Color;

import java.util.Map;

public class AllCardsMessage implements ServerMessage{
    private Map<String, Color> colorMap;
    private Map<String,Integer> levelMap;

    public AllCardsMessage(Map<String, Color> colorMap, Map<String, Integer> levelMap) {
        this.colorMap = colorMap;
        this.levelMap = levelMap;
    }

    @Override
    public void handleMessage(ClientView clientView) {
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
