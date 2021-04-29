package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;

import java.util.ArrayList;

public class LeaderCardsReply implements ClientMessage{
    private ArrayList<String> leaderCardsId;

    public LeaderCardsReply(ArrayList<String> leaderCardsId) {
        this.leaderCardsId = leaderCardsId;
    }


    @Override
    public void handleMessage(Controller controller) { }
}
