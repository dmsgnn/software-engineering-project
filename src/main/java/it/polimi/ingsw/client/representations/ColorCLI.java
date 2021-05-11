package it.polimi.ingsw.client.representations;

import it.polimi.ingsw.client.MarbleColors;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;

public enum ColorCLI {
    //Color end string, color reset
    RESET("\033[0m"),
    CLEAR("\033[H\033[2J"),
    RED("\033[0;31m"),
    GREEN("\033[38;5;28m"),
    YELLOW("\u001B[33m"),
    BLUE("\033[0;34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    GREY("\u001B[37m");

    private final String code;

    ColorCLI(String s) {
        this.code = s;
    }

    @Override
    public String toString() {
        return code;
    }

    public static String cardColor(Color col){
        if(col == Color.BLUE) return BLUE.toString();
        else if(col == Color.GREEN) return GREEN.toString();
        else if(col == Color.PURPLE) return PURPLE.toString();
        else return YELLOW.toString(); //Color = YELLOW
    }

    public static String resourceColor(Resource rss){
        if(rss==Resource.COINS) return YELLOW.toString();
        else if(rss==Resource.SERVANTS) return PURPLE.toString();
        else if(rss==Resource.SHIELDS) return BLUE.toString();
        else return GREY.toString(); //STONES
    }

    public static String marbleColor(MarbleColors marble){
        if(marble==MarbleColors.BLUE ) return BLUE.toString();
        else if(marble==MarbleColors.RED) return RED.toString();
        else if(marble==MarbleColors.GREY) return GREY.toString();
        else if(marble==MarbleColors.PURPLE) return PURPLE.toString();
        else if(marble==MarbleColors.YELLOW) return YELLOW.toString();
        else return RESET.toString(); //WhiteMarble
    }
}
