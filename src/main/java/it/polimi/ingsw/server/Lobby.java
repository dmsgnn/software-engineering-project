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

    public Map<ServerSocketHandler, String> getLoggedPlayers() {
        return loggedPlayers;
    }

    public void setPlayerGameNumber(int playerGameNumber) {
        this.playerGameNumber = playerGameNumber;
    }


    public int getPlayerGameNumber() {
        return playerGameNumber;
    }

    public synchronized boolean isFull(){
        return(loggedPlayers.size() == playerGameNumber);
    }

    public boolean isGameStarted() {
        return isGameStarted;
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
        Controller controller = new Controller(game, users);

        for(ServerView s : users) {
            s.addObserver(controller);
        }
        //connections.stream().forEach(x -> x.sendEvent(new GameStartEvent()));
        controller.startGame();
        printUsers();

    }

    /**
     * Prints the list of logged players
     */
    private void printUsers() {
        System.out.println("number of players: " + loggedPlayers.size());
        System.out.println("Logged players: ");
        loggedPlayers.values().forEach(System.out::println);
    }

}
