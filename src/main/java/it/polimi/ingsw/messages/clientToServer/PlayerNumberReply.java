package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;

public class PlayerNumberReply implements ClientMessage{
    private int playerNum;

    public PlayerNumberReply(int playerNum) {
        this.playerNum = playerNum;
    }

    @Override
    public void handleMessage(Controller controller) { }
}
