package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.PingManager;

import java.util.ArrayList;

/**
 * this class communicates the player's choice to carry out a market action
 */
public class RowColumnSelection implements ClientMessage {
    private final int index;
    private final boolean rowOrCol;
    private final ArrayList<Resource> exchangeBuffResources;
    private final String username;


    public RowColumnSelection(int index, boolean rowOrCol, ArrayList<Resource> exchangeBuffResources, String username) {
        this.index = index;
        this.rowOrCol = rowOrCol;
        this.exchangeBuffResources = exchangeBuffResources;
        this.username = username;
    }

    @Override
    public void handleMessage(Controller controller) {
        controller.marketAction(index,rowOrCol, exchangeBuffResources,username);
    }

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
