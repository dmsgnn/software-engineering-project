package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.actions.leaderAction.DiscardLeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscardLeaderCardTest {

    @Test
    @DisplayName("Test the correct discard of a card")
    public void discardTest(){
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<LeaderCard> leaderCards = game.getActivePlayer().take4cards();
        ArrayList<LeaderCard> temp = new ArrayList<>();
        temp.add(leaderCards.get(0));
        temp.add(leaderCards.get(1));
        game.getActivePlayer().setCardsHand(temp);
        LeaderCard leaderCard1 = game.getActivePlayer().getCardsHand().get(0);
        LeaderCard leaderCard2 = game.getActivePlayer().getCardsHand().get(1);
        DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard(leaderCard1,game.getActivePlayer().getCardsHand(),game.getActivePlayer().getFaithTrack());
        discardLeaderCard.doAction(game.getActivePlayer().getPlayerBoard());
        assertEquals(leaderCard2,game.getActivePlayer().getCardsHand().get(0));
        assertEquals(1,game.getActivePlayer().getCardsHand().size());
    }
}
