package it.polimi.ingsw.leadercard.ability;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.ProductionBuffErrorException;
import it.polimi.ingsw.playerboard.PlayerBoard;

public class ProductionAbility implements Ability {
    private Resource cost;


    @Override
    public void setResource(Resource cost) {
        this.cost = cost;
    }

    /**
     * add the resource to the buff
     * @param playerBoard
     * @throws ProductionBuffErrorException
     */
    @Override
    public void useAbility(PlayerBoard playerBoard) throws ProductionBuffErrorException {
        playerBoard.getLeaderCardBuffs().addProductionBuff(cost);

    }
}
