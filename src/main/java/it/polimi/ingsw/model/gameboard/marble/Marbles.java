package it.polimi.ingsw.model.gameboard.marble;

import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public abstract class  Marbles {
    public void drawEffect(ArrayList<Resource> resources, ArrayList<Resource> exchangeResources){}

    /**
     * method used in the controller to convert marbles to enums
     * @return MarbleColors
     */
    public MarbleColors getColor(){return null;}
}
