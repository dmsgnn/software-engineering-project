package it.polimi.ingsw.server;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.messages.serverToClient.Ping;

import java.util.Timer;
import java.util.TimerTask;

public class PingManager implements Observer<ClientMessage>{
    private ServerSocketHandler connection;
    private boolean ping;
    private Timer pinger;
    private TimerTask task;
    private boolean stopPing=false;

    //constructor
    public PingManager(ServerSocketHandler connection) {
        this.connection = connection;
        connection.addObserver(this);
        ping = true;
    }

    /**
     * sets true the boolean ping variable
     */
    public void receivePing() {
        ping = true;
    }

    /**
     * when a connection is established this method is called
     * a timer is started and it is updated whenever a pong from the client is received and then sends a ping to reply
     * if the ping is not received within two secs, it drops the connection
     */
    public void startPing() {
        pinger = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if(!ping) {
                    //System.out.println("timer");
                    pinger.cancel();
                    stopPing=true;
                    connection.disconnect(connection.getLobby());
                }
                else {
                    ping = false;
                    connection.sendMessage(new Ping());
                    //System.out.print("ping");
                }
            }
        };

        //2 sec to receive pong
        pinger.schedule(task,0,2000);

    }

    /**
     * cancels the timer for the ping and stops to check if the client is still connected
     */
    public void stop() {
        if(!stopPing) {
            pinger.cancel();
            stopPing=true;
        }
    }

    /**
     * Manages the reception of a pong message
     * @param message
     */
    @Override
    public void update(ClientMessage message) {
        message.handleMessage(this);
    }
}
