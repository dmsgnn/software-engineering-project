package it.polimi.ingsw.leadercard.ability;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.ExchangeBuffErrorException;
import it.polimi.ingsw.playerboard.PlayerBoard;

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
