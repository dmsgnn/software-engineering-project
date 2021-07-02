package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;

/**
 * this class sends an update to the client after lorenzo has drawn a token
 */
public class LorenzoMessage implements ServerMessage {
    private final int lorenzoPosition;
    private final String[][] newCardGrid;
    private final String message;

    public LorenzoMessage(String message, int lorenzoPosition, String[][] newCardGrid) {
        this.lorenzoPosition = lorenzoPosition;
        this.newCardGrid = newCardGrid;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.lorenzoUpdate(message, lorenzoPosition, newCardGrid);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
