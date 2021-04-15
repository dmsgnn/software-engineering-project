package it.polimi.ingsw.leadercard.Requirements;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.playerboard.PlayerBoard;
import java.util.HashMap;
import java.util.Map;

public class ResourceRequirements implements Requirements{
    Map<Resource, Integer> cardRequirements;

    public ResourceRequirements() {
        cardRequirements = new HashMap<Resource, Integer>();
    }


    @Override
    public boolean checkRequirements(PlayerBoard playerBoard) {
        for (Resource resource: cardRequirements.keySet()){
            if (cardRequirements.get(resource)>((playerBoard.getWarehouse().storedResources().get(resource))+(playerBoard.getStrongbox().getValue(resource)))) return false;
        }
        return true;
    }

    @Override
    public Map<Resource, Integer> getRequirements(PlayerBoard playerBoard) {
        return cardRequirements;
    }

    @Override
    public boolean IsColor() {
        return false;
    }

    public Map<Resource, Integer> getCardRequirements() {
        return cardRequirements;
    }

    public void setCardRequirements(Map<Resource, Integer> cardRequirements) {
        this.cardRequirements = cardRequirements;
    }
}
