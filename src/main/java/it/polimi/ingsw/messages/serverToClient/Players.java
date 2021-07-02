package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

import java.util.ArrayList;

/**
 * this class sends the names of the players in the game to the client
 */
public class Players implements ServerMessage{
    private final ArrayList<String> players;

    public Players(ArrayList<String> players) {
        this.players = players;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.addPlayersToGameboard(players);

    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
