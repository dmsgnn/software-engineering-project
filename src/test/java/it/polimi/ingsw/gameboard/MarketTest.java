package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.exceptions.InvalidIndexException;
import it.polimi.ingsw.Resource;
import it.polimi.ingsw.gameboard.marble.Marbles;
import it.polimi.ingsw.utility.MarketParserXML;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarketTest {
    Market market = new MarketParserXML().marketParser();


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
        ArrayList<Resource> tempResources = new ArrayList<>();
        ArrayList<Resource> resources = null;
        for(int i = 0; i < market.getColumns(); i++) { //testo ogni colonna
            tempResources.removeAll(tempResources);
            for(int k = 0; k < market.getRows(); k++) { //attivo effetto per ogni elem della colonna
                market.getMarbleGrid()[k][i].drawEffect(tempResources);
            }
            try {
                resources = market.pickColumn(i);
            } catch (InvalidIndexException e){
                e.printStackTrace();
            }
            assertEquals(resources, tempResources);
        }
    }

    @Test
    @DisplayName("Check correct resource output for getRow")
    public void testGetRow(){
        ArrayList<Resource> tempResources = new ArrayList<>();
        ArrayList<Resource> resources = null;
        for(int i=0; i < market.getRows(); i++) {
            tempResources.removeAll(tempResources);
            for(int k = 0; k < market.getColumns(); k++) {
                market.getMarbleGrid()[i][k].drawEffect(tempResources);
            }
            try{
                resources = market.pickRow(i);
            } catch (InvalidIndexException e){
                e.printStackTrace();
            }
            assertEquals(resources, tempResources);
        }
    }

    @Test
    @DisplayName("Check correct market update for getColumn")
    public void testShiftColumn(){
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

