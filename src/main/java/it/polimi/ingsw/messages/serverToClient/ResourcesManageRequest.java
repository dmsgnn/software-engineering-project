package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class ResourcesManageRequest implements  ServerMessage{
    private ArrayList<Resource> newResources;
    private ArrayList<ArrayList<Resource>> depotResources;

    public ResourcesManageRequest(ArrayList<Resource> resources, ArrayList<ArrayList<Resource>> depotResources) {
        this.newResources = resources;
        this.depotResources = depotResources;
    }

    @Override
    public void handleMessage(ClientView clientView) { }
}
