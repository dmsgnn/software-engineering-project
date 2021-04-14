package it.polimi.ingsw.gameboard.marble;

import it.polimi.ingsw.Resource;

import java.util.ArrayList;

public class BlueMarble extends Marbles {
    /**
     * @param resources
     * adds SHIELDS when picked
     * @param exchangeResources
     */
    @Override
    public void drawEffect(ArrayList<Resource> resources, ArrayList<Resource> exchangeResources) {
        resources.add(Resource.SHIELDS);
    }
}
