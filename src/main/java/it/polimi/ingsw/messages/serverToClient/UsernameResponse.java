package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

import java.util.ArrayList;

public class UsernameResponse implements ServerMessage{
    private String nickname;
    private boolean isFree;
    private ArrayList<String> usedNicknames;

    public UsernameResponse(boolean isFree, String nickname, ArrayList<String> usedNicknames) {
        this.usedNicknames = new ArrayList<>(usedNicknames);
        this.isFree = isFree;
        this.nickname = nickname;
    }


    @Override
    public void handleMessage(ClientView clientView) { clientView.manageUsernameResponse(isFree, nickname, usedNicknames);}

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
