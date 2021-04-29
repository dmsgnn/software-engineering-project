package it.polimi.ingsw.messages.clientToServer;

import it.polimi.ingsw.controller.Controller;

public class LoginMessage implements ClientMessage{
    private final String login = "login";


    @Override
    public void handleMessage(Controller controller) { }
}
