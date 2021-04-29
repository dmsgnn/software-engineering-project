package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;

public class PlayerNumberRequest implements ServerMessage{
    private int[] listOfInt;

    public PlayerNumberRequest() {
        listOfInt = new int[]{1,2,3,4};
    }


    @Override
    public void handleMessage(ClientView clientView) { }
}
