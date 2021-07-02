package it.polimi.ingsw.model.leadercard.ability;

import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

/**
 * cointais the methods common to all the leadercard's abilities
 */
public interface Ability {
    void useAbility(PlayerBoard playerBoard);
    void setResource(Resource resource);
    String drawAbility();
    void clientAbility(ClientPlayerBoard playerBoard);
}
