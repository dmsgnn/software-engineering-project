package it.polimi.ingsw.client;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.messages.clientToServer.Pong;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;

/**
 * Class to verify that a user is still active. Once a ping is received from the server,
 * a {@link Pong} is sent to prove that the connection is working.
 * It extends the class observer to effectively update and override the method when a Pong (a ServerMessage) is received.
 */
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
