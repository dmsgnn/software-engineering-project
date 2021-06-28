package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.Resource;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CardBuffsTest {


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
    public void addDiscountBuffTest() {
        CardBuffs cardBuffs = new CardBuffs();
        Resource resource = randomResource();
        cardBuffs.addDiscountBuff(resource);
        assertEquals(resource, cardBuffs.getDiscountBuff().get(0));
        assertTrue(cardBuffs.isDiscountBuffActive());
    }

    @Test
    public void addProductionBuffTest() {
        CardBuffs cardBuffs = new CardBuffs();
        Resource resource = randomResource();
        cardBuffs.addProductionBuff(resource);
        assertEquals(resource, cardBuffs.getProductionBuff().get(0));
        assertTrue(cardBuffs.isExtraProductionAvailable());

    }


    @Test
    public void addExchangeBuffTest() {
        CardBuffs cardBuffs = new CardBuffs();
        Resource resource = randomResource();
        cardBuffs.addExchangeBuff(resource);
        assertEquals(resource, cardBuffs.getExchangeBuff().get(0));
        assertTrue(cardBuffs.isExchangeBuffActive());

    }

    @Test
    public void extraTest(){
        CardBuffs cardBuffs = new CardBuffs();
        Resource resource = randomResource();
        Resource resource1 = resource;
        while (resource == resource1){
            resource1 = randomResource();
        }
        cardBuffs.addDiscountBuff(resource);
        cardBuffs.addDiscountBuff(resource1);
        assertEquals(2,cardBuffs.getDiscountBuff().size());
        assertEquals(resource1,cardBuffs.getDiscountBuff().get(1));
        assertFalse(cardBuffs.getDiscountBuff().isEmpty());

        cardBuffs.addExchangeBuff(resource);
        cardBuffs.addExchangeBuff(resource1);
        assertEquals(2,cardBuffs.getExchangeBuff().size());
        assertEquals(resource1,cardBuffs.getExchangeBuff().get(1));
        assertFalse(cardBuffs.getExchangeBuff().isEmpty());
        cardBuffs.addProductionBuff(resource);
        cardBuffs.addProductionBuff(resource1);
        assertEquals(2,cardBuffs.getProductionBuff().size());
        assertEquals(resource1,cardBuffs.getProductionBuff().get(1));
        assertFalse(cardBuffs.getProductionBuff().isEmpty());
    }


}
