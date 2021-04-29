package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;

public class UsernameRequest implements ServerMessage{
    private final String string ="username";

    @Override
    public void handleMessage(ClientView clientView) { }
}
