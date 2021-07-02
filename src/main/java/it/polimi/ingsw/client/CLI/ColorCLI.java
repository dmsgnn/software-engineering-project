package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;

/**
 * used by {@link it.polimi.ingsw.client.CLI.CLI} to print colors
 */
public enum ColorCLI {
    //Color end string, color reset
    RESET("\033[0m"),
    CLEAR("\033[H\033[2J"),
    RED("\033[0;31m"),
    GREEN("\033[38;5;28m"),
    YELLOW("\u001B[33m"),
    CYAN("\u001b[36m"),
    PURPLE("\u001B[35m"),
    GREY("\033[90m");

    private final String code;

    ColorCLI(String s) {
        this.code = s;
    }

    @Override
    public String toString() {
        return code;
    }

    /**
     * @param col color of the card
     * @return color code
     */
    public static String cardColor(Color col){
        if(col == Color.BLUE) return CYAN.toString();
        else if(col == Color.GREEN) return GREEN.toString();
        else if(col == Color.PURPLE) return PURPLE.toString();
        else return YELLOW.toString(); //Color = YELLOW
    }

    /**
     * @param rss color of the resource
     * @return color code
     */
    public static String resourceColor(Resource rss){
        if(rss==Resource.COINS) return YELLOW.toString();
        else if(rss==Resource.SERVANTS) return PURPLE.toString();
        else if(rss==Resource.SHIELDS) return CYAN.toString();
        else return GREY.toString(); //STONES
    }

    /**
     * @param marble color of the marble
     * @return color code
     */
    public static String marbleColor(MarbleColors marble){
        if(marble==MarbleColors.BLUE ) return CYAN.toString();
        else if(marble==MarbleColors.RED) return RED.toString();
        else if(marble==MarbleColors.GREY) return GREY.toString();
        else if(marble==MarbleColors.PURPLE) return PURPLE.toString();
        else if(marble==MarbleColors.YELLOW) return YELLOW.toString();
        else return RESET.toString(); //WhiteMarble
    }
}
