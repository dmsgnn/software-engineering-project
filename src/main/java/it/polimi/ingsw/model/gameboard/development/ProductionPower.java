package it.polimi.ingsw.model.gameboard.development;

import it.polimi.ingsw.client.representations.ColorCLI;
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

    public String drawProduction(){
        StringBuilder builder = new StringBuilder();
        int length=0;
        for(Resource rss: prodCost.keySet()){
            if(prodCost.get(rss)>0){
                builder.append(ColorCLI.resourceColor(rss)).append(prodCost.get(rss)).append(" ");
                length=length+2;
            }
        }
        builder.append(ColorCLI.RESET).append("->");
        length=length+2;
        for(Resource rss: resourceGain.keySet()){
            if(resourceGain.get(rss)>0){
                builder.append(ColorCLI.resourceColor(rss)).append(resourceGain.get(rss)).append(" ");
                length=length+2;
            }
        }
        if(faithGain>0){
            builder.append(ColorCLI.RED).append(faithGain);
            length++;
        }
        System.out.println(length);
        if(length<10) for(int i=0; i<10-length; i++) builder.append(" ");
        return builder.toString();
    }

}
