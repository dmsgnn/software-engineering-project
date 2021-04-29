package it.polimi.ingsw.leadercard.ability;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.playerboard.PlayerBoard;

public class DiscountAbility implements Ability{
    private Resource resource;

    @Override
    public void setResource(Resource cost) {
        this.resource = cost;
    }

    /**
     * add the resource to the buff
     * @param playerBoard of the player
     */
    @Override
    public void useAbility(PlayerBoard playerBoard) {
        playerBoard.getLeaderCardBuffs().addDiscountBuff(this.resource);


    }
}
