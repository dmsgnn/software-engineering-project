package it.polimi.ingsw.model.leadercard.ability;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

public class ExchangeAbility implements Ability {
    private Resource gain;


    @Override
    public void setResource(Resource gain) {
        this.gain = gain;
    }

    /**
     * add the resource to the buff
     * @param playerBoard of the player
     */
    @Override
    public void useAbility(PlayerBoard playerBoard) {
        playerBoard.getLeaderCardBuffs().addExchangeBuff(gain);

    }
}
