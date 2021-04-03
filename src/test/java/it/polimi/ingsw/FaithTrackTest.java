package it.polimi.ingsw;

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

    // vaticanReportActivationTest to do

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
