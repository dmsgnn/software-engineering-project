package it.polimi.ingsw.client;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.messages.clientToServer.Pong;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;

public class PingReceiver implements Observer<ServerMessage> {
    private ClientSocketHandler connection;

    //constructor
    public PingReceiver(ClientSocketHandler connection) {
        this.connection = connection;
        connection.addObserver(this);
    }

    /**
     * when a ping is received this method is called
     */
    public void receivePing() {
        connection.sendMessage(new Pong());
    }

    /**
     * Updates the class to send a pong when it receives a ping.
     * @param message is the ServerMessage received
     */
    @Override
    public void update(ServerMessage message) {
        message.handleMessage(this);
    }
}
