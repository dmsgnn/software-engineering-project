package it.polimi.ingsw.model.leadercard.ability;

import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.client.representations.ColorCLI;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.depot.CardDepot;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

public class StoreAbility implements Ability {
    private Resource resource;

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String drawAbility() {
        StringBuilder builder = new StringBuilder();
        builder.append(ColorCLI.resourceColor(resource)).append("■ ■");
        return "   " + builder.toString() + "   ";
    }

    @Override
    public void clientAbility(ClientPlayerBoard playerBoard) {

    }

    /**
     * add the new depot
     * @param playerBoard of the player
     */
    @Override
    public void useAbility(PlayerBoard playerBoard) {
        playerBoard.getWarehouse().getDepots().add(new CardDepot(2,0,resource));
        playerBoard.getWarehouse().setDepotsNum(playerBoard.getWarehouse().getDepotsNum()+1);

    }
}
