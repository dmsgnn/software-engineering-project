package it.polimi.ingsw.leaderAction;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.exceptions.*;

public interface LeaderAction {
    public void doLeaderAction(Player player) throws ErrorActivationLeaderCardException, ExchangeBuffErrorException, DiscountBuffErrorException, ZeroCapacityException, ProductionBuffErrorException;
}
