package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

/**
 * this class sends the request for a manage resources to the client
 */
public class ResourcesManageRequest implements  ServerMessage{
    private ArrayList<Resource> resources;

    public ResourcesManageRequest(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public void handleMessage(ClientView clientView) { clientView.manageResources(resources);}

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
