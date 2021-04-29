package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;

import java.util.ArrayList;

public class LeaderCardsRequest implements ServerMessage{
    private ArrayList<String> leaderCardsId;

    public LeaderCardsRequest(ArrayList<String> leaderCardsId) {
        this.leaderCardsId = leaderCardsId;
    }

    @Override
    public void handleMessage(ClientView clientView) { }
}
