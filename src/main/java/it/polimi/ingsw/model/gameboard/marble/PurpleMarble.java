package it.polimi.ingsw.model.gameboard.marble;

import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
/**
 * class that represents the purple marble
 */
public class PurpleMarble extends Marbles {

    /**
     * activates the effect of the marble
     * @param resources adds SERVANTS when picked
     * @param exchangeResources resources to exchange for white marbles, not use here
     */
    @Override
    public void drawEffect(ArrayList<Resource> resources, ArrayList<Resource> exchangeResources){
        resources.add(Resource.SERVANTS);
    }

    @Override
    public MarbleColors getColor() {
        return MarbleColors.PURPLE;
    }
}