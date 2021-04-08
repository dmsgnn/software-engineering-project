package it.polimi.ingsw.leadercard.ability;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.DiscountBuffErrorException;
import it.polimi.ingsw.exceptions.ExchangeBuffErrorException;
import it.polimi.ingsw.exceptions.ProductionBuffErrorException;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import it.polimi.ingsw.playerboard.PlayerBoard;

public interface Ability {
    public void useAbility(PlayerBoard playerBoard) throws DiscountBuffErrorException, ExchangeBuffErrorException, ProductionBuffErrorException, ZeroCapacityException;
    public void setResource(Resource resource);
}
