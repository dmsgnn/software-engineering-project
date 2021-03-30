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

public class CardDepotTest {
    CardDepot cardDepot = null;
    private static final List<Resource> VALUES = Collections.unmodifiableList(Arrays.asList(Resource.values()));
    private static final int SIZE = VALUES.size();
    static Random random = new Random();

    /**
     * Generate a random Resource
     * @return
     */
    public static Resource randomResource(){
        return VALUES.get(random.nextInt(SIZE));
    }



    @Test
    @DisplayName("WrongResourceTest")
    public void addCardResourceTest(){
        Resource resource = randomResource();
        Resource newResource = randomResource() ;
        try {
            cardDepot = new CardDepot(3,1,resource);
        } catch (ZeroCapacityException e) {
            e.printStackTrace();
        }
        while (newResource == cardDepot.getResource()){
            newResource = randomResource();
        }

        try {
            cardDepot.addResources(newResource);
        } catch (ZeroCapacityException e) {
            e.printStackTrace();
        } catch (FullWarehouseException e) {
            e.printStackTrace();
        } catch (WrongResourceException e) {
            e.printStackTrace();
        }
        assertEquals(resource, cardDepot.getResource());
        assertEquals(1,cardDepot.getOccupied());

    }



}
