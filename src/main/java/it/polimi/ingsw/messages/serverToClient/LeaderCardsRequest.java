package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

import java.util.ArrayList;

public class LeaderCardsRequest implements ServerMessage{
    private ArrayList<String> leaderCardsId;

    public LeaderCardsRequest(ArrayList<String> leaderCardsId) {
        this.leaderCardsId = leaderCardsId;
    }

    @Override
    public void handleMessage(ClientView clientView) { clientView.selectStartingCards(leaderCardsId); }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
