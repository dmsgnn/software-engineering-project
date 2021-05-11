package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

public class ReConnectionUpdate implements ServerMessage {

    private final Map<String, ArrayList<String>> devCardSlots;
    private final Map<String,Integer> faithPositions;
    private final Map<String,ArrayList<String>> leaderCardsPlayed;
    private final ArrayList<String> LeaderCards;
    private final Map<String,Map<Resource, Integer>> strongbox;
    private final Map<String,Map<Integer,ArrayList<Resource>>> warehouse;


    public ReConnectionUpdate(Map<String, ArrayList<String>> devCardSlots, Map<String,
            Integer> faithPositions, Map<String, ArrayList<String>> leaderCardsPlayed,
                              ArrayList<String> leaderCards, Map<String, Map<Resource,
            Integer>> strongbox, Map<String, Map<Integer, ArrayList<Resource>>> warehouse) {
        this.devCardSlots = devCardSlots;
        this.faithPositions = faithPositions;
        this.leaderCardsPlayed = leaderCardsPlayed;
        LeaderCards = leaderCards;
        this.strongbox = strongbox;
        this.warehouse = warehouse;
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}