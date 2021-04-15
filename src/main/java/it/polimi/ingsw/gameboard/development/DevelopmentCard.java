package it.polimi.ingsw.gameboard.development;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.gameboard.Color;

import java.util.Map;

public class DevelopmentCard {
    private final Map<Resource, Integer> cardRequirements;
    private final Color color;
    private final int level;
    private final int victoryPoints;
    private final ProductionPower production;

    public DevelopmentCard(Map<Resource, Integer> cardRequirements, Color color, int level, int victoryPoints, ProductionPower production){
        this.cardRequirements=cardRequirements;
        this.color=color;
        this.level=level;
        this.victoryPoints=victoryPoints;
        this.production=production;
    }

    public Map<Resource, Integer> getCardRequirements() {
        return cardRequirements;
    }

    public Color getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public ProductionPower getProduction() {
        return production;
    }

    /**
     *
     * @param playerResources total resources owned by the player
     * @return true if the player has selected the right resources to buy the card, false otherwise
     */
    public boolean checkCardRequirements(Map<Resource, Integer> playerResources){

        for (Map.Entry<Resource, Integer> entry : cardRequirements.entrySet()) {
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            if(!(playerResources.containsKey(key) && playerResources.get(key)==value)){
                return false;
            }
        }

        return true;
    }
}
