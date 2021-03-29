package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.Resource;

import java.util.ArrayList;

public class BlueMarble extends Marbles {
    /**
     * @param resources
     * adds SHIELDS when picked
     */
    @Override
    public void drawEffect(ArrayList<Resource> resources) {
        resources.add(Resource.SHIELDS);
    }
}
