package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.PingManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResourcesManageReply implements ClientMessage{
    private final Map<Integer,ArrayList<Resource>> resources;
    private final HashMap<Resource,Integer> discardRes;
    private final HashMap<Resource,Integer> newRes;

    public ResourcesManageReply(Map<Integer, ArrayList<Resource>> resources, HashMap<Resource, Integer> discardRes, HashMap<Resource, Integer> newRes) {
        this.resources = resources;
        this.discardRes = discardRes;
        this.newRes = newRes;
    }

    @Override
    public void handleMessage(Controller controller) { controller.manageResources(resources,discardRes,newRes);}

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
