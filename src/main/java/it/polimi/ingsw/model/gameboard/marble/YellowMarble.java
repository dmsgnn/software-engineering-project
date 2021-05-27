package it.polimi.ingsw.model.gameboard.marble;

import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.model.Resource;

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

    @Override
    public MarbleColors getColor() {
        return MarbleColors.YELLOW;
    }
}
