package it.polimi.ingsw.model.gameboard.development;

import it.polimi.ingsw.client.representations.ColorCLI;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;

import java.util.Map;

public class DevelopmentCard {
    private final Map<Resource, Integer> cardRequirements;
    private final Color color;
    private final String id;
    private final int level;
    private final int victoryPoints;
    private final ProductionPower production;


    public DevelopmentCard(Map<Resource, Integer> cardRequirements, Color color, String id, int level, int victoryPoints, ProductionPower production){
        this.cardRequirements=cardRequirements;
        this.color=color;
        this.id = id;
        this.level=level;
        this.victoryPoints=victoryPoints;
        this.production=production;
    }

    public Map<Resource, Integer> getCardRequirements() {
        return cardRequirements;
    }

    public String getId() {
        return id;
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
            if(!(playerResources.containsKey(key) && playerResources.get(key).equals(value))){
                return false;
            }
        }

        return true;
    }

    private String border(){
        return ColorCLI.cardColor(getColor()) + "║";
    }

    public String drawTop(){
        return ColorCLI.cardColor(color) + "╔═════════╗";
    }
    public String drawBottom(){
        return ColorCLI.cardColor(color) + "╚═════════╝";
    }

    public String drawID(){
        return border() + "" + id + "" + border();
    }

    public String drawLevelAndPoints(){
        int length;
        StringBuilder builder = new StringBuilder();
        builder.append(ColorCLI.RESET).append("lv").append(level).append("  vp");
        length=7;
        builder.append(ColorCLI.RESET).append(victoryPoints);
        if(victoryPoints<10) length++;
        else length=length+2;
        if(length<9) for(int i=0; i<9-length; i++) builder.append(" ");
        return border() + "" + builder.toString() + "" + border();
    }

    public String drawRequirements(){
        int length=0;
        StringBuilder builder = new StringBuilder();
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
        return border() + "" + builder.toString() + "" + border();
    }

    public String drawProdCostAndGain(){
        return border() + "" + production.drawProduction() + "" + border();
    }
    /*
      |  D001    |
      | 3    4   |
      |1,2,3,4   |
      |1,2->3,4,8|
    */
}
