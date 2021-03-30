package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.FullWarehouseException;
import it.polimi.ingsw.exceptions.WrongResourceException;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class WarehouseTest {
    Warehouse warehouse = null;
    private static final List<Resource> VALUES = Collections.unmodifiableList(Arrays.asList(Resource.values()));
    private static final int SIZE = VALUES.size();
    static Random random = new Random();

    /**
     * Generate a random Resource
     * @return a random Resource
     */
    public static Resource randomResource(){
        return VALUES.get(random.nextInt(SIZE));
    }


    @Test
    @DisplayName("WrongCapacityTest")
    public void WarehouseCreationTest() throws ZeroCapacityException {
        warehouse= new Warehouse();
        assertEquals(1,warehouse.getDepots().get(0).getCapacity());
        assertEquals(2,warehouse.getDepots().get(1).getCapacity());
        assertEquals(3,warehouse.getDepots().get(2).getCapacity());

    }



    @Test
    @DisplayName("isFullTest")
    public void isFullTest() throws ZeroCapacityException {
        warehouse = new Warehouse();
        Resource resource = null;
        Resource resource1 = null;
        Resource resource2 = null;
        Resource resource3 = null;
        for (int i=0; i<3;i++) {
            while (!warehouse.getDepots().get(i).isFull()) {
                resource = randomResource();
                try {
                    warehouse.getDepots().get(i).addResources(resource);
                } catch (ZeroCapacityException | FullWarehouseException | WrongResourceException e) {
                    e.printStackTrace();
                }


            }
            if (i==0) resource1 = resource;
            if (i==1) resource2 = resource;
            if (i==2) resource3 = resource;
        }

        assertEquals(resource1,warehouse.getDepots().get(0).getResource());
        assertEquals(resource2,warehouse.getDepots().get(1).getResource());
        assertEquals(resource3,warehouse.getDepots().get(2).getResource());
        assertTrue(warehouse.isFull());

    }




}
