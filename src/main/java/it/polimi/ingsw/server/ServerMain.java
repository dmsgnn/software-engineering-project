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
    private int portNumber;
    private ExecutorService executor;
    private ArrayList<Lobby> lobbies;

    private ArrayList<String> takenUsernames;
    private Map<String, Lobby> disconnectedUsers;

    //private int playerID;
    private Timer timer;


    //constructor
    public ServerMain(int port){
        this.lobbies = new ArrayList<>();
        this.portNumber = port;
        this.takenUsernames = new ArrayList<>();
        this.disconnectedUsers = new HashMap<>();
        executor = Executors.newCachedThreadPool();
        //playerId = 0;
        timer = new Timer();
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
                //System.out.println("\nPort already in use!\n");
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
                connection.sendMessage(new Welcome());
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
    //used also when all the players of the game disconnects (to modify if persistence FA)
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
        if(takenUsernames.contains(username)){
            System.out.print("Username " + username + " already taken");
            connection.sendMessage(new UsernameResponse(false, null, takenUsernames));
            return;
        }

        if(disconnectedUsers.containsKey(username)){
            System.out.println("user "+ username+" reconnected to the game");
            Lobby lob = disconnectedUsers.get(username);
            lob.decreaseDisconnectedUsers();
            takenUsernames.add(username);
            lob.getLoggedPlayers().put(connection, username);
            disconnectedUsers.remove(username);
            lob.reConnection(username, connection);
            return;
        }

        takenUsernames.add(username);

        //the first lobby is created
        if(lobbies.size()==0) {
            Lobby lob;
            lob = new Lobby();
            lob.getLoggedPlayers().put(connection, username);
            connection.setLobby(lob);
            lobbies.add(lob);
            connection.sendMessage(new UsernameResponse(true, username, new ArrayList<>()));
            connection.sendMessage(new PlayerNumberRequest());
            System.out.println(username+" creo la prima lobby");
        }
        //checks if the last created lobby has a free place
        else if (!lobbies.get(lobbies.size()-1).isFull()){
            lobbies.get(lobbies.size()-1).getLoggedPlayers().put(connection, username);
            connection.setLobby(lobbies.get(lobbies.size()-1));
            connection.sendMessage(new UsernameResponse(true, username, new ArrayList<>()));
            if(lobbies.get(lobbies.size()-1).isFull()) {
                lobbies.get(lobbies.size()-1).startGame();
            }
            System.out.println(username + " l'ultima lobby non è piena");

        }
        // if all lobbies are full a new one is created
        else{
            Lobby lob;
            lob = new Lobby();
            lob.getLoggedPlayers().put(connection, username);
            connection.setLobby(lob);
            lobbies.add(lob);
            connection.sendMessage(new UsernameResponse(true, username, new ArrayList<>()));
            connection.sendMessage(new PlayerNumberRequest());
            System.out.println(username+" l'ultima lobby era piena e ne creo una nuova");

        }
        connection.startPing();
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
        lobby.getLoggedPlayers().remove(connection);
        lobby.disconnectedPlayer(username);
    }

    /**
     * disconnects a user from a lobby
     * @param lobby is the lobby where the client is disconnecting from
     * @param connection is the socket connection of the disconnecting client
     */
    public void disconnect(ServerSocketHandler connection, Lobby lobby){
        System.out.println("User " + lobby.getLoggedPlayers().get(connection) + " disconnected from lobby");
        lobby.getLoggedPlayers().remove(connection);
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
