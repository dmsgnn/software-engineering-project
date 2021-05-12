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

    /**
     * CLI method
     * @return production of the card
     */
    public String drawProduction(){
        StringBuilder builder = new StringBuilder();
        int length=0;
        for(Resource rss: prodCost.keySet()){
            if(prodCost.get(rss)>0){
                builder.append(ColorCLI.resourceColor(rss)).append(prodCost.get(rss)).append(ColorCLI.RESET).append(",");
                length=length+2;
            }
        }
        if(builder.length()>0) {
            builder.deleteCharAt(builder.length() - 1);
            length--;
        }
        builder.append(ColorCLI.RESET).append("»");
        length++;
        for(Resource rss: resourceGain.keySet()){
            if(resourceGain.get(rss)>0){
                builder.append(ColorCLI.resourceColor(rss)).append(resourceGain.get(rss)).append(ColorCLI.RESET).append(",");
                length=length+2;
            }
        }
        if(faithGain>0){
            builder.append(ColorCLI.RED).append(faithGain).append(" ");
            length=length+2;
        }
        if(builder.length()>0) {
            builder.deleteCharAt(builder.length() - 1);
            length--;
        }
        if(length<9) for(int i=0; i<9-length; i++) builder.append(" ");
        return builder.toString();
    }

}
