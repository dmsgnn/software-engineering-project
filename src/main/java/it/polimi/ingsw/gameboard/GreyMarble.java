package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.Resource;

import java.util.ArrayList;

public class GreyMarble extends Marbles {
    /**
     * @param resources
     * adds STONES when picked
     */
    @Override
    public void drawEffect(ArrayList<Resource> resources) {
        resources.add(Resource.STONES);
    }
}
