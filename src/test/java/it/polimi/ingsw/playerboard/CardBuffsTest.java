package it.polimi.ingsw.playerboard;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.DiscountBuffErrorException;
import it.polimi.ingsw.exceptions.ExchangeBuffErrorException;
import it.polimi.ingsw.exceptions.ProductionBuffErrorException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardBuffsTest {

    private CardBuffs cardBuffs = new CardBuffs();
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
    public void addDiscountBuffTest() throws DiscountBuffErrorException {
        Resource resource = randomResource();
        cardBuffs.addDiscountBuff(resource);
        assertEquals(resource, cardBuffs.getDiscountBuff().get(0));
        assertTrue(cardBuffs.isDiscountBuffActive());
    }

    @Test
    public void addProductionBuffTest() throws ProductionBuffErrorException {
        Resource resource = randomResource();
        cardBuffs.addProductionBuff(resource);
        assertEquals(resource, cardBuffs.getProductionBuff().get(0));
        assertTrue(cardBuffs.isExtraProductionAvailable());

    }


    @Test
    public void addExchangeBuffTest() throws ExchangeBuffErrorException {
        Resource resource = randomResource();
        cardBuffs.addExchangeBuff(resource);
        assertEquals(resource, cardBuffs.getExchangeBuff().get(0));
        assertTrue(cardBuffs.isExchangeBuffActive());

    }


}
