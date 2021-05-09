package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.PingManager;

import java.util.ArrayList;
import java.util.Map;

public class ResourcesReply implements ClientMessage{
    private final Map<Integer,ArrayList<Resource>> resources;

    public ResourcesReply(Map<Integer,ArrayList<Resource>> resources) {
        this.resources = resources;
    }

    @Override
    public void handleMessage(Controller controller) { controller.pickStartingResources(resources);}

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
