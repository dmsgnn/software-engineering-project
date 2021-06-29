package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.client.CLI.ColorCLI;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.leadercard.Requirements.ResourceRequirements;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceRequirementsTest {
    private final  Game game = new Game();
    private final PlayerBoard playerBoard = new PlayerBoard(new Player("name,",1,game),game);
    private final ResourceRequirements requirements = new ResourceRequirements();

    //random
    private static final List<Resource> VALUES = Collections.unmodifiableList(Arrays.asList(Resource.values()));
    private static final int SIZE = VALUES.size();
    static Random random = new Random();

    public ResourceRequirementsTest()  {}


    /**
     * Generate a random Resource
     * @return a RANDOM resource from the enum
     */
    public static Resource randomResource(){
        return VALUES.get(random.nextInt(SIZE));
    }

    @Test
    public void reqTest() {
        Resource resource = randomResource();
        int quantity = 0;
        while (quantity==0){
            quantity = random.nextInt(10);
        }
        for (int i=0;i<quantity;i++) {
            if (!playerBoard.getWarehouse().getDepots().get(2).isFull()) playerBoard.getWarehouse().getDepots().get(2).addResources(resource);
            else{
                playerBoard.getStrongbox().addResource(resource,quantity-i);
            }
        }
        int finalQuantity = quantity;
        HashMap<Resource,Integer> req = new HashMap<Resource,Integer>(){{
            put(resource, finalQuantity);
        }};
        requirements.setCardRequirements(req);
        assertTrue(requirements.checkRequirements(playerBoard));
    }

    @Test
    @DisplayName("controls that the string for the CLI is built correctly")
    public void CLIDrawingTest(){
        ResourceRequirements requirements = new ResourceRequirements();
        Map<Resource, Integer> temp = new HashMap<>();
        temp.put(Resource.SHIELDS, 5);
        temp.put(Resource.STONES, 0);
        requirements.setCardRequirements(temp);

        String output = "    " + ColorCLI.CYAN + "5    ";
        assertEquals(output, requirements.drawRequirements());
    }





}
