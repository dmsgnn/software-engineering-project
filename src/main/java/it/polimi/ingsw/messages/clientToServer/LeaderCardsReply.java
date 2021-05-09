package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

import java.util.ArrayList;

public class LeaderCardsReply implements ClientMessage{
    private final ArrayList<String> leaderCardsId;

    public LeaderCardsReply(ArrayList<String> leaderCardsId) {
        this.leaderCardsId = leaderCardsId;
    }


    @Override
    public void handleMessage(Controller controller) {
        controller.pickStartingLeaderCards(leaderCardsId);
    }

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
