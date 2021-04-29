package it.polimi.ingsw.leadercard.ability;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.playerboard.PlayerBoard;

public interface Ability {
    public void useAbility(PlayerBoard playerBoard);
    public void setResource(Resource resource);
}
