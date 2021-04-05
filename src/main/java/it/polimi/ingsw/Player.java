package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.ZeroCapacityException;
import it.polimi.ingsw.leaderCard.LeaderCard;
import it.polimi.ingsw.playerBoard.PlayerBoard;

public class Player {
    private Game game;
    private String nickname;
    private int victoryPoints;
    private int turnOrder;
    private PlayerBoard playerBoard;
    private LeaderCard[] cardsHand;

    public Player(String nickname, int turnOrder, Game game) throws ZeroCapacityException {
        this.game = game;
        this.turnOrder = turnOrder;
        this.nickname = nickname;
        this.victoryPoints = 0;
        this.playerBoard = new PlayerBoard(this, game);
        this.cardsHand = new LeaderCard[2];
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /**
     * increases the victory points of the player
     * @param amount is the quantity of victory point to add to the player
     */
    public void increaseVictoryPoints (int amount){
        victoryPoints += amount;
    }

    /**
     * checks if the player is the firstPlayer
     * @return boolean true if the player is the firstPlayer
     */
    public boolean isFirst(){
        return turnOrder == 0;
    }
}
