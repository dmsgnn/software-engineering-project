package it.polimi.ingsw.model.leadercard.ability;

import it.polimi.ingsw.client.CLI.ColorCLI;
import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

public class ProductionAbility implements Ability {
    private Resource cost;


    @Override
    public void setResource(Resource cost) {
        this.cost = cost;
    }

    public Resource getCost() {
        return cost;
    }

    @Override
    public String drawAbility() {
        StringBuilder builder = new StringBuilder();
        builder.append(ColorCLI.resourceColor(cost)).append("1");
        builder.append(ColorCLI.RESET).append(" =");
        builder.append(ColorCLI.RESET).append(" ?");
        builder.append(ColorCLI.RED).append(" 1");
        return " " + builder.toString() + " ";
    }

    @Override
    public void clientAbility(ClientPlayerBoard playerBoard) {
        playerBoard.addProductionBuff(cost);
    }

    /**
     * add the resource to the buff
     * @param playerBoard of the player
     */
    @Override
    public void useAbility(PlayerBoard playerBoard) {
        playerBoard.getLeaderCardBuffs().addProductionBuff(cost);

    }
}
