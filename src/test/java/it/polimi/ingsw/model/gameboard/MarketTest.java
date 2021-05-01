package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.InvalidIndexException;
import it.polimi.ingsw.model.gameboard.marble.Marbles;
import it.polimi.ingsw.utility.MarketParserXML;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarketTest {

    Game game = new Game();
    Market market = new MarketParserXML().marketParser(game);


    @Test
    @DisplayName("Check negative index for pickColumn")
    public void testNegativeColIndex(){

        try {
            market.pickColumn(-1);
        } catch (InvalidIndexException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Check invalid index for pickColumn")
    public void testWrongColumnIndex(){

        try {
            market.pickColumn(market.getColumns());
        } catch (InvalidIndexException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Check negative index for pickRow")
    public void testNegativeRowIndex(){

        try {
            market.pickRow(-1);
        } catch (InvalidIndexException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Check invalid index for pickColumn")
    public void testWrongRowIndex(){

        try {
            market.pickRow(market.getRows());
        } catch (InvalidIndexException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Check correct resource output for getColumn")
    public void testGetColumn(){
        game.setActivePlayer(new Player("Giorgio", 1, game));

        ArrayList<Marbles> tempMarbles = new ArrayList<>();
        ArrayList<Marbles> marbles = null;
        for(int i = 0; i < market.getColumns(); i++) { //testo ogni colonna
            tempMarbles.removeAll(tempMarbles);
            for(int k = 0; k < market.getRows(); k++) { //attivo effetto per ogni elem della colonna
                tempMarbles.add(market.getMarbleGrid()[k][i]);
            }
            try {
                marbles = market.pickColumn(i);
            } catch (InvalidIndexException e){
                e.printStackTrace();
            }
            assertEquals(marbles, tempMarbles);
        }
    }

    @Test
    @DisplayName("Check correct resource output for getRow")
    public void testGetRow(){
        game.setActivePlayer(new Player("Giorgio", 1, game));

        ArrayList<Marbles> tempMarbles = new ArrayList<>();
        ArrayList<Marbles> marbles = null;
        for(int i=0; i < market.getRows(); i++) {
            tempMarbles.removeAll(tempMarbles);
            for(int k = 0; k < market.getColumns(); k++) {
                tempMarbles.add(market.getMarbleGrid()[i][k]);
            }
            try{
                marbles = market.pickRow(i);
            } catch (InvalidIndexException e){
                e.printStackTrace();
            }
            assertEquals(marbles, tempMarbles);
        }
    }

    @Test
    @DisplayName("Check correct market update for getColumn")
    public void testShiftColumn(){

        game.setActivePlayer(new Player("Giorgio", 1, game));

        Marbles[][] grid = new Marbles[market.getRows()][market.getColumns()];
        for(int i=0; i < market.getColumns(); i++) {
            Marbles tempMarble = market.getFreeMarble();
            for (int row = 0; row < market.getRows(); row++) {
                for (int col = 0; col < market.getColumns(); col++) {
                    grid[row][col] = market.getMarbleGrid()[row][col];
                }
            }
            try{
                market.pickColumn(i);
            } catch (InvalidIndexException e){
                e.printStackTrace();
            }

            int k = 0;
            assertEquals(market.getFreeMarble(), grid[k][i]); //controllo che la colonna sia stata correttamente modificata
            for(; k < market.getRows()-1; k++) {
                assertEquals(market.getMarbleGrid()[k][i], grid[k+1][i]);
            }
            assertEquals(market.getMarbleGrid()[k][i], tempMarble);

            for (int row = 0; row < market.getRows(); row++) { //controllo le colonne diverse da quella selezionata
                for (int col = 0; col < market.getColumns(); col++) {
                    if(col != i)
                        assertEquals(market.getMarbleGrid()[row][col], grid[row][col]);
                }
            }
        }
    }
    @Test
    @DisplayName("Check correct market update for getRow")
    public void testShiftRow() {

        game.setActivePlayer(new Player("Giorgio", 1, game));

        Marbles[][] grid = new Marbles[market.getRows()][market.getColumns()];
        for (int i = 0; i < market.getRows(); i++) {
            Marbles tempMarble = market.getFreeMarble();
            for (int row = 0; row < market.getRows(); row++) {
                for (int col = 0; col < market.getColumns(); col++) {
                    grid[row][col] = market.getMarbleGrid()[row][col];
                }
            }
            try{
                market.pickRow(i);
            } catch (InvalidIndexException e){
                e.printStackTrace();
            }

            int k = 0;
            assertEquals(market.getFreeMarble(), grid[i][k]); // controllo che la riga sia stata correttamente modificata
            for(; k < market.getColumns()-1; k++) {
                assertEquals(market.getMarbleGrid()[i][k], grid[i][k+1]);
            }
            assertEquals(market.getMarbleGrid()[i][k], tempMarble);


            for (int row = 0; row < market.getRows(); row++) { //controllo le righe diverse da quella selezionata
                for (int col = 0; col < market.getColumns(); col++) {
                    if(row != i)
                        assertEquals(market.getMarbleGrid()[row][col], grid[row][col]);
                }
            }
        }
    }
}

