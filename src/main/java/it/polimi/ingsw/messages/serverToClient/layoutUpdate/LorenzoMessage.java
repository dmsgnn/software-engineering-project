package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;

public class LorenzoMessage implements ServerMessage {
    private int position;
    private int lorenzoPosition;
    private String[][] firstGrid;
    private String[][] newCardGrid;

    public LorenzoMessage(int position, int lorenzoPosition, String[][] firstGrid, String[][] secondGrid) {
        this.position = position;
        this.lorenzoPosition = lorenzoPosition;
        this.firstGrid = firstGrid;
        this.newCardGrid = secondGrid;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.lorenzoUpdate(position, lorenzoPosition, firstGrid, newCardGrid);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
