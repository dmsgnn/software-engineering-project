package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.Resource;

import java.util.Map;

public class ProductionPower {
    private Map<Resource, Integer> prodCost;
    private Map<Resource, Integer> resourceGain;
    private int faithGain;

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
