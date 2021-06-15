package it.polimi.ingsw.model.gameboard.marble;

import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class BlueMarble extends Marbles {

    /**
     * activates the effect of the marble
     * @param resources adds SHIELDS when picked
     * @param exchangeResources resources to exchange fort white marbles, not use here
     */
    @Override
    public void drawEffect(ArrayList<Resource> resources, ArrayList<Resource> exchangeResources) {
        resources.add(Resource.SHIELDS);
    }

    @Override
    public MarbleColors getColor() {
        return MarbleColors.BLUE;
    }
}
