package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.playerboard.depot.WarehouseDepot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarehouseDepotTest {
    private static final List<Resource> VALUES = Collections.unmodifiableList(Arrays.asList(Resource.values()));
    private static final int SIZE = VALUES.size();
    static Random random = new Random();

    /**
     * Generate a random Resource
     * @return a RANDOM resource from the enum
     */
    public static Resource randomResource(){
        return VALUES.get(random.nextInt(SIZE));
    }


    @Test
    @DisplayName("Insert a new resource in the depot")
    public void changeResourceTest(){
        Resource newResource = randomResource();
        WarehouseDepot warehouseDepot = null;
        warehouseDepot = new WarehouseDepot(3,3,randomResource());

        while (newResource == warehouseDepot.getResource()){
            newResource = randomResource();
        }

        warehouseDepot.addResources(newResource);

        assertEquals(newResource, warehouseDepot.getResource());
        assertEquals(1,warehouseDepot.getOccupied());
    }


    @Test
    @DisplayName("Increase the resource in the depot")
    public void increaseResourceTest() {
        Resource resource = randomResource();
        WarehouseDepot warehouseDepot = null;
        warehouseDepot = new WarehouseDepot(3, 1, resource);

        warehouseDepot.addResources(resource);

        assertEquals(resource,warehouseDepot.getResource());
        assertEquals(2,warehouseDepot.getOccupied());

    }

    @Test
    @DisplayName("remove the resource in the depot")
    public void removeResourceTest(){
        Resource resource = randomResource();
        WarehouseDepot warehouseDepot = null;
            warehouseDepot = new WarehouseDepot(3,3,resource);

        warehouseDepot.removeResource(2);


        assertEquals(resource,warehouseDepot.getResource());
        assertEquals(1,warehouseDepot.getOccupied());
    }
}
