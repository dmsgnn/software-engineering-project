package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.exceptions.NoCardsLeftException;
import it.polimi.ingsw.exceptions.WrongLevelException;
import it.polimi.ingsw.gameboard.development.DevelopmentCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {

    @Test
    public void viewCardTest(){
        Gameboard gameboard = new Gameboard();
        try {
            assertEquals(gameboard.viewCard(Color.BLUE,3).getColor(),Color.BLUE);
            assertEquals(gameboard.viewCard(Color.BLUE,3).getLevel(),3);
        } catch (NoCardsLeftException | WrongLevelException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buyCardTest(){
        Gameboard gameboard = new Gameboard();
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
        Gameboard gameboard = new Gameboard();
        try {
            gameboard.viewCard(Color.BLUE,0);
        } catch (NoCardsLeftException | WrongLevelException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void viewCardLevel4orGreaterTest(){
        Gameboard gameboard = new Gameboard();
        try {
            gameboard.viewCard(Color.BLUE,4);
        } catch (NoCardsLeftException | WrongLevelException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void buyCardLevel0Test(){
        Gameboard gameboard = new Gameboard();
        try {
            gameboard.buyCard(Color.BLUE,0);
        } catch (NoCardsLeftException | WrongLevelException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void buyCardLevel4orGreaterTest(){
        Gameboard gameboard = new Gameboard();
        try {
            gameboard.viewCard(Color.BLUE,4);
        } catch (NoCardsLeftException | WrongLevelException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void emptyColumnTrueTest(){
        Gameboard gameboard = new Gameboard();
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
        Gameboard gameboard = new Gameboard();

        assertFalse(gameboard.isOneColumnEmpty());
    }
}
