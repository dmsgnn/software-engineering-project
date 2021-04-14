package it.polimi.ingsw.gameboard.marble;

import it.polimi.ingsw.Resource;

import java.util.ArrayList;

public class PurpleMarble extends Marbles {
    /**
     * @param resources
     * adds SERVANTS when picked
     * @param exchangeResources
     */
    @Override
    public void drawEffect(ArrayList<Resource> resources, ArrayList<Resource> exchangeResources){
        resources.add(Resource.SERVANTS);
    }
}