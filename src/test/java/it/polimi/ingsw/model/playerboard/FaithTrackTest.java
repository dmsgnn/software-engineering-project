package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.exceptions.FullPlayerException;
import it.polimi.ingsw.model.playerboard.faithTrack.FaithTrack;
import it.polimi.ingsw.model.playerboard.faithTrack.PlayerFaithTrack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    @DisplayName("Test that a user can't exceed the position of 24")
    public void positionExceededTest() throws FullPlayerException {
        Game game = new Game();
        game.addPlayer("paolo");
        game.addPlayer("luca");
        game.setActivePlayer(game.getPlayers(0));
        FaithTrack playerFaithTrack = game.getPlayers(1).getFaithTrack();
        playerFaithTrack.setPosition(23);
        playerFaithTrack.increasePosition();
        assertEquals(24, playerFaithTrack.getPosition());
        playerFaithTrack.increasePosition();
        assertEquals(24, playerFaithTrack.getPosition());
        game.getActivePlayer().getFaithTrack().increaseAllPositions();
        game.getActivePlayer().getFaithTrack().increaseAllPositions();
        game.getActivePlayer().getFaithTrack().increaseAllPositions();
        assertEquals(24, playerFaithTrack.getPosition());
    }

    @Test
    @DisplayName("Test that the increase all position if in solo game increases the position of Lorenzo")
    public void increaseAllSoloGameTest() {
        ArrayList<String> users = new ArrayList<>();
        users.add("George");
        Game game = new Game(users);
        game.setActivePlayer(game.getPlayers(0));
        assertEquals(game.getLorenzo().getTrack().getPosition(), 0);
        game.getActivePlayer().getFaithTrack().increaseAllPositions();
        assertEquals(game.getLorenzo().getTrack().getPosition(), 1);
    }


    @Test
    @DisplayName("Test three cases and checks that in case every position get incremented, the vatican report check is correctly done")
    public void increaseAllPositionTest() throws FullPlayerException {
        Game game = new Game();
        game.addPlayer("First");
        game.addPlayer("Second");
        game.addPlayer("Third");
        game.setActivePlayer(game.getPlayers(0));
        assertEquals(0, game.getNumVaticanReports());
        game.getPlayers(0).getPlayerBoard().getFaithTrack().increaseAllPositions();
        // checks every positions
        assertEquals(0, game.getPlayers(0).getPlayerBoard().getFaithTrack().getPosition());
        assertEquals(1, game.getPlayers(1).getPlayerBoard().getFaithTrack().getPosition());
        assertEquals(1, game.getPlayers(2).getPlayerBoard().getFaithTrack().getPosition());
        // checks every victory points, no changes
        assertEquals(0, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(0, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(0, game.getPlayers(2).getPlayerBoard().getFaithTrack().getVictoryPoints());
        // checks num of vatican report, no changes
        assertEquals(0, game.getNumVaticanReports());

        // position changing
        game.getPlayers(1).getPlayerBoard().getFaithTrack().setPosition(7);
        game.getPlayers(2).getPlayerBoard().getFaithTrack().setPosition(3);
        game.getPlayers(0).getPlayerBoard().getFaithTrack().increaseAllPositions();

        // checks every positions
        assertEquals(0, game.getPlayers(0).getPlayerBoard().getFaithTrack().getPosition());
        assertEquals(8, game.getPlayers(1).getPlayerBoard().getFaithTrack().getPosition());
        assertEquals(4, game.getPlayers(2).getPlayerBoard().getFaithTrack().getPosition());
        // checks every victory points, only player number 1 gained some victory points
        assertEquals(0, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(2, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(0, game.getPlayers(2).getPlayerBoard().getFaithTrack().getVictoryPoints());
        // checks num of vatican report, one vatican reports activated
        assertEquals(1, game.getNumVaticanReports());

        // position changing
        game.getPlayers(1).getPlayerBoard().getFaithTrack().setPosition(15);
        game.getPlayers(2).getPlayerBoard().getFaithTrack().setPosition(11);
        game.getPlayers(0).getPlayerBoard().getFaithTrack().increaseAllPositions();

        // checks every positions
        assertEquals(0, game.getPlayers(0).getPlayerBoard().getFaithTrack().getPosition());
        assertEquals(16, game.getPlayers(1).getPlayerBoard().getFaithTrack().getPosition());
        assertEquals(12, game.getPlayers(2).getPlayerBoard().getFaithTrack().getPosition());
        // checks every victory points, players number 1 and 2 gained some victory points
        assertEquals(0, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(5, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(5, game.getPlayers(2).getPlayerBoard().getFaithTrack().getVictoryPoints());
        // checks num of vatican report, two vatican reports activated
        assertEquals(2, game.getNumVaticanReports());

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
        playerFaithTrack.setPosition(7);
        playerFaithTrack.increasePosition();
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
    @DisplayName("Test the correct activation of vatican reports in single player")
    public void multipleVaticanActivationTest() {
        ArrayList<String> users = new ArrayList<>();
        users.add("George");
        Game game = new Game(users);
        //vatican report called when it shouldn't
        assertEquals(game.getNumVaticanReports(), 0);
        game.getLorenzo().getTrack().vaticanReportActivation();
        assertEquals(game.getNumVaticanReports(), 0);
        // first vatican report - failed
        game.getLorenzo().getTrack().setPosition(7);
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 0);
        game.getLorenzo().getTrack().increasePosition();
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 0);
        // second vatican report - failed
        game.getLorenzo().getTrack().setPosition(15);
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 0);
        game.getLorenzo().getTrack().increasePosition();
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 0);
        // third vatican report - failed
        game.getLorenzo().getTrack().setPosition(23);
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 0);
        game.getLorenzo().getTrack().increasePosition();
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 0);

        game = new Game(users);
        // first vatican report - taken
        game.getLorenzo().getTrack().setPosition(7);
        game.getPlayers(0).getPlayerBoard().getFaithTrack().setPosition(6);
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 0);
        game.getLorenzo().getTrack().increasePosition();
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 2);
        // second vatican report - taken
        game.getLorenzo().getTrack().setPosition(15);
        game.getPlayers(0).getPlayerBoard().getFaithTrack().setPosition(14);
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 2);
        game.getLorenzo().getTrack().increasePosition();
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 5);
        // third vatican report - taken
        game.getLorenzo().getTrack().setPosition(23);
        game.getPlayers(0).getPlayerBoard().getFaithTrack().setPosition(22);
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 5);
        game.getLorenzo().getTrack().increasePosition();
        assertEquals(game.getPlayers(0).getFaithTrack().getVictoryPoints(), 9);

        assertEquals(game.getLorenzo().getTrack().getPosition(), 24);
        game.getLorenzo().getTrack().increasePosition();
        assertEquals(game.getLorenzo().getTrack().getPosition(), 24);

    }

    @Test
    @DisplayName("Test the correct assignment of points depending by the player's position and check that every vatican report can be activated only once")
    public void vaticanReportActivationTest() {
        Game game = new Game();
        assertEquals(0, game.getPlayersNumber());
        try {
            game.addPlayer("Paolo");
        } catch (FullPlayerException e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Gina");
        } catch (FullPlayerException e) {
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
        assertEquals(5, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(0, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
        game.getPlayers(1).getPlayerBoard().getFaithTrack().setPosition(15);
        game.getPlayers(1).getPlayerBoard().getFaithTrack().increasePosition();
        assertEquals(2, game.getNumVaticanReports());
        assertEquals(5, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(0, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
        game.getPlayers(1).getPlayerBoard().getFaithTrack().setPosition(23);
        game.getPlayers(1).getPlayerBoard().getFaithTrack().increasePosition();
        assertEquals(3, game.getNumVaticanReports());
        assertEquals(5, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(8, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
        game.getPlayers(0).getPlayerBoard().getFaithTrack().setPosition(23);
        game.getPlayers(0).getPlayerBoard().getFaithTrack().increasePosition();
        assertEquals(3, game.getNumVaticanReports());
        assertEquals(9, game.getPlayers(0).getPlayerBoard().getFaithTrack().getVictoryPoints());
        assertEquals(8, game.getPlayers(1).getPlayerBoard().getFaithTrack().getVictoryPoints());
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

    @Test
    @DisplayName("Test the correct incrementation of the victory points, depending by position")
    public void victoryPointsPositionTest() {
        Game game = new Game();
        PlayerFaithTrack playerFaithTrack = new PlayerFaithTrack(game);
        assertEquals(0,playerFaithTrack.getPosition());
        for (int i = 0; i < 2; i++) {
            playerFaithTrack.increasePosition();
        }
        assertEquals(0,playerFaithTrack.getVictoryPoints());
        assertEquals(2,playerFaithTrack.getPosition());
        for (int i = 0; i < 1; i++) {
            playerFaithTrack.increasePosition();
        }
        assertEquals(1,playerFaithTrack.getVictoryPoints());
        assertEquals(3,playerFaithTrack.getPosition());
        for (int i = 0; i < 2; i++) {
            playerFaithTrack.increasePosition();
        }
        assertEquals(1,playerFaithTrack.getVictoryPoints());
        for (int i = 0; i < 3; i++) {
            playerFaithTrack.increasePosition();
        }
        assertEquals(2,playerFaithTrack.getVictoryPoints());
        for (int i = 0; i < 3; i++) {
            playerFaithTrack.increasePosition();
        }
        assertEquals(4,playerFaithTrack.getVictoryPoints());
        for (int i = 0; i < 3; i++) {
            playerFaithTrack.increasePosition();
        }
        assertEquals(6,playerFaithTrack.getVictoryPoints());
        for (int i = 0; i < 3; i++) {
            playerFaithTrack.increasePosition();
        }
        assertEquals(9,playerFaithTrack.getVictoryPoints());
        for (int i = 0; i < 3; i++) {
            playerFaithTrack.increasePosition();
        }
        assertEquals(12,playerFaithTrack.getVictoryPoints());
        for (int i = 0; i < 3; i++) {
            playerFaithTrack.increasePosition();
        }
        assertEquals(16,playerFaithTrack.getVictoryPoints());
        for (int i = 0; i < 3; i++) {
            playerFaithTrack.increasePosition();
        }
        assertEquals(20,playerFaithTrack.getVictoryPoints());

        for (int i = 0; i < 25; i++) {
            playerFaithTrack.increasePosition();
        }
        assertEquals(20,playerFaithTrack.getVictoryPoints());
    }
}
