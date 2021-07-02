package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.server.PingManager;

/**
 * this class allows the addition of a player to a game
 */
public class LoginMessage implements ClientMessage{
    private String username;

    public LoginMessage(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void handleMessage(Controller controller) {}

    @Override
    public void handleMessage(PingManager pingSender){
    }

}
