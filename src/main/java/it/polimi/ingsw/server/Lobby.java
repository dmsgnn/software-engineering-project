package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class Lobby {
    //map to save the nickname and the socket of the player
    //a player is added when connected to the game and deleted when he disconnects
    private Map<ServerSocketHandler, String> loggedPlayers = new HashMap<>();
    //num of players
    private int playerGameNumber;
    //true if the game is started
    private boolean isGameStarted;
    //number of disconnected users
    private int disconnectedUsers;

    private Controller controller;

    public int getDisconnectedUsers() {
        return disconnectedUsers;
    }

    public Map<ServerSocketHandler, String> getLoggedPlayers() {
        return loggedPlayers;
    }

    public void setPlayerGameNumber(int playerGameNumber) {
        this.playerGameNumber = playerGameNumber;
    }


    public int getPlayerGameNumber() {
        return playerGameNumber;
    }


    public boolean isGameStarted() {
        return isGameStarted;
    }

    /**
     * decreases the number of disconnected users
     * used when a player reconnects to the game
     */
    public synchronized void decreaseDisconnectedUsers(){
        disconnectedUsers--;
    }

    /**
     * increases the number of disconnected users
     * used when a player leaves a game
     */
    public synchronized void increaseDisconnectedUsers(){
        disconnectedUsers++;
    }

    /**
     * return true if the number of disconnected users plus the logged users is equals to the total player number
     */
    public synchronized boolean isFull(){
        return(loggedPlayers.size() + disconnectedUsers == playerGameNumber);
    }

    /**
     * Start the game creating a new game and a new controller
     * it also initializes the observer and observable classes
     */
    public void startGame() {
        System.out.println("The game is about to begin");
        isGameStarted = true;

        ArrayList<ServerView> users = new ArrayList<>();
        ArrayList<String> players = new ArrayList<>();

        for(ServerSocketHandler s : loggedPlayers.keySet()){
            ServerView newUser = new ServerView(loggedPlayers.get(s), s);
            players.add(loggedPlayers.get(s));
            users.add(newUser);
        }



        Game game = new Game(players);
        controller = new Controller(game, users);

        for(ServerView s : users) {
            s.addObserver(controller);
        }
        //connections.stream().forEach(x -> x.sendEvent(new GameStartEvent()));
        controller.startGame();
        printUsers();

    }

    /**
     * calls the reconnection method of the controller
     * used when a player reconnects to the game
     */
    public void reConnection(String username, ServerSocketHandler connection){
        ServerView reconnectedUser = new ServerView(username, connection);
        controller.playerReconnection(reconnectedUser);
    }

    /**
     * used when to communicate to the controller that a player has been disconnected from the game
     */
    public void disconnectedPlayer(String username){
        //controller.playerDisconnection(username);
    }

    /**
     * Prints the list of logged players
     */
    private void printUsers() {
        System.out.println(loggedPlayers.size() + " Logged players: ");
        loggedPlayers.values().forEach(System.out::println);
    }

}
