package it.polimi.ingsw.model.leadercard.Requirements;

import it.polimi.ingsw.client.CLI.ColorCLI;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import java.util.HashMap;
import java.util.Map;
/**
 * color requirements of the leadercards
 */
public class ResourceRequirements implements Requirements{
    private Map<Resource, Integer> cardRequirements;

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

    public void setCardRequirements(Map<Resource, Integer> cardRequirements) {
        this.cardRequirements = cardRequirements;
    }

    @Override
    public String drawRequirements() {
        StringBuilder builder = new StringBuilder();
        builder.append("    ");
        int length=4;
        for(Resource rss: cardRequirements.keySet()){
            if(cardRequirements.get(rss)>0) {
                builder.append(ColorCLI.resourceColor(rss)).append(cardRequirements.get(rss)).append(" ");
                length = length + 2;
            }
        }
        if(builder.length()>0) {
            builder.deleteCharAt(builder.length() - 1);
            length--;
        }
        if(length<9) for(int i=0; i<9-length; i++) builder.append(" ");
        return builder.toString();
    }
}
