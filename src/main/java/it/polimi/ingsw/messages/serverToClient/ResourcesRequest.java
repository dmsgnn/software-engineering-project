package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;

public class ResourcesRequest implements ServerMessage{
    private int resourceNumber;

    public ResourcesRequest(int resourceNumber) {
        this.resourceNumber = resourceNumber;
    }

    @Override
    public void handleMessage(ClientView clientView) { clientView.startingResources(resourceNumber);}
}
