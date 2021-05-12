package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

public class ErrorMessage implements ServerMessage{
    private final String string;

    public ErrorMessage(String string) {
        this.string = string;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.responseMessage(string);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
