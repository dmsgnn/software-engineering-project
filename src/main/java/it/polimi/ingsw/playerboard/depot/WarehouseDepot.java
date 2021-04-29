package it.polimi.ingsw.playerboard.depot;

import it.polimi.ingsw.Resource;

public class WarehouseDepot extends BaseDepot {

    public WarehouseDepot(int capacity, int occupied, Resource resource)  {
        super(capacity, occupied, resource);
    }


    @Override
    public boolean addResources(Resource newResource) {
        int occupied;
        //increase amount
        if (getResource() == newResource) {
            //isFull or not
            if (!isFull()){
                occupied= getOccupied() +1;
                setOccupied(occupied);
                return true;
            }else return false;
        }
        //new resource
        else{

            setResource(newResource);
            setOccupied(1);
            return true;
        }

    }

}
