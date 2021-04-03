package it.polimi.ingsw;

public class Player {
    private String nickname;
    private int victoryPoints;
    private int turnOrder;
    //private PlayerBoard playerBoard;

    public Player(String nickname, int turnOrder){
        this.turnOrder = turnOrder;
        this.nickname = nickname;
        this.victoryPoints = 0;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getTurnOrder() {
        return turnOrder;
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
