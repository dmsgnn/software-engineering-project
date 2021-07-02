package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

import java.util.ArrayList;

/**
 * this class sends to the client the chosen username
 * and whether or not it has been set as active
 */
public class UsernameResponse implements ServerMessage{
    private final String nickname;
    private final boolean isFree;

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
