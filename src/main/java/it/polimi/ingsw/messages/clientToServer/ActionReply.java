package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.controller.Controller;

public class ActionReply implements ClientMessage{
    private final Actions action;

    public ActionReply(Actions action) {
        this.action = action;
    }

    @Override
    public void handleMessage(Controller controller) { controller.selectAction(action);}
}