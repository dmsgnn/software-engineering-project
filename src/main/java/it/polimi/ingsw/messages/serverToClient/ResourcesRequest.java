package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

/**
 * this class sends the request for a starting manage resources to the client
 */
public class ResourcesRequest implements ServerMessage{
    private final int resourceNumber;

    public ResourcesRequest(int resourceNumber) {
        this.resourceNumber = resourceNumber;
    }

    @Override
    public void handleMessage(ClientView clientView) { clientView.startingResources(resourceNumber);}

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
