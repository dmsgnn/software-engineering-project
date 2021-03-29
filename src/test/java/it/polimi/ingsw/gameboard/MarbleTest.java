package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MarbleTest {
    @Test
    @DisplayName("Test blue marble with empty and not empty ArrayList")
    public void testDrawBlue(){
        ArrayList<Resource> resources = new ArrayList<>();
        Marbles marble = new BlueMarble();
        marble.drawEffect(resources);
        assertEquals(resources.get(0),Resource.SHIELDS);
        marble.drawEffect(resources);
        assertEquals(resources.get(1),Resource.SHIELDS);
    }
    @Test
    @DisplayName("Test grey marble with empty and not empty ArrayList")
    public void testDrawGrey(){
        ArrayList<Resource> resources = new ArrayList<>();
        Marbles marble = new GreyMarble();
        marble.drawEffect(resources);
        assertEquals(resources.get(0),Resource.STONES);
        marble.drawEffect(resources);
        assertEquals(resources.get(1),Resource.STONES);
    }
    @Test
    @DisplayName("Test yellow marble with empty and not empty ArrayList")
    public void testDrawYellow(){
        ArrayList<Resource> resources = new ArrayList<>();
        Marbles marble = new YellowMarble();
        marble.drawEffect(resources);
        assertEquals(resources.get(0),Resource.COINS);
        marble.drawEffect(resources);
        assertEquals(resources.get(1),Resource.COINS);
    }
    @Test
    @DisplayName("Test purple marble with empty and not empty ArrayList")
    public void testDrawPurple(){
        ArrayList<Resource> resources = new ArrayList<>();
        Marbles marble = new PurpleMarble();
        marble.drawEffect(resources);
        assertEquals(resources.get(0),Resource.SERVANTS);
        marble.drawEffect(resources);
        assertEquals(resources.get(1),Resource.SERVANTS);
    }

}
