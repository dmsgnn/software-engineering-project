package it.polimi.ingsw.model.leadercard.ability;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

public interface Ability {
    public void useAbility(PlayerBoard playerBoard);
    public void setResource(Resource resource);
    String drawAbility();
}
