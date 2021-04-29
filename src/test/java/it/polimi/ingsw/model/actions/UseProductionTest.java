package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.leaderAction.PlayLeaderCard;
import it.polimi.ingsw.model.actions.normalAction.UseProduction;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.development.ProductionPower;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.Requirements.ColorRequirements;
import it.polimi.ingsw.model.leadercard.ability.ProductionAbility;
import it.polimi.ingsw.model.leadercard.ability.StoreAbility;
import it.polimi.ingsw.model.playerboard.DevelopmentCardSlot;
import it.polimi.ingsw.model.playerboard.Strongbox;
import it.polimi.ingsw.model.playerboard.depot.BaseDepot;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UseProductionTest {

   @Test
    public void correctActionTest() {
        Game game = new Game();
        game.setActivePlayer(new Player("Giorgio", 1, game));

       //cost doesn't matter for this test
       ArrayList<LeaderCard> cards = new ArrayList<LeaderCard>();
        LeaderCard card11 = new LeaderCard("1",2, new StoreAbility(), new ColorRequirements(), Resource.SERVANTS);
       LeaderCard card22 = new LeaderCard("2",2, new ProductionAbility(), new ColorRequirements(), Resource.SERVANTS);

       cards.add(0,card11);
       cards.add(1,card22);

       PlayLeaderCard play = new PlayLeaderCard(cards, card11, new HashMap<>(), new HashMap<>(), new HashMap<>());
       try {
           play.doAction(game.getActivePlayer().getPlayerBoard());
       } catch (InvalidActionException e) {
           e.printStackTrace();
       }

       PlayLeaderCard play2 = new PlayLeaderCard(cards , card22, new HashMap<>(), new HashMap<>(), new HashMap<>());
       game.getActivePlayer().setCardsHand(cards);
       try {

           play2.doAction(game.getActivePlayer().getPlayerBoard());
       } catch (InvalidActionException e) {
           e.printStackTrace();
       }

        ArrayList<BaseDepot> depots = game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots();

       depots.get(0).addResources(Resource.COINS);
       depots.get(1).addResources(Resource.STONES);
       depots.get(1).addResources(Resource.STONES);
       depots.get(2).addResources(Resource.SERVANTS);
       depots.get(2).addResources(Resource.SERVANTS);
       depots.get(2).addResources(Resource.SERVANTS);
       //card depot
       depots.get(3).addResources(Resource.SERVANTS);
       depots.get(3).addResources(Resource.SERVANTS);
       for(Resource resource : Resource.values()){
           //strongbox con 4 di ogni risorsa
           game.getActivePlayer().getPlayerBoard().getStrongbox().addResource(resource, 4);
       }

        //create devCards to use as test
        Map<Resource, Integer> prodCost = new HashMap<>();
        prodCost.put(Resource.SERVANTS, 3);
        prodCost.put(Resource.STONES, 2);
        Map<Resource, Integer> prodCost2 = new HashMap<>();
        prodCost2.put(Resource.COINS, 2);
        prodCost2.put(Resource.SHIELDS, 2);
        Map<Resource, Integer> resGain = new HashMap<>();
        resGain.put(Resource.SERVANTS, 1);
        resGain.put(Resource.STONES, 1);
        ProductionPower power = new ProductionPower(prodCost, resGain, 2);
        ProductionPower power2 = new ProductionPower(prodCost2, resGain, 2);
        DevelopmentCard card = new DevelopmentCard(new HashMap<>(), Color.GREEN,"",1, 1, power);
        DevelopmentCard card2 = new DevelopmentCard(new HashMap<>(), Color.GREEN,"",1, 1, power2);

        ArrayList<DevelopmentCardSlot> playerSlot = game.getActivePlayer().getPlayerBoard().getSlots();

        playerSlot.get(0).addCardOnTop(card, game.getActivePlayer().getPlayerBoard());
        playerSlot.get(1).addCardOnTop(card2, game.getActivePlayer().getPlayerBoard());


        Map<Resource, Integer> wareHouse = new HashMap<>();
        wareHouse.put(Resource.COINS, 1);
        wareHouse.put(Resource.STONES, 1);
        wareHouse.put(Resource.SERVANTS, 1);
        Map<Resource, Integer> leaderDepotResources = new HashMap<>();
        leaderDepotResources.put(Resource.SERVANTS, 1);
        Map<Resource, Integer> strongboxResources = new HashMap<>();
        strongboxResources.put(Resource.SERVANTS, 3);
        strongboxResources.put(Resource.SHIELDS, 2);
        strongboxResources.put(Resource.STONES, 1);
        strongboxResources.put(Resource.COINS, 2);

        ArrayList<Resource> boardResources = new ArrayList<>();
        boardResources.add(Resource.SERVANTS);
        boardResources.add(Resource.COINS);
        boardResources.add(Resource.SHIELDS);

        UseProduction production = new UseProduction(new ArrayList<>(Arrays.asList(0,1)), new ArrayList<>(Collections.singletonList(0)),
                new ArrayList<>(Collections.singletonList(Resource.COINS)), boardResources, wareHouse, leaderDepotResources, strongboxResources);


        try {
            production.doAction(game.getActivePlayer().getPlayerBoard());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //controls if the updated resources inside warehouse are correct
        assertEquals(depots.get(0).getOccupied(), 0);
        assertEquals(depots.get(1).getOccupied(), 1);
        assertEquals(depots.get(2).getOccupied(), 2);
        assertEquals(depots.get(3).getOccupied(), 1);

        Map<Resource, Integer> map = new HashMap<>();
        map.put(Resource.SERVANTS, 3);
        map.put(Resource.SHIELDS, 3);
        map.put(Resource.STONES, 5);
        map.put(Resource.COINS, 3);
        Strongbox strongbox = game.getActivePlayer().getPlayerBoard().getStrongbox();
        //controls if the updated resources inside strongbox are correct
        assertEquals(strongbox.getResources(), map);

    }


    @Test
    public void validActionTestWrongPayment(){
        Game game = new Game();

        game.setActivePlayer(new Player("Giorgio", 1, game));


        ArrayList<BaseDepot> depots = game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots();
        depots.get(0).addResources(Resource.COINS);
        depots.get(1).addResources(Resource.STONES);
        depots.get(2).addResources(Resource.SERVANTS);
        for(Resource resource : Resource.values()){
            //strongbox con 2 di ogni risorsa
            game.getActivePlayer().getPlayerBoard().getStrongbox().addResource(resource, 2);
        }
        Map<Resource, Integer> wareHouse = new HashMap<>();
        Map<Resource, Integer> leaderDepotResources = new HashMap<>();
        Map<Resource, Integer> strongboxResources = new HashMap<>();
        strongboxResources.put(Resource.SERVANTS, 0);
        strongboxResources.put(Resource.SHIELDS, 1);
        strongboxResources.put(Resource.COINS, 1);

        ArrayList<Resource> boardResources = new ArrayList<>();
        boardResources.add(Resource.SERVANTS);
        boardResources.add(Resource.COINS);
        boardResources.add(Resource.SHIELDS);

        UseProduction production = new UseProduction(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                                                        boardResources, wareHouse, leaderDepotResources, strongboxResources);

        assertFalse(production.validAction(game.getActivePlayer().getPlayerBoard()));
    }

}
