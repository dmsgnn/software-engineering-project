package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

public class Welcome implements ServerMessage{

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.login();
    }

    @Override
    public void handleMessage(PingReceiver ping) {
        return;
    }

}
