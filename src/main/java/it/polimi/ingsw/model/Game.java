package it.polimi.ingsw.model;

import it.polimi.ingsw.model.actions.Actions;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.gameboard.Gameboard;
import it.polimi.ingsw.model.singleplayer.LorenzoAI;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;

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

    public void nextPlayer(){
        for (int i=0; i<4;i++){
            if (activePlayer.equals(players[i])) {
                if (i==3) activePlayer= players[0];
                else activePlayer= players[i+1];
            }
        }
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


}
