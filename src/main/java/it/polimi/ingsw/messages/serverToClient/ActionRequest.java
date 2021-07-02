package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.controller.Actions;

import java.util.ArrayList;

/**
 * this class sends the client the choice of executable actions
 */
public class ActionRequest implements ServerMessage{
    private ArrayList<Actions> possibleActions;

    public ActionRequest(ArrayList<Actions> possibleActions) {
        this.possibleActions = possibleActions;
    }

    @Override
    public void handleMessage(ClientView clientView) { clientView.pickAction(possibleActions);}

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
