package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;

public class RowColumnSelection implements ClientMessage {
    private int index;

    public RowColumnSelection(int index) {
        this.index = index;
    }

    @Override
    public void handleMessage(Controller controller) { }
}
