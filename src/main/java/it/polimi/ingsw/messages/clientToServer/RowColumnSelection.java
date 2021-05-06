package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class RowColumnSelection implements ClientMessage {
    private int index;
    private boolean rowOrCol;
    private ArrayList<Resource> exchangeBuffResources;

    public RowColumnSelection(int index, boolean rowOrCol, ArrayList<Resource> exchangeBuffResources) {
        this.index = index;
        this.rowOrCol = rowOrCol;
        this.exchangeBuffResources = exchangeBuffResources;
    }

    @Override
    public void handleMessage(Controller controller) { }
}
