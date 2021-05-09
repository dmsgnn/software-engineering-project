package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

public class PlayerNumberRequest implements ServerMessage{
    private int[] listOfInt;

    public PlayerNumberRequest() {
        listOfInt = new int[]{1,2,3,4};
    }


    @Override
    public void handleMessage(ClientView clientView) { clientView.numOfPlayers(4); }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
