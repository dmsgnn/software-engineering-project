package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class StrongboxTest {
    private Strongbox strongbox;
    Random rand = new Random();


    @BeforeEach
    public void setUp() throws Exception{
        strongbox = new Strongbox();
    }



    @Test
    @DisplayName("Controllo aggiunta risorse")
    public void addResourceTest(){
        strongbox.addResource(Resource.SHIELDS, 5);
        assertEquals(strongbox.getValue(Resource.SHIELDS), 5, "Valore errato");
    }


    @Test
    @DisplayName("Controllo rimozione risorse")
    public void RemoveResourceTest(){
        int min=0, a, max=0, current=0;
        for (a=0; a< 1001; a++ ) {
            min = rand.nextInt(50000);
            max = rand.nextInt(50000);
            current += max;
            int finalMin = min;
            if ((current -min)>=0)  { current -= min;}
            strongbox.addResource(Resource.SHIELDS, max);
            try {
                strongbox.removeResource(Resource.SHIELDS, min);
            } catch (InsufficientResourcesException e) {
                assertTrue(true);
            }
            strongbox.addResource(Resource.COINS, max);
            try {
                strongbox.removeResource(Resource.COINS, min);
            } catch (InsufficientResourcesException e) {
                assertTrue(true);
            }
            strongbox.addResource(Resource.SERVANTS, max);
            try {
                strongbox.removeResource(Resource.SERVANTS, min);
            } catch (InsufficientResourcesException e) {
                assertTrue(true);
            }
            strongbox.addResource(Resource.STONES, max);

            //assertThrows(InsufficientResourcesException.class, () -> strongbox.removeResource(Resource.STONES, finalMin));

            try {
                strongbox.removeResource(Resource.STONES, min);
            } catch (InsufficientResourcesException e) {
                assertTrue(true);
            }

            assertEquals(current, strongbox.getValue(Resource.SHIELDS), "valore errato");
            assertEquals(current, strongbox.getValue(Resource.COINS), "valore errato");
            assertEquals(current, strongbox.getValue(Resource.SERVANTS), "valore errato");
            assertEquals(current, strongbox.getValue(Resource.STONES), "valore errato");
        }

    }

}
