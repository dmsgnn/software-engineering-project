package it.polimi.ingsw.playerboard;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.leadercard.Requirements.ColorRequirements;
import it.polimi.ingsw.leadercard.ability.DiscountAbility;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerBoardTest {
    private DiscountAbility discountAbility = new DiscountAbility();
    private ColorRequirements colorRequirements = new ColorRequirements();
    private Game game = new Game();
    private PlayerBoard playerBoard = new PlayerBoard(new Player("user", 0, game), game);
    private static final List<Resource> VALUES = Collections.unmodifiableList(Arrays.asList(Resource.values()));
    private static final int SIZE = VALUES.size();
    static Random random = new Random();


    /**
     * Generate a random Resource
     * @return a RANDOM resource from the enum
     */
    public static Resource randomResource(){
        return VALUES.get(random.nextInt(SIZE));
    }


    public PlayerBoardTest() throws ZeroCapacityException {
    }

    @Test
    public void addLeaderCardTest() throws ErrorActivationLeaderCardException {
        LeaderCard  leaderCard= new LeaderCard(1,discountAbility,colorRequirements, randomResource());
        playerBoard.addLeaderCard(leaderCard);
        assertEquals(leaderCard, playerBoard.getLeaderCards().get(0));
    }

    @Test
    public void boardProductionTest() throws EmptyWarehouseException, InsufficientResourcesException, WrongResourceException, FullWarehouseException, ZeroCapacityException {
        Resource resource1 = randomResource();
        Resource resource2 = randomResource();
        Resource resource3 = randomResource();
        playerBoard.getWarehouse().getDepots().get(0).addResources(resource1);
        playerBoard.getWarehouse().getDepots().get(1).addResources(resource2);
        playerBoard.boardProduction(resource1,resource2,resource3);
        assertEquals(1,playerBoard.getStrongbox().getValue(resource3));
        
    }


}
