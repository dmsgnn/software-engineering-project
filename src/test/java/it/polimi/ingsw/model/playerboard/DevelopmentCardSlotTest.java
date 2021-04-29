package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.Gameboard;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DevelopmentCardSlotTest {
    Game game = new Game();
    Gameboard gameboard = new Gameboard(game);
    PlayerBoard playerBoard = new PlayerBoard(new Player("name",1,game),game);
    DevelopmentCardSlot developmentCardSlot = new DevelopmentCardSlot();
    private static final List<Color> VALUES = Collections.unmodifiableList(Arrays.asList(Color.values()));
    private static final int SIZE = VALUES.size();
    static Random random = new Random();
    private DevelopmentCard developmentCard1 = gameboard.buyCard(randomColor(),1);
    private DevelopmentCard developmentCard2 = gameboard.buyCard(randomColor(),2);
    private DevelopmentCard developmentCard3 = gameboard.buyCard(randomColor(),3);

    public DevelopmentCardSlotTest() throws WrongLevelException, NoCardsLeftException {
    }
    @Test
    public void addCardOnTopTest(){

        developmentCardSlot.addCardOnTop(developmentCard1,playerBoard);
        developmentCardSlot.addCardOnTop(developmentCard2,playerBoard);
        developmentCardSlot.addCardOnTop(developmentCard3,playerBoard);

        assertEquals(developmentCard1,developmentCardSlot.getCard(0));
        assertEquals(developmentCard2,developmentCardSlot.getCard(1));
        assertEquals(developmentCard3,developmentCardSlot.getCard(2));
        assertTrue(developmentCardSlot.isFull());
        assertEquals(3,developmentCardSlot.numOfCards());


    }

    @Test
    public void lookTopTest()  {
        developmentCardSlot.addCardOnTop(developmentCard1,playerBoard);
        assertEquals(developmentCard1,developmentCardSlot.lookTop());
        developmentCardSlot.addCardOnTop(developmentCard2,playerBoard);
        assertEquals(developmentCard2,developmentCardSlot.lookTop());
        developmentCardSlot.addCardOnTop(developmentCard3,playerBoard);
        assertEquals(developmentCard3,developmentCardSlot.lookTop());
    }

    @Test
    public void fullTest(){
        assertFalse(developmentCardSlot.isFull());
    }


    /**
     * Generate a random Color
     * @return a RANDOM color from the enum
     */
    public static Color randomColor(){
        return VALUES.get(random.nextInt(SIZE));
    }


}
