package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.actions.normalAction.marketAction.MarketAction;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.gameboard.marble.Marbles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MarketActionTest {
    public class MarketTest extends MarketAction{


        public MarketTest(int index, ArrayList<Marbles> marbles, Market market) {
            super(index, marbles, market);
        }
    }

    @Test
    @DisplayName("test market action ")
    public void marketAction(){
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<Marbles> marbles = new ArrayList<>();
        ArrayList<Marbles> marbles1 = new ArrayList<>();
        ArrayList<Marbles> marbles2 = new ArrayList<>();
        MarketAction marketAction = new MarketTest(0, marbles,game.getBoard().getMarketBoard());
        marbles1 = marketAction.getMarbles();
        try {
            marketAction.doAction(game.getActivePlayer().getPlayerBoard());
        } catch (InvalidActionException | WrongLevelException | NoCardsLeftException e) {
            assertTrue(true);
        }
        marbles2 = marketAction.getMarbles();
        assertTrue(marbles.isEmpty());
        assertNotEquals(marbles1, marbles2);

    }

    @Test
    @DisplayName("test wrong index market action ")
    public void marketError(){
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<Marbles> marbles = new ArrayList<>();
        boolean fas = false;
        MarketAction marketAction = new MarketTest(5, marbles,game.getBoard().getMarketBoard());
        try {
            marketAction.doAction(game.getActivePlayer().getPlayerBoard());
        } catch (InvalidActionException | WrongLevelException | NoCardsLeftException e) {
            assertTrue(true);
            fas = true;
        }
        if (fas) fail();
    }

}
