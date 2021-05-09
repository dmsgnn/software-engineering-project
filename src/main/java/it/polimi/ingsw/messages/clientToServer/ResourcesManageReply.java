package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.PingManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResourcesManageReply implements ClientMessage{
    private final Map<Integer,ArrayList<Resource>> resources;
    private final Map<Resource,Integer> discardRes;

    public ResourcesManageReply(Map<Integer, ArrayList<Resource>> resources, Map<Resource, Integer> discardRes) {
        this.resources = resources;
        this.discardRes = discardRes;
    }

    @Override
    public void handleMessage(Controller controller) { controller.manageResources(resources,discardRes);}

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
