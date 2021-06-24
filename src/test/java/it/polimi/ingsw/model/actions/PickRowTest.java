package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.actions.normalAction.marketAction.PickColumn;
import it.polimi.ingsw.model.actions.normalAction.marketAction.PickRow;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.exceptions.InvalidIndexException;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.gameboard.marble.Marbles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PickRowTest {

    @Test
    @DisplayName("Test pick row action and valid action")
    public void pickRowTest(){
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<Marbles> marbles1 = game.getBoard().getMarketBoard().getOneRow(0);
        ArrayList<Marbles> marbles2 = new ArrayList<>();
        PickRow pickRow = new PickRow(0,new ArrayList<>(),game.getBoard().getMarketBoard());
        try {
            marbles2 = pickRow.pickRowOrColumn(game.getBoard().getMarketBoard(),0 );
        } catch (InvalidIndexException e) {
            assertTrue(true);
        }
        assertEquals(marbles1,marbles2);
        assertTrue(pickRow.validAction(game.getActivePlayer().getPlayerBoard()));
        assertNotEquals(marbles1,pickRow.getMarbles());
    }

    @Test
    @DisplayName("Test invalid index")
    public void error(){
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        PickRow pickRow = new PickRow(7,new ArrayList<>(),game.getBoard().getMarketBoard());
        try {
            pickRow.doAction(game.getActivePlayer().getPlayerBoard());
        } catch (InvalidActionException | WrongLevelException | NoCardsLeftException e) {
            assertTrue(true);
        }
        assertThrows(InvalidActionException.class, () ->  pickRow.doAction(game.getActivePlayer().getPlayerBoard()));

    }
}
