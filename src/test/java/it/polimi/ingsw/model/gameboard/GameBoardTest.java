package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {
    Game game = new Game();
    @Test
    public void viewCardTest(){

        Gameboard gameboard = new Gameboard(game);
        try {
            assertEquals(gameboard.viewCard(Color.BLUE,3).getColor(),Color.BLUE);
            assertEquals(gameboard.viewCard(Color.BLUE,3).getLevel(),3);
        } catch (NoCardsLeftException | WrongLevelException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buyCardTest(){
        Gameboard gameboard = new Gameboard(game);
        try {
            DevelopmentCard card = gameboard.buyCard(Color.YELLOW, 2);
            assertEquals(Color.YELLOW, card.getColor());
            assertEquals(2, card.getLevel());
        } catch (NoCardsLeftException | WrongLevelException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void viewCardLevel0Test(){
        Gameboard gameboard = new Gameboard(game);
        assertThrows(WrongLevelException.class, () -> gameboard.viewCard(Color.BLUE,0));
    }
    @Test
    public void viewCardLevel4orGreaterTest(){
        Gameboard gameboard = new Gameboard(game);
        assertThrows(WrongLevelException.class, () -> gameboard.viewCard(Color.BLUE,4));
    }
    @Test
    public void buyCardLevel0Test(){
        Gameboard gameboard = new Gameboard(game);
        assertThrows(WrongLevelException.class, () -> gameboard.buyCard(Color.BLUE,0));
    }
    @Test
    public void buyCardLevel4orGreaterTest(){
        Gameboard gameboard = new Gameboard(game);
        assertThrows(WrongLevelException.class, () -> gameboard.viewCard(Color.BLUE,4));
    }

    @Test
    public void emptyColumnTrueTest(){
        Gameboard gameboard = new Gameboard(game);
        for(int i=0; i<gameboard.getCardRows(); i++){
            for(int j=0; j<4; j++){
                try {
                    gameboard.buyCard(Color.BLUE,i+1);
                } catch (NoCardsLeftException | WrongLevelException e) {
                    e.printStackTrace();
                }
            }
        }
        assertTrue(gameboard.isOneColumnEmpty());
    }
    @Test
    public void emptyColumnFalseTest(){
        Gameboard gameboard = new Gameboard(game);

        assertFalse(gameboard.isOneColumnEmpty());
    }

    @Test
    public void removeLowestLevelTest(){
        Gameboard gameboard = new Gameboard(game);
        gameboard.removeLowestLevel(Color.BLUE, 2);
        assertEquals(gameboard.getCardGrid()[2][Color.BLUE.ordinal()].getSize(), 2);
    }
    @Test
    public void removeLowestLevelTestFromDifferentLevels(){
        Gameboard gameboard = new Gameboard(game);
        try {
            gameboard.getCardGrid()[2][Color.BLUE.ordinal()].removeFirst();
            gameboard.getCardGrid()[2][Color.BLUE.ordinal()].removeFirst();
            gameboard.getCardGrid()[2][Color.BLUE.ordinal()].removeFirst();
        } catch (NoCardsLeftException e) {
            e.printStackTrace();
        }
        gameboard.removeLowestLevel(Color.BLUE, 2);
        assertTrue(gameboard.getCardGrid()[2][Color.BLUE.ordinal()].isEmpty());
        assertEquals(gameboard.getCardGrid()[1][Color.BLUE.ordinal()].getSize(), 3);
    }
    @Test
    public void removeLowestLevelTestOneCardLeft(){
        Gameboard gameboard = new Gameboard(game);

        gameboard.removeLowestLevel(Color.BLUE, 11); //removes all BLUE cards but 1

        gameboard.removeLowestLevel(Color.BLUE, 2); //test
        assertTrue(gameboard.getCardGrid()[0][Color.BLUE.ordinal()].isEmpty());

    }

    @Test
    public void InvalidLevelTest(){
        Gameboard gameboard = new Gameboard(game);
        Assertions.assertThrows(WrongLevelException.class, () -> gameboard.buyCard(Color.BLUE, 0));
        Assertions.assertThrows(WrongLevelException.class, () -> gameboard.buyCard(Color.BLUE, 4));


    }
}
