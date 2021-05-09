package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

public class UsernameReply implements ClientMessage{
    private String username;

    public UsernameReply(String username) {
        this.username = username;
    }

    @Override
    public void handleMessage(Controller controller) { controller.addUsername(username);}

    @Override
    public void handleMessage(PingManager pingSender) {

    }
}
