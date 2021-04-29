package it.polimi.ingsw.model.gameboard.development;

import it.polimi.ingsw.model.Resource;

import java.util.Map;

public class ProductionPower {
    private final Map<Resource, Integer> prodCost;
    private final Map<Resource, Integer> resourceGain;
    private final int faithGain;

    public ProductionPower(Map<Resource, Integer> prodCost, Map<Resource, Integer> resourceGain, int faithGain){
        this.prodCost=prodCost;
        this.resourceGain=resourceGain;
        this.faithGain=faithGain;
    }

    public Map<Resource, Integer> getProdCost() {
        return prodCost;
    }

    public Map<Resource, Integer> getResourceGain() {
        return resourceGain;
    }

    public int getFaithGain() {
        return faithGain;
    }
}
