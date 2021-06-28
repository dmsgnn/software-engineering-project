package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest {
    @Test
    @DisplayName("check that the faith track test end return true when a player reach the end of the faith track")
    public void faithEndTest() {
        ArrayList<String> users = new ArrayList<>();
        users.add("First");
        users.add("Second");
        Game game = new Game(users);
        assertFalse(game.endGame());
        assertEquals(game.getPlayers(0).getPlayerBoard().getFaithTrack().getPosition(), 0);
        assertEquals(game.getPlayers(1).getPlayerBoard().getFaithTrack().getPosition(), 0);
        game.setActivePlayer(game.getPlayers(0));
        game.getPlayers(0).getFaithTrack().setPosition(24);
        assertEquals(game.getPlayers(0).getPlayerBoard().getFaithTrack().getPosition(), 24);
        assertEquals(game.getPlayers(1).getPlayerBoard().getFaithTrack().getPosition(), 0);
        assertTrue(game.endGame());
    }

    @Test
    @DisplayName("check that the dev card end return true when a player has bought seven cards")
    public void devCardTest() throws NoCardsLeftException {
        ArrayList<String> users = new ArrayList<>();
        users.add("First");
        users.add("Second");
        Game game = new Game(users);
        assertFalse(game.endGame());
        game.getPlayers(0).getPlayerBoard().getSlots().get(0).addCardOnTop(game.getBoard().getCardGrid()[2][0].lookFirst(), game.getPlayers(0).getPlayerBoard());
        assertFalse(game.endGame());
        game.getPlayers(0).getPlayerBoard().getSlots().get(1).addCardOnTop(game.getBoard().getCardGrid()[2][1].lookFirst(), game.getPlayers(0).getPlayerBoard());
        assertFalse(game.endGame());
        game.getPlayers(0).getPlayerBoard().getSlots().get(2).addCardOnTop(game.getBoard().getCardGrid()[2][2].lookFirst(), game.getPlayers(0).getPlayerBoard());
        assertFalse(game.endGame());
        game.getPlayers(0).getPlayerBoard().getSlots().get(0).addCardOnTop(game.getBoard().getCardGrid()[1][0].lookFirst(), game.getPlayers(0).getPlayerBoard());
        assertFalse(game.endGame());
        game.getPlayers(0).getPlayerBoard().getSlots().get(1).addCardOnTop(game.getBoard().getCardGrid()[1][1].lookFirst(), game.getPlayers(0).getPlayerBoard());
        assertFalse(game.endGame());
        game.getPlayers(0).getPlayerBoard().getSlots().get(2).addCardOnTop(game.getBoard().getCardGrid()[1][2].lookFirst(), game.getPlayers(0).getPlayerBoard());
        assertFalse(game.endGame());
        game.getPlayers(0).getPlayerBoard().getSlots().get(0).addCardOnTop(game.getBoard().getCardGrid()[0][0].lookFirst(), game.getPlayers(0).getPlayerBoard());
        assertTrue(game.endGame());
    }

    @Test
    @DisplayName("check that the dev card end return true when a player has bought seven cards")
    public void victoryPointTest() {
        ArrayList<String> users = new ArrayList<>();
        users.add("First");
        users.add("Second");
        Game game = new Game(users);
        for(int i=0; i<21; i++)
            game.getPlayers(0).getFaithTrack().increasePosition();
        assertEquals(game.getPlayers(0).getVictoryPoints(), 21);
    }
}
