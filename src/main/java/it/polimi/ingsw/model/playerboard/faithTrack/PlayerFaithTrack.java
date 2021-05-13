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
     * increases the position and check if the vatican report can be activated, in case activates it
     */
    public void increasePosition(){
        if(!endOfTrack()) {
            position += 1;
            if(position==3)
                increaseVictoryPoints(1);
            if(position==6)
                increaseVictoryPoints(2);
            if(position==9)
                increaseVictoryPoints(4);
            if(position==12)
                increaseVictoryPoints(6);
            if(position==15)
                increaseVictoryPoints(9);
            if(position==18)
                increaseVictoryPoints(12);
            if(position==21)
                increaseVictoryPoints(16);
            if(position==24)
                increaseVictoryPoints(20);
            if(vaticanReportCheck()){
                vaticanReportActivation();
            }
        }
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
