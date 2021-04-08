package it.polimi.ingsw.gameboard.marble;

import it.polimi.ingsw.Resource;

import java.util.ArrayList;

public class PurpleMarble extends Marbles {
    /**
     * @param resources
     * adds SERVANTS when picked
     */
    @Override
    public void drawEffect(ArrayList<Resource> resources){
        resources.add(Resource.SERVANTS);
    }
}