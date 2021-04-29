package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;

import java.io.Serializable;

public interface ServerMessage extends Serializable {
    void handleMessage(ClientView clientView);
    // void handleMessage(PingManager pingManager);
}
