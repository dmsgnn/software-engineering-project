package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.ZeroCapacityException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private ArrayList<BaseDepot> depots;

    public Warehouse() throws ZeroCapacityException {

        depots.add(new BaseDepot(1,0,null));
        depots.add(new BaseDepot(2,0,null));
        depots.add(new BaseDepot(3,0,null));
    }

    public boolean isFull(){
        boolean value=true;
        for (BaseDepot depot : this.depots){
            if (depot.isFull()){
                if (value==true) value=true;
            }
            else {
                value = false;
            }
        }
        return value;
    }

    //public boolean areResourceDifferent(){}

    public Map<Resource,Integer> storedResources(){
        Map<Resource,Integer> map = new HashMap<>();
        for (BaseDepot depot : this.depots) {
            map.put(depot.getResource(), depot.getOccupied());
        }
        return map;

    }




}
