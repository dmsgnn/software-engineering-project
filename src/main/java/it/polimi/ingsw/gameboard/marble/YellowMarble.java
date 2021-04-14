package it.polimi.ingsw.gameboard.marble;

import it.polimi.ingsw.Resource;

import java.util.ArrayList;

public class YellowMarble extends Marbles {
    /**
     * @param resources
     * adds COINS when picked
     * @param exchangeResources
     */
    @Override
    public void drawEffect(ArrayList<Resource> resources, ArrayList<Resource> exchangeResources) {
        resources.add(Resource.COINS);
    }
}
