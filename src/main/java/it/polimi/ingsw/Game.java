package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.FullPlayerException;

public class Game {
    private Player[] players;
    private int numVaticanReports;
    private int playersNumber;
    private Player activePlayer;
    private boolean lastRound;
    private boolean terminator;
    private boolean soloMode;
    //private GameBoard board;


    public int getNumVaticanReports() {
        return numVaticanReports;
    }

    public void setNumVaticanReports(int numVaticanReports) {
        this.numVaticanReports = numVaticanReports;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * creates a new player
     * @param nickname is the nickname of the new player, it is unique and it can't be changed during the game
     */
    public void addPlayer(String nickname) throws FullPlayerException {
        if(playersNumber < 4) {
            players[playersNumber] = new Player(nickname, playersNumber);
            playersNumber += 1;
        }
        else throw new FullPlayerException();
    }
}
