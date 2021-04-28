package it.polimi.ingsw.gameboard;
import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.NoCardsLeftException;
import it.polimi.ingsw.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.gameboard.development.DevelopmentCardDeck;
import it.polimi.ingsw.gameboard.development.ProductionPower;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DevelopmentCardDeckTest {
    @Test
    public void isEmptyTrueTest(){
        Map<Resource, Integer> prodCost = new HashMap<>();
        Map<Resource, Integer> resGain = new HashMap<>();
        ProductionPower power = new ProductionPower(prodCost, resGain, 2); //valori non rilevanti per il test

        Map<Resource, Integer> requirements = new HashMap<>();
        requirements.put(Resource.COINS, 1);
        requirements.put(Resource.STONES, 1);

        DevelopmentCard card = new DevelopmentCard(requirements, Color.GREEN,"",1, 1, power);

        ArrayList<DevelopmentCard> list = new ArrayList<>();
        list.add(card);

        DevelopmentCardDeck deck = new DevelopmentCardDeck(list, 1, Color.GREEN);

        try {
            deck.removeFirst();
        } catch (NoCardsLeftException e) {
            e.printStackTrace();
        }
        assertTrue(deck.isEmpty());
    }
    @Test
    public void isEmptyFalseTest(){
        Map<Resource, Integer> prodCost = new HashMap<>();
        Map<Resource, Integer> resGain = new HashMap<>();
        ProductionPower power = new ProductionPower(prodCost, resGain, 2); //valori non rilevanti per il test

        Map<Resource, Integer> requirements = new HashMap<>();
        requirements.put(Resource.COINS, 1);
        requirements.put(Resource.STONES, 1);

        DevelopmentCard card = new DevelopmentCard(requirements, Color.GREEN,"",1, 1, power);

        ArrayList<DevelopmentCard> list = new ArrayList<>();
        list.add(card);

        DevelopmentCardDeck deck = new DevelopmentCardDeck(list, 1, Color.GREEN);
        assertFalse(deck.isEmpty());
    }

    @Test
    public void lookFirstEmptyTest(){
        ArrayList<DevelopmentCard> list = new ArrayList<>();
        DevelopmentCardDeck deck = new DevelopmentCardDeck(list, 1, Color.GREEN);
        try {
            deck.lookFirst();
        } catch (NoCardsLeftException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeFirstEmptyTest(){
        ArrayList<DevelopmentCard> list = new ArrayList<>();
        DevelopmentCardDeck deck = new DevelopmentCardDeck(list, 1, Color.GREEN);
        try {
            deck.removeFirst();
        } catch (NoCardsLeftException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void lookFirstTest(){
        Map<Resource, Integer> prodCost = new HashMap<>();
        Map<Resource, Integer> resGain = new HashMap<>();
        ProductionPower power = new ProductionPower(prodCost, resGain, 2); //valori non rilevanti per il test

        Map<Resource, Integer> requirements = new HashMap<>();
        requirements.put(Resource.COINS, 1);
        requirements.put(Resource.STONES, 1);

        DevelopmentCard card = new DevelopmentCard(requirements, Color.GREEN,"",1, 1, power);

        ArrayList<DevelopmentCard> list = new ArrayList<>();
        list.add(card);

        DevelopmentCardDeck deck = new DevelopmentCardDeck(list, 1, Color.GREEN);

        try {
            assertEquals(deck.lookFirst(), card);
        } catch (NoCardsLeftException e) {
            e.printStackTrace();
        }
        assertFalse(deck.isEmpty());
    }
    @Test
    @DisplayName("controls that removeFirst removes the first card correctly without changing the rest of the list")
    public void removeFirstTest(){
        Map<Resource, Integer> prodCost = new HashMap<>();
        Map<Resource, Integer> resGain = new HashMap<>();
        ProductionPower power = new ProductionPower(prodCost, resGain, 2); //valori non rilevanti per il test

        Map<Resource, Integer> requirements = new HashMap<>();
        requirements.put(Resource.COINS, 1);
        requirements.put(Resource.STONES, 1);

        DevelopmentCard card1 = new DevelopmentCard(requirements, Color.GREEN,"",1, 1, power);

        Map<Resource, Integer> requirements2 = new HashMap<>();
        requirements.put(Resource.COINS, 1);
        requirements.put(Resource.SHIELDS, 1);

        DevelopmentCard card2 = new DevelopmentCard(requirements2, Color.YELLOW,"",1, 1, power);

        Map<Resource, Integer> requirements3 = new HashMap<>();
        requirements.put(Resource.SERVANTS, 3);
        requirements.put(Resource.SHIELDS, 1);

        DevelopmentCard card3 = new DevelopmentCard(requirements3, Color.YELLOW,"",1, 1, power);

        ArrayList<DevelopmentCard> list = new ArrayList<>();
        ArrayList<DevelopmentCard> copy = new ArrayList<>();
        list.add(card1);
        copy.add(card1);
        list.add(card2);
        copy.add(card2);
        list.add(card3);
        copy.add(card3);

        DevelopmentCardDeck deck = new DevelopmentCardDeck(list, 1, Color.GREEN);

        try {
            copy.remove(deck.lookFirst());
            assertEquals(deck.lookFirst(),deck.removeFirst());
            assertTrue(copy.containsAll(deck.getDeck()));
        } catch (NoCardsLeftException e){
            e.printStackTrace();
        }

    }
}
