package it.polimi.ingsw.playerboard;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.playerboard.depot.CardDepot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

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

        cardDepot = new CardDepot(3,1,resource);

        while (newResource == cardDepot.getResource()){
            newResource = randomResource();
        }

        assertFalse(cardDepot.addResources(newResource));
        assertEquals(resource, cardDepot.getResource());
        assertEquals(1,cardDepot.getOccupied());

    }



}
