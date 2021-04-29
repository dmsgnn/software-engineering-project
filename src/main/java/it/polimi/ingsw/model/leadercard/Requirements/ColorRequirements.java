package it.polimi.ingsw.model.leadercard.Requirements;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import java.util.HashMap;
import java.util.Map;

public class ColorRequirements implements Requirements {
    private Map<Color, Integer> colorCardRequirements;
    private Map<Color, Integer> levelCardRequirements;

    public ColorRequirements() {

        this.colorCardRequirements = new HashMap<Color, Integer>();
        this.levelCardRequirements = new HashMap<Color, Integer>();
    }

    @Override
    public boolean checkRequirements(PlayerBoard playerBoard) {
        for (Color color: colorCardRequirements.keySet()){
            if (playerBoard.getColorRequirements().get(levelCardRequirements.get(color)).get(color)<colorCardRequirements.get(color)) return false;

        }

        return true;
    }

    @Override
    public boolean IsColor() {
        return true;
    }

    @Override
    public Map<Resource, Integer> getRequirements(PlayerBoard playerBoard) {
        return null;
    }

    public Map<Color, Integer> getColorCardRequirements() {
        return colorCardRequirements;
    }
    public Map<Color, Integer> getLevelCardRequirements() {
        return levelCardRequirements;
    }

    public void setColorCardRequirements(Map<Color, Integer> cardRequirements) {
        this.colorCardRequirements = cardRequirements;
    }
    public void setLevelCardRequirements(Map<Color, Integer> levelCardRequirements) {
        this.levelCardRequirements = levelCardRequirements;
    }
}
