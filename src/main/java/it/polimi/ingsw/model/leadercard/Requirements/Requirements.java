package it.polimi.ingsw.model.leadercard.Requirements;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

import java.util.Map;

public interface Requirements {

    /**
     * the method checks that the purchase requirements are met
     * @param playerBoard of the player
     * @return true if the player can buy the card
     */
    public boolean checkRequirements(PlayerBoard playerBoard);


    /**
     * method to draw the card's requirements
     * @return string containing the requirements to print
     */
    public String drawRequirements();
}
