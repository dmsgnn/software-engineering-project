package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.EmptyWarehouseException;
import it.polimi.ingsw.exceptions.FullWarehouseException;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;
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
    @DisplayName("Warehouse capacity =0")
    public void zeroCapacityTest(){
        int a =0;
        try {
            WarehouseDepot warehouseDepot = new WarehouseDepot(a,0,null);
        } catch (ZeroCapacityException e) {
            try {
                WarehouseDepot warehouseDepot = new WarehouseDepot(a++,0,null);
            } catch (ZeroCapacityException zeroCapacityException) {
                zeroCapacityException.printStackTrace();
            }
            e.printStackTrace();
        }


    }


    @Test
    @DisplayName("Insert a new resource in the depot")
    public void changeResourceTest(){
        Resource newResource = randomResource();
        WarehouseDepot warehouseDepot = null;
        try { warehouseDepot = new WarehouseDepot(3,3,randomResource());
        } catch (ZeroCapacityException e) {
            e.printStackTrace();
        }
        while (newResource == warehouseDepot.getResource()){
            newResource = randomResource();
        }

        try {
            warehouseDepot.addResources(newResource);
        } catch (ZeroCapacityException | FullWarehouseException e) {
            e.printStackTrace();
        }

        assertEquals(newResource, warehouseDepot.getResource());
        assertEquals(1,warehouseDepot.getOccupied());
    }


    @Test
    @DisplayName("Increase the resource in the depot")
    public void increaseResourceTest() {
        Resource resource = randomResource();
        WarehouseDepot warehouseDepot = null;
        try {
            warehouseDepot = new WarehouseDepot(3, 1, resource);
        } catch (ZeroCapacityException e) {
            e.printStackTrace();
        }

        try {
            warehouseDepot.addResources(resource);
        } catch (ZeroCapacityException | FullWarehouseException e) {
            e.printStackTrace();
        }

        assertEquals(resource,warehouseDepot.getResource());
        assertEquals(2,warehouseDepot.getOccupied());

    }

    @Test
    @DisplayName("remove the resource in the depot")
    public void removeResourceTest(){
        Resource resource = randomResource();
        WarehouseDepot warehouseDepot = null;

        try {
            warehouseDepot = new WarehouseDepot(3,3,resource);
        } catch (ZeroCapacityException e) {
            e.printStackTrace();
        }

        try {
            warehouseDepot.removeResource(2);
        } catch (EmptyWarehouseException e) {
            e.printStackTrace();
        } catch (InsufficientResourcesException e) {
            e.printStackTrace();
        }


        assertEquals(resource,warehouseDepot.getResource());
        assertEquals(1,warehouseDepot.getOccupied());
    }
}
