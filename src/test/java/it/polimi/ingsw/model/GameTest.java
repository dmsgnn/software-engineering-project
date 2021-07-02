package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.FullPlayerException;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.model.leadercard.LeaderCard;
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
        game.setActivePlayer(game.getPlayers(0));
        assertTrue(game.getPlayers(0).isFirst());
        assertEquals(game.getPlayers(0).getNickname(), game.getActivePlayer().getNickname());
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
    @DisplayName("check that the points with the faith tare correct")
    public void victoryPointTest() {
        ArrayList<String> users = new ArrayList<>();
        users.add("First");
        users.add("Second");
        Game game = new Game(users);
        for(int i=0; i<21; i++)
            game.getPlayers(0).getFaithTrack().increasePosition();
        assertEquals(game.getPlayers(0).getVictoryPoints(), 21);
    }

    @Test
    @DisplayName("check that the dev card end return true when a player has bought seven cards")
    public void victoryPointTest2() throws NoCardsLeftException {
        ArrayList<String> users = new ArrayList<>();
        users.add("First");
        users.add("Second");
        Game game = new Game(users);
        DevelopmentCard devCard = game.getBoard().getCardGrid()[0][0].lookFirst();
        int devPoints = devCard.getVictoryPoints();
        game.getPlayers(0).getPlayerBoard().getSlots().get(0).addCardOnTop(devCard, game.getPlayers(0).getPlayerBoard());
        LeaderCard leader = game.getPlayers(0).take4cards().get(0);
        game.getPlayers(0).getPlayerBoard().addLeaderCard(leader);
        int leadPoints = leader.getVictoryPoints();
        int total = 21 + devPoints + leadPoints;
        for(int i=0; i<21; i++)
            game.getPlayers(0).getFaithTrack().increasePosition();
        assertEquals(game.getPlayers(0).getVictoryPoints(), total);
    }

    @Test
    @DisplayName("check that can't be added more than four players")
    public void playerExceptionTest() throws FullPlayerException {
        Game game = new Game();
        game.addPlayer("first");
        game.addPlayer("second");
        game.addPlayer("third");
        game.addPlayer("fourth");
        assertThrows(FullPlayerException.class, () -> game.addPlayer("fifth"));

    }

}
