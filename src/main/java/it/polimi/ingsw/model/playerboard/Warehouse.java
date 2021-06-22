package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.depot.BaseDepot;
import it.polimi.ingsw.model.playerboard.depot.WarehouseDepot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private ArrayList<BaseDepot> depots;
    private int depotsNum;
    private final int baseDepotsNum;

    public Warehouse() {

        depots = new ArrayList<>();
        depots.add(new WarehouseDepot(1,0,null));
        depots.add(new WarehouseDepot(2,0,null));
        depots.add(new WarehouseDepot(3,0,null));
        depotsNum = depots.size();
        baseDepotsNum = depots.size();
    }

    /**
     *
     * @return true if the warehouse is full
     */
    public boolean isFull(){
        for (BaseDepot depot : this.depots){
            if (!depot.isFull()){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return the amount of resources in the warehouse
     */
    public int resourceCounter(){
        int counter=0;
        for (BaseDepot depot : depots) {
            counter += depot.getOccupied();
        }
        return counter;
    }

    public Map<Resource,Integer> storedResources(){
        Map<Resource,Integer> map = new HashMap<>();
        for (Resource resource: Resource.values()){
            map.put(resource,0);
        }
        for (BaseDepot depot : this.depots) {
            if (depot.getResource()!=null) {
                map.replace(depot.getResource(), depot.getOccupied());
            }
        }
        return map;

    }

    public int getBaseDepotsNum() {return baseDepotsNum;}

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
