package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

public class ResourcesRequest implements ServerMessage{
    private int resourceNumber;

    public ResourcesRequest(int resourceNumber) {
        this.resourceNumber = resourceNumber;
    }

    @Override
    public void handleMessage(ClientView clientView) { clientView.startingResources(resourceNumber);}

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
