package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class ResourcesReply implements ClientMessage{
    private ArrayList<ArrayList<Resource>> resources;

    public ResourcesReply(ArrayList<ArrayList<Resource>> resources) {
        this.resources = resources;
    }

    @Override
    public void handleMessage(Controller controller) { }
}
