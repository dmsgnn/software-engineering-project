package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

import java.util.ArrayList;

public class LeaderCardsReply implements ClientMessage{
    private final ArrayList<String> leaderCardsId;
    private final String username;

    public LeaderCardsReply(ArrayList<String> leaderCardsId, String username) {
        this.leaderCardsId = leaderCardsId;
        this.username = username;
    }


    @Override
    public void handleMessage(Controller controller) {
        controller.pickStartingLeaderCards(leaderCardsId,username);
    }

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
