package it.polimi.ingsw.controller;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;

public class Controller implements Observer<ClientMessage> {


    /**
     * Extracts the information from the {@link ClientMessage} which will call a method on the controller.
     * @param message message from the client.
     */
    @Override
    public void update(ClientMessage message) {
        message.handleMessage(this);

    }
}
