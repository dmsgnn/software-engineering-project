package it.polimi.ingsw.client;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import static java.lang.System.exit;

public class ClientSocketHandler extends Observable<ServerMessage> implements Runnable {
    Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    //constructor
    public ClientSocketHandler(Socket socket) {
        this.socket = socket;

        ObjectInputStream tempin = null;
        ObjectOutputStream tempout = null;

        try {
            tempout = new ObjectOutputStream(socket.getOutputStream());
            tempin = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.in = tempin;
        this.out = tempout;

        PingReceiver pinger = new PingReceiver(this);
    }

    /**
     * Thread to remain open to accept server events
     */
    @Override
    public void run() {
        System.out.println("running...");

        try {
            while (true) {
                ServerMessage message = (ServerMessage) in.readObject();

                Thread t = new Thread(() -> {
                    receiveMessage(message);
                });
                t.setDaemon(false);
                t.setPriority(Thread.MAX_PRIORITY);
                t.start();
            }
        } catch(IOException | ClassNotFoundException e) {
            e.getMessage();
            System.out.println("\nServer shut down \n ");
            exit(0);
            //e.printStackTrace();
        }
    }

    /**
     * Called whenever a server message is received
     * @param message Server message to be read
     */
    private void receiveMessage(ServerMessage message) {
        notify(message);
    }

    /**
     * Sends a ClientMessage to the server
     * @param message ClientMessage to be sent
     */
    public synchronized void sendMessage(ClientMessage message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.getMessage();
            //e.printStackTrace();
        }
    }

    /**
     * Closes the socket after closing the streams.
     */
    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        }
        catch(Exception e) {
            //e.printStackTrace();
            e.getMessage();
        }
    }

}
