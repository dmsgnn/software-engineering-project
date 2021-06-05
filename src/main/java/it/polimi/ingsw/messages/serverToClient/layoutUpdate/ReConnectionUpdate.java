package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

public class ReConnectionUpdate implements ServerMessage {

    private final String username;
    private final Map<String, ArrayList<String>> devCardSlots;
    private final Map<String,Integer> faithPositions;
    private final Map<String,ArrayList<String>> leaderCardsPlayed;
    private final ArrayList<String> leaderCards;
    private final Map<String,Map<Resource, Integer>> strongbox;
    private final Map<String,Map<Integer,ArrayList<Resource>>> warehouse;
    private final Map<String,Integer> cardsInHand;
    private final Map<String,Boolean> playersConnected;


    public ReConnectionUpdate(String username, Map<String, ArrayList<String>> devCardSlots, Map<String,
            Integer> faithPositions, Map<String, ArrayList<String>> leaderCardsPlayed,
                              ArrayList<String> leaderCards, Map<String, Map<Resource,
            Integer>> strongbox, Map<String, Map<Integer, ArrayList<Resource>>> warehouse, Map<String, Integer> cardsInHand, Map<String, Boolean> playersConnected) {
        this.username = username;
        this.devCardSlots = devCardSlots;
        this.faithPositions = faithPositions;
        this.leaderCardsPlayed = leaderCardsPlayed;
        this.leaderCards = leaderCards;
        this.strongbox = strongbox;
        this.warehouse = warehouse;
        this.cardsInHand = cardsInHand;
        this.playersConnected = playersConnected;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.reconnectionUpdate(username, devCardSlots, faithPositions, leaderCardsPlayed, leaderCards, strongbox, warehouse, cardsInHand, playersConnected);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
