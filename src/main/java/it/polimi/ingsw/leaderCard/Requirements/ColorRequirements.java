package it.polimi.ingsw.leaderCard.Requirements;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.gameboard.Color;

import java.util.HashMap;
import java.util.Map;

public class ColorRequirements implements Requirements {
    private Map<Color, Integer> cardRequirements;

    public ColorRequirements() {
        this.cardRequirements = new HashMap<Color, Integer>();
        this.cardRequirements.put(Color.BLUE, 0);
        this.cardRequirements.put(Color.GREEN, 0);
        this.cardRequirements.put(Color.PURPLE, 0);
        this.cardRequirements.put(Color.YELLOW, 0);
    }

    @Override
    public boolean checkRequirements() {

        return true;
    }
}
