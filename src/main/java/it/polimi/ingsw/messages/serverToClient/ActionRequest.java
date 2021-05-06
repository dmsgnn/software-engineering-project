package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.controller.Actions;

import java.util.ArrayList;

public class ActionRequest implements ServerMessage{
    private ArrayList<Actions> possibleActions;

    public ActionRequest(ArrayList<Actions> possibleActions) {
        this.possibleActions = possibleActions;
    }

    @Override
    public void handleMessage(ClientView clientView) { clientView.pickAction(possibleActions);}
}
