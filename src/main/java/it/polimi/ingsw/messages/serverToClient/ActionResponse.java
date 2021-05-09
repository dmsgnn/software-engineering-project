package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.controller.Actions;

public class ActionResponse implements ServerMessage{
    private Actions action;

    public ActionResponse(Actions action) {
        this.action = action;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.doAction(action);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}

