package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;

public class LorenzoMessage implements ServerMessage {
    private int position;
    private int lorenzoPosition;
    private String[][] firstGrid;
    private String[][] secondGrid;

    public LorenzoMessage(int position, int lorenzoPosition, String[][] firstGrid, String[][] secondGrid) {
        this.position = position;
        this.lorenzoPosition = lorenzoPosition;
        this.firstGrid = firstGrid;
        this.secondGrid = secondGrid;
    }

    @Override
    public void handleMessage(ClientView clientView) {


    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
