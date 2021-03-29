package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.Resource;

import java.util.ArrayList;

public class YellowMarble extends Marbles {
    /**
     * @param resources
     * adds COINS when picked
     */
    @Override
    public void drawEffect(ArrayList<Resource> resources) {
        resources.add(Resource.COINS);
    }
}
