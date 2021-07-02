package it.polimi.ingsw.model;

import it.polimi.ingsw.model.actions.Actions;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.gameboard.Gameboard;
import it.polimi.ingsw.model.singleplayer.LorenzoAI;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;

/**
 * Core class of the game logic in the model.
 * This class holds the list of the  players.
 * It initializes everything necessary to start a game
 * and to follow its evolution.
 */
public class Game {
    private Player[] players;
    private int numVaticanReports;
    private int playersNumber;
    private Player activePlayer;
    private boolean lastRound;
    private boolean terminator;
    private boolean soloMode;
    private LorenzoAI lorenzo;
    private Gameboard board = new Gameboard(this);

    public int getNumVaticanReports() {
        return numVaticanReports;
    }
    public void setNumVaticanReports(int numVaticanReports) {
        this.numVaticanReports = numVaticanReports;
    }
    public Player getActivePlayer() {
        return activePlayer;
    }
    public Gameboard getBoard() {
        return board;
    }
    public Player getPlayers(int numPlayer) {
        return players[numPlayer];
    }
    public int getPlayersNumber() {
        return playersNumber;
    }
    public LorenzoAI getLorenzo() {
        return lorenzo;
    }

    public boolean isSoloMode() {
        return soloMode;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    //constructor used for testing
    public Game() {
        this.players = new Player[4];
        this.lorenzo = new LorenzoAI(this);
        this.board = new Gameboard(this);
    }

    public Game(ArrayList<String> players){
        this.players = new Player[4];
        this.board = new Gameboard(this);
        this.playersNumber = players.size();
        if(players.size()==1){
            this.lorenzo = new LorenzoAI(this);
            this.soloMode=true;
        }
        else{
            this.soloMode = false;
        }
        for(int i=0; i<players.size(); i++){
            this.players[i] = new Player(players.get(i), i, this);
        }
    }

    /**
     * the player takes an action
     * @param action selected to do
     */
    public void doAction(Actions action) throws InvalidActionException, InsufficientResourcesException, WrongLevelException, NoCardsLeftException {
        action.doAction(activePlayer.getPlayerBoard());
    }

    /**
     * creates a new player
     * @param nickname is the nickname of the new player, it is unique and it can't be changed during the game
     */
    //used for testing
    public void addPlayer(String nickname) throws FullPlayerException {
        if(playersNumber < 4) {
            players[playersNumber] = new Player(nickname, playersNumber, this);
            playersNumber += 1;
        }
        else throw new FullPlayerException();
    }

    /**
     * this method checks if the current active player has 7 active cards
     * @return true if he has them
     */
    private boolean devCardEnd(){
        for (Player player: players){
            if (player != null) {
                int counter = 0;
                for (int i = 0; i < 3; i++) {
                    counter += player.getPlayerBoard().getSlots().get(i).numOfCards();
                }
                if (counter >= 7) return true;
            }
        }
        return false;
    }

    /**
     * this method checks if a player has made it to the end of the faithtrack
     * @returnand true if he has made it
     */
    private boolean faithTrackEnd(){
        for (Player player:players){
            if (player != null) {
                if (player.getFaithTrack().endOfTrack()) return true;
            }
        }
        return false;
    }

    /**
     * @return true if the player has finished the game
     */
    public boolean endGame(){
        boolean dev = devCardEnd();
        boolean faith = faithTrackEnd();
        return  dev||faith;
    }




}
