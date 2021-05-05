package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;

public class RowColumnSelection implements ClientMessage {
    private int index;
    private boolean isRow;

    public RowColumnSelection(int index, boolean isRow) {
        this.index = index;
        this.isRow = isRow;
    }

    @Override
    public void handleMessage(Controller controller) { }
}
