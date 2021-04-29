package it.polimi.ingsw.model.leadercard.ability;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

public class ProductionAbility implements Ability {
    private Resource cost;


    @Override
    public void setResource(Resource cost) {
        this.cost = cost;
    }

    /**
     * add the resource to the buff
     * @param playerBoard of the player
     */
    @Override
    public void useAbility(PlayerBoard playerBoard) {
        playerBoard.getLeaderCardBuffs().addProductionBuff(cost);

    }
}
