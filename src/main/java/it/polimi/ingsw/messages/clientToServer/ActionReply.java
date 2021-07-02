package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

/**
 * this class calls a controller method,
 * thus communicating the action chosen by the client to it
 */
public class ActionReply implements ClientMessage{
    private final Actions action;

    public ActionReply(Actions action) {
        this.action = action;
    }

    @Override
    public void handleMessage(Controller controller) { controller.selectAction(action);}

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
