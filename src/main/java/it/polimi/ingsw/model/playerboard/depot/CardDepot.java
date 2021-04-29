package it.polimi.ingsw.model.playerboard.depot;

import it.polimi.ingsw.model.Resource;

public class CardDepot extends BaseDepot {
    public CardDepot(int capacity, int occupied, Resource resource) {
        super(capacity, occupied, resource);
    }


    /**
     * takes in a resource and if it is of the right type,
     * increases the amount of the depot by one
     * @param newResource to add
     * @return
     */
    public boolean addResources(Resource newResource) {
        //case capacity=0
        if (getResource() == newResource) {
            //isFull or not
            if (!isFull()) {
                setOccupied(getOccupied() + 1);
                return true;
            }
        }
        return false;
    }
}
