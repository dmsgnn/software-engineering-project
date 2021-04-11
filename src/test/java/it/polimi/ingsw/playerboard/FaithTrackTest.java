package it.polimi.ingsw.playerboard;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.exceptions.FullPlayerException;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import it.polimi.ingsw.playerboard.faithTrack.FaithTrack;
import it.polimi.ingsw.playerboard.faithTrack.PlayerFaithTrack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FaithTrackTest {

    @Test
    @DisplayName("Test the correct incrementation of the position, even in the case of position = 24")
    public void positionTest() {
        Game game = new Game();
        FaithTrack playerFaithTrack = new PlayerFaithTrack(game);
        playerFaithTrack.increasePosition();
        assertEquals(1, playerFaithTrack.getPosition());
        playerFaithTrack.setPosition(23);
        playerFaithTrack.increasePosition();
        assertEquals(24, playerFaithTrack.getPosition());
        playerFaithTrack.increasePosition();
        assertEquals(24, playerFaithTrack.getPosition());
    }

    @Test
    @DisplayName("Test the correct evaluation of the method's condition")
    public void endOfTrackTest() {
        Game game = new Game();
        FaithTrack playerFaithTrack = new PlayerFaithTrack(game);
        assertFalse(playerFaithTrack.endOfTrack());
        playerFaithTrack.setPosition(10);
        assertFalse(playerFaithTrack.endOfTrack());
        playerFaithTrack.setPosition(24);
        assertTrue(playerFaithTrack.endOfTrack());
    }

    @Test
    @DisplayName("Test the correct evaluation of the method's condition and incrementation of the numVaticanReports")
    public void vaticanReportCheckTest() {
        Game game = new Game();
        FaithTrack playerFaithTrack = new PlayerFaithTrack(game);
        assertEquals(0, game.getNumVaticanReports());
        playerFaithTrack.setPosition(4);
        playerFaithTrack.vaticanReportCheck();
        assertEquals(0, game.getNumVaticanReports());
        playerFaithTrack.setPosition(8);
        playerFaithTrack.vaticanReportCheck();
        assertEquals(1, game.getNumVaticanReports());
        playerFaithTrack.setPosition(13);
        playerFaithTrack.vaticanReportCheck();
        assertEquals(1, game.getNumVaticanReports());
        playerFaithTrack.setPosition(16);
        playerFaithTrack.vaticanReportCheck();
        assertEquals(2, game.getNumVaticanReports());
        playerFaithTrack.setPosition(19);
        playerFaithTrack.vaticanReportCheck();
        assertEquals(2, game.getNumVaticanReports());
        playerFaithTrack.setPosition(24);
        playerFaithTrack.vaticanReportCheck();
        assertEquals(3, game.getNumVaticanReports());
    }

    @Test
    @DisplayName("Test the correct assignment of points depending by the player's position and check that every vatican report can be activated only once")
    public void vaticanReportActivationTest() {
        Game game = new Game();
        assertEquals(0, game.getPlayersNumber());
        try {
            game.addPlayer("Paolo");
        } catch (FullPlayerException | ZeroCapacityException e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Gina");
        } catch (FullPlayerException | ZeroCapacityException e) {
            e.printStackTrace();
        }
        assertEquals(2, game.getPlayersNumber());
        for(int i=0; i < game.getPlayersNumber(); i++) {
            assertEquals(0, game.getPlayers(i).getPlayerBoard().getFaithTrack().getPosition());
        }
        game.getPlayers(0).getPlayerBoard().getFaithTrack().setPosition(7);
        game.getPlayers(0).getPlayerBoard().getFaithTrack().increasePosition();
        assertEquals(1, game.getNumVaticanReports());
        assertEquals(2, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(0, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
        game.getPlayers(1).getPlayerBoard().getFaithTrack().setPosition(7);
        game.getPlayers(1).getPlayerBoard().getFaithTrack().increasePosition();
        assertEquals(1, game.getNumVaticanReports());
        assertEquals(2, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(0, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
        game.getPlayers(0).getPlayerBoard().getFaithTrack().setPosition(15);
        game.getPlayers(0).getPlayerBoard().getFaithTrack().increasePosition();
        assertEquals(2, game.getNumVaticanReports());
        assertEquals(4, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(0, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
        game.getPlayers(1).getPlayerBoard().getFaithTrack().setPosition(15);
        game.getPlayers(1).getPlayerBoard().getFaithTrack().increasePosition();
        assertEquals(2, game.getNumVaticanReports());
        assertEquals(4, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(0, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
        game.getPlayers(1).getPlayerBoard().getFaithTrack().setPosition(23);
        game.getPlayers(1).getPlayerBoard().getFaithTrack().increasePosition();
        assertEquals(3, game.getNumVaticanReports());
        assertEquals(4, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(4, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
        game.getPlayers(0).getPlayerBoard().getFaithTrack().setPosition(23);
        game.getPlayers(0).getPlayerBoard().getFaithTrack().increasePosition();
        assertEquals(3, game.getNumVaticanReports());
        assertEquals(4, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(4, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
    }

    @Test
    @DisplayName("Test the correct incrementation of the victory points, even in the case of negative amount")
    public void victoryPointsTest() {
        Game game = new Game();
        PlayerFaithTrack playerFaithTrack = new PlayerFaithTrack(game);
        assertEquals(0, playerFaithTrack.getVictoryPoints());
        playerFaithTrack.increaseVictoryPoints(-4);
        assertEquals(0, playerFaithTrack.getVictoryPoints());
        playerFaithTrack.increaseVictoryPoints(5);
        assertEquals(5, playerFaithTrack.getVictoryPoints());
    }
}
