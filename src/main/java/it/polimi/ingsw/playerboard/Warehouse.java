package it.polimi.ingsw.playerboard;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import it.polimi.ingsw.playerboard.depot.BaseDepot;
import it.polimi.ingsw.playerboard.depot.WarehouseDepot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private final ArrayList<BaseDepot> depots;
    private int depotsNum;

    public Warehouse() throws ZeroCapacityException {

        depots = new ArrayList<>();
        depots.add(new WarehouseDepot(1,0,null));
        depots.add(new WarehouseDepot(2,0,null));
        depots.add(new WarehouseDepot(3,0,null));
        depotsNum= depots.size();
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

    public int resourceCounter(){
        int counter=0;
        for (BaseDepot depot : depots) {
            counter += depot.getOccupied();
        }
        return counter;
    }

    //public boolean areResourceDifferent(){}

    public Map<Resource,Integer> storedResources(){
        Map<Resource,Integer> map = new HashMap<>();
        for (BaseDepot depot : this.depots) {
            map.put(depot.getResource(), depot.getOccupied());
        }
        return map;

    }

    public int getDepotsNum() {
        return depotsNum;
    }

    public void setDepotsNum(int depotsNum) {
        this.depotsNum = depotsNum;
    }

    public ArrayList<BaseDepot> getDepots() {
        return depots;
    }
}
