package it.polimi.ingsw.messages.serverToClient.layoutUpdate;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;

public class DiscardLeaderCardUpdate implements ServerMessage {

     private String nickname;
     private String id;
     private int playerFaith;

    public DiscardLeaderCardUpdate(String nickname, String id, int playerFaith) {
        this.nickname = nickname;
        this.id = id;
        this.playerFaith = playerFaith;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.discardLeaderCardUpdate(nickname, id, playerFaith);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
