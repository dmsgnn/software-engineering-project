package it.polimi.ingsw.gameboard.marble;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.gameboard.marble.Marbles;

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
