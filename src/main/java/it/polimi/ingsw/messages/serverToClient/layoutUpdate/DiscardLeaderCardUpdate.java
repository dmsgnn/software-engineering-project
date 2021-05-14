package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;

public class DiscardLeaderCardUpdate implements ServerMessage {

     private String nickname;
     private String id;


    public DiscardLeaderCardUpdate(String nickname, String id) {
        this.nickname = nickname;
        this.id = id;
    }

    @Override
    public void handleMessage(ClientView clientView) {

        clientView.discardLeaderCardUpdate(nickname, id);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
