package it.polimi.ingsw.model.leadercard.Requirements;

import it.polimi.ingsw.client.CLI.ColorCLI;
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
            int counter =0;
            int a = levelCardRequirements.get(color);
            if (a !=0) {
                for (int i = a; i < 4; i++) {
                    counter += playerBoard.getColorRequirements().get(i).get(color);
                }
                if ((levelCardRequirements.get(color) != 0) && (counter < colorCardRequirements.get(color)))
                    return false;
            }

        }

        return true;
    }

    public void setColorCardRequirements(Map<Color, Integer> cardRequirements) {
        this.colorCardRequirements = cardRequirements;
    }
    public void setLevelCardRequirements(Map<Color, Integer> levelCardRequirements) {
        this.levelCardRequirements = levelCardRequirements;
    }

    @Override
    public String drawRequirements() {
        StringBuilder builder = new StringBuilder();
        builder.append("  ");
        int length=2;

        for(Color color: colorCardRequirements.keySet()){
            if(colorCardRequirements.get(color)>0) {
                int level = levelCardRequirements.get(color);
                builder.append(ColorCLI.RESET).append(colorCardRequirements.get(color));
                if(level==1){
                    builder.append(ColorCLI.cardColor(color)).append("■ ");
                    length = length + 3;
                }
                else {
                    builder.append(ColorCLI.cardColor(color)).append("■■ ");
                    length = length + 4;
                }
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
