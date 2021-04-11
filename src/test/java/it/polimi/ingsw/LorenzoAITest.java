package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NoCardsLeftException;
import it.polimi.ingsw.exceptions.WrongLevelException;
import it.polimi.ingsw.gameboard.Color;
import it.polimi.ingsw.singleplayer.LorenzoAI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LorenzoAITest {

    @Test
    @DisplayName("check if the checkEndGame method returns true in desired situations like position=24 or one column empty")
    public void checkEndGameTest() throws WrongLevelException, NoCardsLeftException {
        Game game = new Game();
        LorenzoAI lorenzo = new LorenzoAI(game);
        //position check
        assertEquals(0, lorenzo.getTrack().getPosition());
        assertFalse(lorenzo.checkEndGame());
        lorenzo.getTrack().setPosition(24);
        assertTrue(lorenzo.checkEndGame());
        lorenzo.getTrack().setPosition(22);
        assertFalse(lorenzo.checkEndGame());
        //column check
        for(int i=0; i<4; i++){
            game.getBoard().buyCard(Color.GREEN, 1);
        }
        for(int i=0; i<4; i++){
            game.getBoard().buyCard(Color.GREEN, 2);
        }
        for(int i=0; i<4; i++){
            game.getBoard().buyCard(Color.GREEN, 3);
        }
        assertTrue(lorenzo.checkEndGame());
    }
}
