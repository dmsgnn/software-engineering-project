package it.polimi.ingsw.server;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.messages.clientToServer.LoginMessage;
import it.polimi.ingsw.messages.clientToServer.PlayerNumberReply;
import it.polimi.ingsw.messages.serverToClient.EndGameMessage;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;

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

    private int messageCounter;

    private PingManager sender;

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
     * @param server 
     */
    public ServerSocketHandler(Socket socket, ServerMain server) {
        this.socket = socket;
        this.server = server;
        this.sender = new PingManager(this);
        this.lobby = null;
        this.messageCounter = 0;

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
                //if(event instanceof Pong) System.out.print("pong received");
                if(message instanceof PlayerNumberReply){
                    lobby.setPlayerGameNumber(((PlayerNumberReply) message).getPlayerNum());
                    if(lobby.getPlayerGameNumber()==1)
                        lobby.startGame();
                }
                if(message instanceof LoginMessage) {
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
     * @param message
     */
    public synchronized void sendMessage(ServerMessage message) {
        try {
            out.writeObject(message);
            out.flush();
            if(message instanceof EndGameMessage)
                messageCounter++;
            if(messageCounter>=lobby.getLoggedPlayers().size())
                server.endGame(lobby);
        } catch (Exception e) {
            e.getMessage();
            //e.printStackTrace();
        }
    }

    /**
     * Makes the login of the user on the server
     * @param message
     */
    private void login(LoginMessage message) {
        synchronized (lock) {
            server.loginUser(message.getUsername(),this);
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
            if(lobby.isGameStarted()) {
                //this part must be modified if persistence FA
                if(lobby.getPlayerGameNumber()==1 || lobby.getLoggedPlayers().size() == 1){
                    server.deleteLobby(lob);
                    return;
                }
                server.disconnectAndSave(this, lob);
            }
            else {
                server.disconnect(this, lob);
            }
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
