package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.client.CLI.ColorCLI;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.model.leadercard.Requirements.ColorRequirements;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColorRequirementsTest {
    private final Game game= new Game();
    private final PlayerBoard playerBoard = new PlayerBoard(new Player("name",1,game),game);
    private final ColorRequirements colorRequirements = new ColorRequirements();
    private static final List<Color> VALUES = Collections.unmodifiableList(Arrays.asList(Color.values()));
    private static final int SIZE = VALUES.size();
    static Random random = new Random();

    public ColorRequirementsTest() {}

    /**
     * Generate a random Color
     * @return a RANDOM color from the enum
     */
    public static Color randomColor(){
        return VALUES.get(random.nextInt(SIZE));
    }
    @Test
    public void reqTest() {
        Color color = randomColor();
        int levelCard=0;
        while (levelCard==0){
            levelCard = random.nextInt(3);
        }
        HashMap<Color,Integer> level = new HashMap<>();
        level.put(color,levelCard);
        HashMap<Color,Integer> qua = new HashMap<>();
        qua.put(color,levelCard);
        colorRequirements.setLevelCardRequirements(level);
        colorRequirements.setColorCardRequirements(qua);
        for (int i=0; i<levelCard;i++){
            playerBoard.getSlots().get(i).addCardOnTop(new DevelopmentCard(null,color,"",levelCard,levelCard,null),playerBoard);
        }
        assertTrue(colorRequirements.checkRequirements(playerBoard));
    }

    @Test
    @DisplayName("controls that the string for the CLI is built correctly")
    public void CLIDrawingTest(){
        ColorRequirements requirements = new ColorRequirements();
        Map<Color, Integer> colorRequirements = new HashMap<>();
        colorRequirements.put(Color.BLUE, 2);
        colorRequirements.put(Color.YELLOW, 0);
        Map<Color, Integer> levelCardRequirements = new HashMap<>();
        levelCardRequirements.put(Color.BLUE, 1);
        levelCardRequirements.put(Color.YELLOW, 0);
        requirements.setColorCardRequirements(colorRequirements);
        requirements.setLevelCardRequirements(levelCardRequirements);

        String output = "  " + ColorCLI.RESET + "2" + ColorCLI.CYAN + "■     ";
        assertEquals(output, requirements.drawRequirements());

        colorRequirements.put(Color.BLUE, 1);
        levelCardRequirements.put(Color.BLUE,2);
        requirements.setColorCardRequirements(colorRequirements);
        requirements.setLevelCardRequirements(levelCardRequirements);
        String output2 = "  " + ColorCLI.RESET + "1" + ColorCLI.CYAN + "■■    ";
        assertEquals(output2, requirements.drawRequirements());
    }

}
