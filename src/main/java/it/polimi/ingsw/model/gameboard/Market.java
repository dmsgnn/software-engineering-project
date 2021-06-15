package it.polimi.ingsw.model.gameboard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.exceptions.InvalidIndexException;
import it.polimi.ingsw.model.gameboard.marble.*;

import java.util.ArrayList;
import java.util.Random;

public class Market {
    private final Marbles[][] marbleGrid;
    private Marbles freeMarble;
    private final int rows, columns;

    public Market(Game game, int rows, int columns, int red, int white, int blue, int yellow, int purple, int grey) {
        this.rows = rows;
        this.columns = columns;
        Random rand = new Random();
        marbleGrid = new Marbles[rows][columns];
        ArrayList<Marbles> list = new ArrayList<>();

        for(int i=0; i<red; i++) list.add(new RedMarble(game));
        for(int i=0; i<white; i++) list.add(new WhiteMarble(game));
        for(int i=0; i<blue; i++) list.add(new BlueMarble());
        for(int i=0; i<yellow; i++) list.add(new YellowMarble());
        for(int i=0; i<purple; i++) list.add(new PurpleMarble());
        for(int i=0; i<grey; i++) list.add(new GreyMarble());

        int randomIndex = rand.nextInt(list.size());
        freeMarble = list.get(randomIndex);
        list.remove(randomIndex);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                randomIndex = rand.nextInt(list.size());
                marbleGrid[row][col] = list.get(randomIndex);
                list.remove(randomIndex);
            }
        }
    }

    /**
     * utility methods that returns a row or column of the market grid
     * @param pos number of the column or row
     * @param pickRow true if row, false if column
     * @return list of marbles contained inside the specified row or column
     */
    private ArrayList<Marbles> getRowOrCol(int pos, boolean pickRow){
        ArrayList<Marbles> temp = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if(pickRow && row == pos) {
                    temp.add(marbleGrid[row][col]);
                }
                else if(!pickRow && col == pos) {
                    temp.add(marbleGrid[row][col]);
                }
            }
        }
        return temp;
    }

    /**
     * called to obtain the marbles inside a column
     * @param col column number
     * @return list of marbles contained inside the specified column
     */
    public ArrayList<Marbles> getOneColumn(int col){
        return getRowOrCol(col, false);
    }

    /**
     * called to obtain the marbles inside a row
     * @param row row number
     * @return list of marbles contained inside the specified row
     */
    public ArrayList<Marbles> getOneRow(int row){
        return getRowOrCol(row, true);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Marbles[][] getMarbleGrid() {
        return marbleGrid;
    }

    public Marbles getFreeMarble() {
        return freeMarble;
    }

    /**
     * activates the effects of the marbles and modifies the market after a player chooses a column to get resources from
     * @param col it's the column chosen by the player
     * @return array containing the marbles gained from the market
     */
    public ArrayList<Marbles> pickColumn(int col) throws InvalidIndexException {
        if(col < 0 || col >= this.columns) throw new InvalidIndexException();
        return pickRowOrColumn(col, false);
    }

    /**
     * activates the effects of the marbles and modifies the market after a player chooses a row to get resources from
     * @param row it's the row chosen by the player
     * @return array containing the marbles gained from the market
     */
    public ArrayList<Marbles> pickRow(int row) throws InvalidIndexException{
        if(row < 0 || row >= this.rows) throw new InvalidIndexException();
        else return pickRowOrColumn(row, true);
    }

    /**
     * utility method to avoid code duplication, shifts a row or column
     * @param pos number of the row or column
     * @param pickRow true if row, false if column
     * @return array containing the marbles gained from the market
     */
    private ArrayList<Marbles> pickRowOrColumn(int pos, boolean pickRow){
        ArrayList<Marbles> output = new ArrayList<>();
        ArrayList<Marbles> temp = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if(pickRow && row == pos) {
                    output.add(marbleGrid[row][col]);
                    temp.add(marbleGrid[row][col]);
                }
                else if(!pickRow && col == pos) {
                    output.add(marbleGrid[row][col]);
                    temp.add(marbleGrid[row][col]);
                }
            }
        }
        temp.add(this.freeMarble);

        for (int i=0; i<temp.size()-1; i++){
            if(pickRow)
                marbleGrid[pos][i] = temp.get(i+1);
            else
                marbleGrid[i][pos] = temp.get(i+1);
        }
        this.freeMarble = temp.get(0);
        return output;
    }

    /**
     * controls if the column exists
     * @param col number of the column
     * @return true if it exists, false otherwise
     */
    public boolean validColumn(int col) {
        return col >= 0 && col < this.columns;
    }

    /**
     * controls if the row exists
     * @param row number of the row
     * @return true if it exists, false otherwise
     */
    public boolean validRow(int row) {
        return row >= 0 && row < this.rows;
    }
}

