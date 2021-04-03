package it.polimi.ingsw.leaderCard.Requirements;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.gameboard.Color;

import java.util.HashMap;
import java.util.Map;

public class ResourceRequirements implements Requirements{
    Map<Resource, Integer> cardRequirements;

    public ResourceRequirements() {
        cardRequirements = new HashMap<Resource, Integer>();
        cardRequirements.put(Resource.COINS, 0);
        cardRequirements.put(Resource.STONES, 0);
        cardRequirements.put(Resource.SHIELDS, 0);
        cardRequirements.put(Resource.SERVANTS, 0);
    }


    @Override
    public boolean checkRequirements() {
        return false;
    }
}
