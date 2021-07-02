package it.polimi.ingsw.model.playerboard.depot;

import it.polimi.ingsw.model.Resource;

/**
 * This class represents the Depot of the activated Leader Card Depot.
 */
public class CardDepot extends BaseDepot {
    private final Resource resource1;
    public CardDepot(int capacity, int occupied, Resource resource) {
        super(capacity, occupied, resource);
        this.resource1 = resource;

    }


    /**
     * takes in a resource and if it is of the right type,
     * increases the amount of the depot by one
     * @param newResource to add
     * @return
     */
    public boolean addResources(Resource newResource) {
        //case capacity=0
        if (correctResource(newResource)) {
            //isFull or not
            if (!isFull()) {
                setOccupied(getOccupied() + 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean correctResource(Resource resource12){
        return  (resource1.equals(resource12));
    }

    @Override
    public void removeResource(int amount) {
        setOccupied(getOccupied()-amount);
    }
}
