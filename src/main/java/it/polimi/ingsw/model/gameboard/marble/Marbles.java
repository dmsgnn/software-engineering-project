package it.polimi.ingsw.model.gameboard.marble;

import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
/**
 * abstract class that represents a marble
 */
public abstract class  Marbles {
    /**
     * activates the effect of the marble
     * @param resources list to change accordingly to the effect of the marble
     * @param exchangeResources used by white marbles
     */
    public void drawEffect(ArrayList<Resource> resources, ArrayList<Resource> exchangeResources){}

    /**
     * method used in the controller to convert marbles to enums
     * @return MarbleColors
     */
    public MarbleColors getColor(){return null;}
}
