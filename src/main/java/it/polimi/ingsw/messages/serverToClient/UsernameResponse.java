package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

import java.util.ArrayList;

public class UsernameResponse implements ServerMessage{
    private String nickname;
    private boolean isFree;

    public UsernameResponse(boolean isFree, String nickname) {
        this.isFree = isFree;
        this.nickname = nickname;
    }



    @Override
    public void handleMessage(ClientView clientView) { clientView.manageUsernameResponse(isFree, nickname);}

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
