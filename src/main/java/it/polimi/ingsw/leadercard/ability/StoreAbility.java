package it.polimi.ingsw.leadercard.ability;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import it.polimi.ingsw.playerboard.depot.CardDepot;
import it.polimi.ingsw.playerboard.PlayerBoard;

public class StoreAbility implements Ability {
    private Resource resource;

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    /**
     * add the new depot
     * @param playerBoard
     * @throws ZeroCapacityException
     */
    @Override
    public void useAbility(PlayerBoard playerBoard) throws ZeroCapacityException {
        playerBoard.getWarehouse().getDepots().add(new CardDepot(2,0,resource));
        playerBoard.getWarehouse().setDepotsNum(playerBoard.getWarehouse().getDepotsNum()+1);

    }
}
