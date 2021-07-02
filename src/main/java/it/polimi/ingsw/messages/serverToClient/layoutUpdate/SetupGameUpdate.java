package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

/**
 * this class sends an update to the client
 * when the initial choice of resources and leader cards is completed
 */
public class SetupGameUpdate implements ServerMessage {
    private final Map<String,ArrayList<String>> leaderCards;
    private final Map<String,Map<Integer, ArrayList<Resource>>> resources;
    private final Map<String,Integer> newFaithTrackPosition;

    public SetupGameUpdate(Map<String,ArrayList<String>> leaderCards, Map<String,Map<Integer, ArrayList<Resource>>> resources, Map<String,Integer> newFaithTrackPosition) {
        this.leaderCards = leaderCards;
        this.resources = resources;
        this.newFaithTrackPosition = newFaithTrackPosition;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.setupGameUpdate(leaderCards, resources, newFaithTrackPosition);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }


}
