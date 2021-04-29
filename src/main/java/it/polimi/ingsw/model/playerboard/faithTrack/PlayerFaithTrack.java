package it.polimi.ingsw.model.playerboard.faithTrack;

import it.polimi.ingsw.model.Game;

public class PlayerFaithTrack extends FaithTrack {
    private int victoryPoints;

    public PlayerFaithTrack(Game game) {
        super(game);
        this.victoryPoints = 0;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * increases the victory points of the player
     * @param amount is the quantity of victory point to add to the player
     */
    public void increaseVictoryPoints (int amount){
        if(amount<0)
            return;
        victoryPoints += amount;
    }

}
