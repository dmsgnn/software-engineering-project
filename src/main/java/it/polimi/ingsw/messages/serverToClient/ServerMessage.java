package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.server.PingManager;

import java.io.Serializable;

public interface ServerMessage extends Serializable {
    void handleMessage(ClientView clientView);
    void handleMessage(PingReceiver pingManager);
}
