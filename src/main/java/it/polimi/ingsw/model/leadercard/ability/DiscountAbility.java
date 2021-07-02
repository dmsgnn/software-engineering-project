package it.polimi.ingsw.model.leadercard.ability;

import it.polimi.ingsw.client.CLI.ColorCLI;
import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

/**
 * represents the resource discount ability of the leadercards
 */
public class DiscountAbility implements Ability{
    private Resource resource;

    @Override
    public void setResource(Resource cost) {
        this.resource = cost;
    }

    public Resource getResource() {
        return resource;
    }

    @Override
    public String drawAbility() {
        StringBuilder builder = new StringBuilder();
        builder.append(ColorCLI.resourceColor(resource)).append("-1");
        return "   " + builder.toString() + "    ";
    }

    @Override
    public void clientAbility(ClientPlayerBoard playerBoard) {
        playerBoard.addDiscountBuff(resource);
    }

    /**
     * add the resource to the buff
     * @param playerBoard of the player
     */
    @Override
    public void useAbility(PlayerBoard playerBoard) {
        playerBoard.getLeaderCardBuffs().addDiscountBuff(this.resource);


    }
}
