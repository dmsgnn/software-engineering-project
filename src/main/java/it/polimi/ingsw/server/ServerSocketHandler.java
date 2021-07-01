package it.polimi.ingsw.server;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.messages.clientToServer.LoginMessage;
import it.polimi.ingsw.messages.clientToServer.PlayerNumberReply;
import it.polimi.ingsw.messages.serverToClient.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerSocketHandler extends Observable<ClientMessage> implements Runnable{
    private static final Object lock = new Object();

    private Socket socket;
    private ServerMain server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Lobby lobby;


    private PingManager sender;

    public PingManager getSender() {
        return sender;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    // constructor
    /**
     * Opens a channel between the client and the server and start the pinging process
     * @param socket client connection
     * @param server server to be connected with
     */
    public ServerSocketHandler(Socket socket, ServerMain server) {
        this.socket = socket;
        this.server = server;
        this.sender = new PingManager(this);
        this.lobby = null;

        try {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            //e.printStackTrace();
            e.getMessage();
        }
    }

    /**
     * The thread catches the events coming from the client to communicate to the server.
     * If the event is a LoginMessage, a method is called
     */
    public void run() {
        try {
            while(true) {
                ClientMessage message = (ClientMessage) in.readObject();
                if(message instanceof PlayerNumberReply){
                    if(((PlayerNumberReply) message).getPlayerNum() > 0 && ((PlayerNumberReply) message).getPlayerNum() < 5) {
                        lobby.setPlayerGameNumber(((PlayerNumberReply) message).getPlayerNum());
                        if (lobby.getPlayerGameNumber() == 1)
                            lobby.startGame();
                        synchronized (lock) {
                            lock.notifyAll();
                        }
                    }
                    else{
                        sendMessage(new PlayerNumberRequest());
                    }
                }
                else if(message instanceof LoginMessage) {
                    LoginMessage presentation = (LoginMessage) message;
                    login(presentation);
                }
                else notify(message);
            }
        }catch (Exception e) {
            //e.printStackTrace();
            e.getMessage();
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException ioException) {
                //ioException.printStackTrace();
            }
        }
    }

    /**
     * Sends a message to the client
     * @param message is the message to send
     */
    public synchronized void sendMessage(ServerMessage message) {
        try {
            out.writeObject(message);
            out.flush();
            if(message instanceof FinalScoreMessage)
                lobby.increaseMessageCounter();
            if(lobby.getMessageCounter() >= lobby.getLoggedPlayers().size() && lobby.getLoggedPlayers().size() != 0) {
                System.out.println("there is a winner, game will end soon\n");
                server.endGame(lobby);
            }
        } catch (Exception e) {
            e.getMessage();
            //e.printStackTrace();
        }
    }

    /**
     * Makes the login of the user on the server
     * @param message is the login message which contains the nickname of the user
     */
    private void login(LoginMessage message) {
        synchronized (lock) {
            while(server.getLobbies().size() > 0 && server.getLobbies().get(server.getLobbies().size()-1).getPlayerGameNumber() == 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            }
            if(getSender().isStopPing()){
                this.sender = new PingManager(this);
                startPing();
            }

            server.loginUser(message.getUsername(), this);
        }
    }

    /**
     * Disconnects only the requested client
     * if the game is single player or there is only one user connected the lobby is deleted (thinking about persistence FA..)
     * if the game is started and there are more than one player connected the user is disconnected and saved
     * otherwise the user is disconnected and nothing is saved
     * @param lob is the lobby from the client is disconnected
     */
    protected void disconnect(Lobby lob) {

        synchronized (lock) {
            // this part must be used because when all the player leave a game it mustn't be deleted
            if(lobby.isGameStarted()) {
                server.disconnectAndSave(this, lob);
            }
            else {
                server.disconnect(this, lob);
            }
            if(server.getLobbies().size() == 0 || server.getLobbies().get(server.getLobbies().size()-1).isFull())
                lock.notifyAll();
        }
    }

    /**
     * Starts the ping interaction
     */
    public void startPing() {
        sender.startPing();
    }

    /**
     * Stops the ping interaction
     */
    public void stopPing() {
        sender.stop();
    }


}
