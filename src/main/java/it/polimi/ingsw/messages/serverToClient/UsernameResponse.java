package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;

public class UsernameResponse implements ServerMessage{
    private String nickname;

    public UsernameResponse(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void handleMessage(ClientView clientView) { clientView.setNickname(nickname);}
}
