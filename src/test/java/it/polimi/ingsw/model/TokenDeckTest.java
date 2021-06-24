package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCardDeck;
import it.polimi.ingsw.model.singleplayer.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TokenDeckTest {

    @Test
    @DisplayName("check that the top element of deck is removed and added to usedTokens")
    public void pickTopTest() {
        Game game = new Game();
        LorenzoAI lorenzo = new LorenzoAI(game);
        assertEquals(7, lorenzo.getTokens().getDeck().size());
        assertEquals(0, lorenzo.getTokens().getUsedTokens().size());
        // makes a copy of the original array
        ArrayList<Token> temp = new ArrayList<>(lorenzo.getTokens().getDeck());
        // calls the method
        lorenzo.getTokens().pickTop();
        // checks the new dimensions of the arrays
        assertEquals(6, lorenzo.getTokens().getDeck().size());
        assertEquals(1, lorenzo.getTokens().getUsedTokens().size());
        // checks that the past top token of deck is now contained in usedTokens and is not contained in deck
        assertTrue(lorenzo.getTokens().getUsedTokens().contains(temp.get(6)));
        assertFalse(lorenzo.getTokens().getDeck().contains(temp.get(6)));
        // checks that all the token of the original array, excepted the last top token, are the same
        for(int i=0; i<6; i++){
            assertTrue(lorenzo.getTokens().getDeck().contains(temp.get(i)));
        }
    }

    @Test
    @DisplayName("check that the top element of deck is removed and added to usedTokens")
    public void shuffleTest() {
        Game game = new Game();
        LorenzoAI lorenzo = new LorenzoAI(game);
        assertEquals(7, lorenzo.getTokens().getDeck().size());
        // makes a copy of the original array of tokens
        ArrayList<Token> temp = new ArrayList<>(lorenzo.getTokens().getDeck());
        // pickTop method to making disorder
        lorenzo.getTokens().pickTop();
        lorenzo.getTokens().pickTop();
        lorenzo.getTokens().pickTop();
        // calls the shuffle method
        lorenzo.getTokens().shuffle();
        // checks that new deck contains all the tokens of the original deck
        assertEquals(7, lorenzo.getTokens().getDeck().size());
        for(int i=0; i<7; i++){
            assertTrue(lorenzo.getTokens().getDeck().contains(temp.get(i)));
        }
        // the new deck is in a different order than the original, a real test is not written because
        // some elements could casually be in the same position and test would randomly fail
    }

    @Test
    @DisplayName("check that every token communicates the correct message")
    public void tokensMessageTest() {
        Game game = new Game();
        Token faith = new FaithToken(game, 2, "T00");
        assertEquals("Lorenzo Il Magnifico gained 2 faith points", faith.getClientMessage());
        Token shuffle = new ShuffleToken(game, 2, 1, "T00");
        assertEquals("Lorenzo Il Magnifico gained 2 faith points and shuffled his token deck", shuffle.getClientMessage());
        Token discard = new DiscardCardToken(game, Color.BLUE, 2, "T00");
        assertEquals("Lorenzo Il Magnifico removed 2 BLUE cards", discard.getClientMessage());
    }

    @Test
    @DisplayName("check that every token do the right effect")
    public void tokensEffectTest() {
        // faith token effect test
        Game game = new Game();
        assertEquals(game.getLorenzo().getTrack().getPosition(), 0);
        Token faith = new FaithToken(game, 2, "T00");
        faith.activateEffect();
        assertEquals(game.getLorenzo().getTrack().getPosition(), 2);

        // shuffle token effect test
        game = new Game();
        assertEquals(game.getLorenzo().getTrack().getPosition(), 0);
        Token shuffle = new ShuffleToken(game, 2, 1, "T00");
        shuffle.activateEffect();
        assertEquals(game.getLorenzo().getTrack().getPosition(), 2);

        // discard token effect test
        game = new Game();
        DevelopmentCardDeck[][] grid = game.getBoard().getCardGrid();
        assertEquals(grid[2][3].getColor(), Color.BLUE);
        assertEquals(grid[2][3].getLevel(), 1);
        assertEquals(grid[2][3].getSize(), 4);
        Token discard = new DiscardCardToken(game, Color.BLUE, 2, "T00");
        discard.activateEffect();
        assertEquals(grid[2][3].getColor(), Color.BLUE);
        assertEquals(grid[2][3].getLevel(), 1);
        assertEquals(grid[2][3].getSize(), 2);

    }
}
