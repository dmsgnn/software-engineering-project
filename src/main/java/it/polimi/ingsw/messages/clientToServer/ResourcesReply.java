package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.PingManager;

import java.util.ArrayList;
import java.util.Map;

public class ResourcesReply implements ClientMessage{
    private final Map<Integer,ArrayList<Resource>> resources;
    private final String username;

    public ResourcesReply(Map<Integer, ArrayList<Resource>> resources, String username) {
        this.resources = resources;
        this.username = username;
    }

    @Override
    public void handleMessage(Controller controller) {
        controller.pickStartingResources(resources,username);
    }

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
