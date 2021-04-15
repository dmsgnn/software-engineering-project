package it.polimi.ingsw;

import it.polimi.ingsw.actions.Actions;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.gameboard.Gameboard;
import it.polimi.ingsw.singleplayer.LorenzoAI;

import javax.naming.InsufficientResourcesException;

public class Game {
    private Player[] players = new Player[4];
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
    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }


    /**
     * the player takes an action
     * @param action selected to do
     */
    public void doAction(Actions action) throws InvalidActionException, InsufficientResourcesException, WrongLevelException, EmptyWarehouseException, NoCardsLeftException {
        action.doAction(activePlayer.getPlayerBoard());
    }

    /**
     * creates a new player
     * @param nickname is the nickname of the new player, it is unique and it can't be changed during the game
     */
    public void addPlayer(String nickname) throws FullPlayerException, ZeroCapacityException {
        if(playersNumber < 4) {
            players[playersNumber] = new Player(nickname, playersNumber, this);
            playersNumber += 1;
        }
        else throw new FullPlayerException();
    }


}
