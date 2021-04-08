package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.gameboard.development.ProductionPower;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DevelopmentCardTest {
    @Test
    @DisplayName("The player doesn't have one resource type required to buy the card")
    public void wrongPlayerRes(){
        Map<Resource, Integer> prodCost = new HashMap<>();
        Map<Resource, Integer> resGain = new HashMap<>();
        ProductionPower power = new ProductionPower(prodCost, resGain, 2); //valori non rilevanti per il test

        Map<Resource, Integer> requirements = new HashMap<>();
        requirements.put(Resource.COINS, 1);
        requirements.put(Resource.STONES, 1);

        DevelopmentCard card = new DevelopmentCard(requirements, Color.GREEN,1, 1, power);

        Map<Resource, Integer> playerRes = new HashMap<>();
        playerRes.put(Resource.STONES, 1);

        assertFalse(card.checkCardRequirements(playerRes));
    }

    @Test
    @DisplayName("The player doesn't have enough resources to buy the card")
    public void notEnoughPlayerRes(){
        Map<Resource, Integer> prodCost = new HashMap<>();
        Map<Resource, Integer> resGain = new HashMap<>();
        ProductionPower power = new ProductionPower(prodCost, resGain, 2); //valori non rilevanti per il test

        Map<Resource, Integer> requirements = new HashMap<>();
        requirements.put(Resource.SERVANTS, 2);
        requirements.put(Resource.STONES, 1);

        DevelopmentCard card = new DevelopmentCard(requirements, Color.GREEN,1, 1, power);

        Map<Resource, Integer> playerRes = new HashMap<>();
        playerRes.put(Resource.STONES, 1);
        playerRes.put(Resource.SERVANTS, 1);

        assertFalse(card.checkCardRequirements(playerRes));
    }

    @Test
    @DisplayName("The player can buy the card")
    public void enoughPlayerRes(){
        Map<Resource, Integer> prodCost = new HashMap<>();
        Map<Resource, Integer> resGain = new HashMap<>();
        ProductionPower power = new ProductionPower(prodCost, resGain, 2); //valori non rilevanti per il test

        Map<Resource, Integer> requirements = new HashMap<>();
        requirements.put(Resource.SHIELDS, 1);
        requirements.put(Resource.SERVANTS, 2);
        requirements.put(Resource.COINS, 1);

        DevelopmentCard card = new DevelopmentCard(requirements, Color.GREEN,1, 1, power);

        Map<Resource, Integer> playerRes = new HashMap<>();
        playerRes.put(Resource.SHIELDS, 1);
        playerRes.put(Resource.SERVANTS, 2);
        playerRes.put(Resource.COINS, 2);

        assertTrue(card.checkCardRequirements(playerRes));
    }
}
