package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

/**
 * this class tells the server the number of players in a game to create
 */
public class PlayerNumberReply implements ClientMessage{
    private int playerNum;

    public PlayerNumberReply(int playerNum) {
        this.playerNum = playerNum;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    @Override
    public void handleMessage(Controller controller) { controller.setPlayersNumber(playerNum);}

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
