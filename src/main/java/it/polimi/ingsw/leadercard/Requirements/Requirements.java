package it.polimi.ingsw.leadercard.Requirements;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.playerboard.PlayerBoard;

import java.util.Map;

public interface Requirements {

    /**
     * the method checks that the purchase requirements are met
     * @param playerBoard of the player
     * @return true if the player can buy the card
     */
    public boolean checkRequirements(PlayerBoard playerBoard);

    /**
     * @param playerBoard of the player
     * @return the resource requirements
     */
    public Map<Resource, Integer> getRequirements(PlayerBoard playerBoard);

    /**
     *
     * @return true if the card has color requirements, false if has resource requirements
     */
    public boolean IsColor();
}
