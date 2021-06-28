package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.leadercard.ability.DiscountAbility;
import it.polimi.ingsw.model.leadercard.ability.ExchangeAbility;
import it.polimi.ingsw.model.leadercard.ability.ProductionAbility;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class AbilityTest {
    private final Game game= new Game();
    private final PlayerBoard playerBoard = new PlayerBoard(new Player("name",1,game),game);
    private static final List<Color> VALUES = Collections.unmodifiableList(Arrays.asList(Color.values()));
    private static final int SIZE = VALUES.size();
    static Random random = new Random();

    @Test
    public void abilityTest(){
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<LeaderCard> leaderCards;
        leaderCards = game.getActivePlayer().take4cards();
        leaderCards.get(0).activateCard(game.getActivePlayer().getPlayerBoard());
        if (leaderCards.get(0).getAbility() instanceof DiscountAbility){
            Resource resource = ((DiscountAbility) leaderCards.get(0).getAbility()).getResource();
            assertEquals(resource,game.getActivePlayer().getPlayerBoard().getLeaderCardBuffs().getActiveDiscountBuff());
        }
        else if (leaderCards.get(0).getAbility() instanceof ExchangeAbility){
            Resource resource = ((ExchangeAbility) leaderCards.get(0).getAbility()).getGain();
            assertEquals(resource,game.getActivePlayer().getPlayerBoard().getLeaderCardBuffs().getActiveExchangeBuff());
        }
        else if (leaderCards.get(0).getAbility() instanceof ProductionAbility){
            Resource resource = ((ProductionAbility) leaderCards.get(0).getAbility()).getCost();
            assertEquals(resource,game.getActivePlayer().getPlayerBoard().getLeaderCardBuffs().getProductionBuff().get(0));
        }
        else assertEquals(4, game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().size());
        leaderCards.get(0).drawAbility();
        assertTrue(true);



    }

}
