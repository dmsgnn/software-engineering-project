package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.FullPlayerException;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import it.polimi.ingsw.gameboard.Gameboard;

public class Game {
    private Player[] players = new Player[4];
    private int numVaticanReports;
    private int playersNumber;
    private Player activePlayer;
    private boolean lastRound;
    private boolean terminator;
    private boolean soloMode;
    private Gameboard board = new Gameboard();



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
