package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.actions.setupAction.AddStartingLeaderCards;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AddStartingLeaderCardsTest {

    @Test
    @DisplayName("Test correct setup of leaderCards")
    public void leaderSetup(){
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<LeaderCard> leaderCards = game.getActivePlayer().take4cards();
        ArrayList<LeaderCard> leaderSelection = new ArrayList<>();
        leaderSelection.add(leaderCards.get(0));
        leaderSelection.add(leaderCards.get(1));
        AddStartingLeaderCards addStartingLeaderCards = new AddStartingLeaderCards(leaderSelection, game.getActivePlayer());
        try {
            addStartingLeaderCards.doAction(game.getActivePlayer().getPlayerBoard());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        assertEquals(leaderSelection.get(0),game.getActivePlayer().getCardsHand().get(0));
        assertEquals(leaderSelection.get(1),game.getActivePlayer().getCardsHand().get(1));
    }

    @Test
    @DisplayName("Test wrong number of leader cards")
    public void WrongSize(){
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<LeaderCard> leaderCards = game.getActivePlayer().take4cards();
        ArrayList<LeaderCard> leaderSelection = new ArrayList<>();
        leaderSelection.add(leaderCards.get(0));
        leaderSelection.add(leaderCards.get(1));
        leaderSelection.add(leaderCards.get(2));
        AddStartingLeaderCards addStartingLeaderCards = new AddStartingLeaderCards(leaderSelection, game.getActivePlayer());
        assertFalse(addStartingLeaderCards.validAction(game.getActivePlayer().getPlayerBoard()));
    }
}
