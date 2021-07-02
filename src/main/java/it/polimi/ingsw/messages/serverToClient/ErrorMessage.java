package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.controller.Error;

/**
 * this class tells the client that an error has been made
 */
public class ErrorMessage implements ServerMessage{
    private final Error error;

    public ErrorMessage(Error error) {
        this.error= error;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.errorManagement(error);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
