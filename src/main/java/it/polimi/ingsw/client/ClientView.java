package it.polimi.ingsw.client;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;

public class ClientView implements Observer<ServerMessage> {


    /**
     * Called when a {@link ServerMessage} is received. The package is extracted and the method on the incoming
     * event overrides a method on ClientView
     * @param message Incoming message
     */
    @Override
    public void update(ServerMessage message) {
        message.handleMessage(this);
    }
}
