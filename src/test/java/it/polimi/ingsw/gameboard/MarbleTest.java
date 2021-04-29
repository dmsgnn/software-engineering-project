package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Resource;
import it.polimi.ingsw.gameboard.marble.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class MarbleTest {
    @Test
    @DisplayName("Test blue marble with empty and not empty ArrayList")
    public void testDrawBlue(){
        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Resource> exchangeResources = new ArrayList<>();
        Marbles marble = new BlueMarble();
        marble.drawEffect(resources, exchangeResources);
        assertEquals(resources.get(0),Resource.SHIELDS);
        marble.drawEffect(resources, exchangeResources);
        assertEquals(resources.get(1),Resource.SHIELDS);
    }
    @Test
    @DisplayName("Test grey marble with empty and not empty ArrayList")
    public void testDrawGrey(){
        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Resource> exchangeResources = new ArrayList<>();
        Marbles marble = new GreyMarble();
        marble.drawEffect(resources, exchangeResources);
        assertEquals(resources.get(0),Resource.STONES);
        marble.drawEffect(resources, exchangeResources);
        assertEquals(resources.get(1),Resource.STONES);
    }
    @Test
    @DisplayName("Test yellow marble with empty and not empty ArrayList")
    public void testDrawYellow(){
        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Resource> exchangeResources = new ArrayList<>();
        Marbles marble = new YellowMarble();
        marble.drawEffect(resources, exchangeResources);
        assertEquals(resources.get(0),Resource.COINS);
        marble.drawEffect(resources, exchangeResources);
        assertEquals(resources.get(1),Resource.COINS);
    }
    @Test
    @DisplayName("Test purple marble with empty and not empty ArrayList")
    public void testDrawPurple(){
        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Resource> exchangeResources = new ArrayList<>();
        Marbles marble = new PurpleMarble();
        marble.drawEffect(resources, exchangeResources);
        assertEquals(resources.get(0),Resource.SERVANTS);
        marble.drawEffect(resources, exchangeResources);
        assertEquals(resources.get(1),Resource.SERVANTS);
    }
    @Test
    @DisplayName("Test red marble effect")
    public void testDrawRed(){
        Game game = new Game();

        game.setActivePlayer(new Player("Giorgio", 1, game));

        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Resource> exchangeResources = new ArrayList<>();
        Marbles marble = new RedMarble(game);
        marble.drawEffect(resources, exchangeResources);
        assertEquals(game.getActivePlayer().getFaithTrack().getPosition(),1);
    }

    @Test
    @DisplayName("Test white marble without active buff")
    public void testDrawWhiteWithoutBuff(){
        Game game = new Game();

        game.setActivePlayer(new Player("Giorgio", 1, game));


        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Resource> exchangeResources = new ArrayList<>();
        Marbles marble = new WhiteMarble(game);
        marble.drawEffect(resources, exchangeResources);
        assertTrue(resources.isEmpty());
    }

    @Test
    @DisplayName("Test white marble with active buff")
    public void testDrawWhiteWithBuff(){
        Game game = new Game();

        game.setActivePlayer(new Player("Giorgio", 1, game));


        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Resource> exchangeResources = new ArrayList<>();
        Marbles marble = new WhiteMarble(game);
        game.getActivePlayer().getPlayerBoard().getLeaderCardBuffs().addExchangeBuff(Resource.COINS);
        exchangeResources.add(Resource.COINS);
        marble.drawEffect(resources, exchangeResources);
        assertEquals(resources.get(0),Resource.COINS);
    }
}
