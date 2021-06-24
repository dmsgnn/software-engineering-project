package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.singleplayer.FaithToken;
import it.polimi.ingsw.model.singleplayer.LorenzoAI;
import it.polimi.ingsw.model.singleplayer.Token;
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

    @Test
    @DisplayName("check that the draw token activate the effect of the top token of the deck")
    public void drawEffectTest() {
        Game game = new Game();
        Token faith = new FaithToken(game, 2, "T00");
        game.getLorenzo().getTokens().getDeck().add(faith);
        assertEquals(game.getLorenzo().getTrack().getPosition(), 0);
        game.getLorenzo().drawToken();
        assertEquals(game.getLorenzo().getTrack().getPosition(), 2);
    }
}
