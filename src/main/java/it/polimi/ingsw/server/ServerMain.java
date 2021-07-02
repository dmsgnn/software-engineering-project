package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.serverToClient.PlayerNumberRequest;
import it.polimi.ingsw.messages.serverToClient.UsernameResponse;
import it.polimi.ingsw.messages.serverToClient.Welcome;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.*;

public class ServerMain {
    //needed
    private final int portNumber;
    private final ExecutorService executor;
    private final ArrayList<Lobby> lobbies;

    private final ArrayList<String> takenUsernames;
    private final Map<String, Lobby> disconnectedUsers;



    //constructor
    public ServerMain(int port){
        this.lobbies = new ArrayList<>();
        this.portNumber = port;
        this.takenUsernames = new ArrayList<>();
        this.disconnectedUsers = new HashMap<>();
        executor = Executors.newCachedThreadPool();
        //playerId = 0;
    }

    /**
     * Starts the server to listen to clients and to accept their connection
     * creates a ServerSocketHandler for each client
     */
    public void startServer() {
        System.out.println("Port number: " + portNumber);
        ServerSocket serverSocket = null;
        System.out.println("Server started!");
        try {
            try{
                serverSocket = new ServerSocket(portNumber);
            } catch (IOException e) {
                System.out.println("\nPort already in use!\n");
                exit(0);
                //e.printStackTrace();
            }
            while(true) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("Accepted!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert clientSocket != null;
                ServerSocketHandler connection = new ServerSocketHandler(clientSocket, this);
                executor.submit(connection);
                connection.startPing();
                //connection.sendMessage(new Welcome());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    /**
     * called when there is a winner, stops to ping the clients of the lobby and delete it
     * @param l is the lobby of the ending game
     */
    public void endGame (Lobby l){
        l.getLoggedPlayers().forEach((k,v)-> k.stopPing());

        for(String nick : l.getLoggedPlayers().values()){
            takenUsernames.remove(nick);
        }
        while(disconnectedUsers.containsValue(l)) {
            disconnectedUsers.values().remove(l);
        }
        l.getLoggedPlayers().clear();
        lobbies.remove(l);
        System.out.println("Number of active games: " + lobbies.size());
    }


    /**
     * makes the login of the client, if the username is already taken it sends a negative response
     * otherwise it makes the login and if there are no lobbies or they are all full a new one is created
     * if the last created lobby has free places the player enter the lobby and if after that the lobby is full the game starts
     * if the username is saved in disconnected usernames the user will be re-connected to the saved lobby
     * @param username is the nickname of the player
     * @param connection is the socket connection
     */
    public synchronized void loginUser(String username, ServerSocketHandler connection) {
        if(connection.getSender().isStopPing()){
            System.out.print("User " + username + " can't do the login because he left");
            return;
        }
        if(takenUsernames.contains(username)){
            System.out.print("Username " + username + " already taken");
            connection.sendMessage(new UsernameResponse(false, null));
            return;
        }

        if(disconnectedUsers.containsKey(username)){
            System.out.println("user "+ username+" reconnected to the game");
            Lobby lob = disconnectedUsers.get(username);
            lob.decreaseDisconnectedUsers();
            takenUsernames.add(username);
            lob.addLogged(connection, username);
            disconnectedUsers.remove(username);
            connection.setLobby(lob);
            lob.reConnection(username, connection);
            return;
        }

        takenUsernames.add(username);

        //the first lobby is created
        if(lobbies.size()==0) {
            Lobby lob;
            lob = new Lobby();
            lob.addLogged(connection, username);
            connection.setLobby(lob);
            lobbies.add(lob);
            connection.sendMessage(new UsernameResponse(true, username));
            connection.sendMessage(new PlayerNumberRequest());
            System.out.println(username+" created first lobby");
        }
        //checks if the last created lobby has a free place
        else if (!lobbies.get(lobbies.size()-1).isFull()){
            lobbies.get(lobbies.size()-1).addLogged(connection, username);
            connection.setLobby(lobbies.get(lobbies.size()-1));
            connection.sendMessage(new UsernameResponse(true, username));
            if(lobbies.get(lobbies.size()-1).isFull()) {
                lobbies.get(lobbies.size()-1).startGame();
            }
            System.out.println(username + " joined an existing lobby");

        }
        // if all lobbies are full a new one is created
        else{
            Lobby lob;
            lob = new Lobby();
            lob.addLogged(connection, username);
            connection.setLobby(lob);
            lobbies.add(lob);
            connection.sendMessage(new UsernameResponse(true, username));
            connection.sendMessage(new PlayerNumberRequest());
            System.out.println("last lobby was full, " +username+ " created a new lobby");

        }
    }

    /**
     * disconnects a user from a started game and save his nicknames for possible re-connection
     * @param lobby is the lobby where the client is disconnecting from
     * @param connection is the socket connection of the disconnecting client
     */
    public void disconnectAndSave(ServerSocketHandler connection, Lobby lobby){
        String username = lobby.getLoggedPlayers().get(connection);
        lobby.increaseDisconnectedUsers();
        System.out.println("User " + username + " disconnected from game");
        disconnectedUsers.put(username, lobby);
        takenUsernames.remove(username);
        lobby.remove(connection);
        lobby.disconnectedPlayer(username);
    }

    /**
     * disconnects a user from a lobby
     * @param lobby is the lobby where the client is disconnecting from
     * @param connection is the socket connection of the disconnecting client
     */
    public void disconnect(ServerSocketHandler connection, Lobby lobby){
        System.out.println("User " + lobby.getLoggedPlayers().get(connection) + " disconnected from lobby");
        lobby.remove(connection);
        if(lobby.getLoggedPlayers().size()==0)
            lobbies.remove(lobby);
    }


    public static void main(String[] args) throws IOException {
        int portNumber;
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if(inetAddress != null)
            System.out.println("Hello, I'm on: " + inetAddress.getHostAddress());
        else
            System.out.println("Hello, I'm on: 127.0.0.1");

        if(args.length >= 1 && Integer.parseInt(args[0])>1024)
            portNumber = Integer.parseInt(args[0]);
        else
            portNumber = 4000;

        ServerMain server = new ServerMain(portNumber);
        server.startServer();
    }
}
