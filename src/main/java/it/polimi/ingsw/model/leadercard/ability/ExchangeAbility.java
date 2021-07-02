package it.polimi.ingsw.model.leadercard.ability;

import it.polimi.ingsw.client.CLI.ColorCLI;
import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
/**
 * represents the market's marbles exchange ability of the leadercards
 */
public class ExchangeAbility implements Ability {
    private Resource gain;


    @Override
    public void setResource(Resource gain) {
        this.gain = gain;
    }

    public Resource getGain() {
        return gain;
    }

    @Override
    public String drawAbility() {
        StringBuilder builder = new StringBuilder();
        builder.append(ColorCLI.RESET).append("● = ").append(ColorCLI.resourceColor(gain)).append("●");
        return "  " + builder.toString() + "  ";
    }

    @Override
    public void clientAbility(ClientPlayerBoard playerBoard) {
        playerBoard.addExchangeBuff(gain);
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
