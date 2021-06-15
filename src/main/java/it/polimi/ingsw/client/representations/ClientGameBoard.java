package it.polimi.ingsw.client.representations;

import it.polimi.ingsw.model.gameboard.Color;

import java.util.ArrayList;
import java.util.Optional;

public class ClientGameBoard {

    private final ArrayList<ClientPlayerBoard> playerBoards = new ArrayList<>();
    private MarbleColors[][] market;
    private MarbleColors freeMarble;
    private String[][] cards;

    /**
     * creates a playerboard for each player inside the game
     * @param nicknames of the players in the game
     */
    public void addPlayers(ArrayList<String> nicknames){
        nicknames.forEach(nickname -> playerBoards.add(new ClientPlayerBoard(nickname)));
    }

    /**
     * initialize the market
     * @param market first configuration
     * @param freeMarble first free marble
     */
    public void initializeMarket(MarbleColors[][] market, MarbleColors freeMarble) {
        this.market = market;
        this.freeMarble = freeMarble;
    }

    public boolean correctCardId(String id){
        for (String[] card : cards) {
            for (int j = 0; j < cards[0].length; j++) {
                if (card[j].equals(id)) return true;
            }
        }
        return false;
    }

    /**
     * @return number of players
     */
    public int getNumOfPlayers(){
        return playerBoards.size();
    }

    /**
     * initializes the card grid
     * @param cards first configuration
     */
    public void initializeCards(String[][] cards){
        this.cards = cards;
    }

    /**
     * @param nickname player's nickname
     * @return the playerboard of the specified player
     */
    public ClientPlayerBoard getOnePlayerBoard(String nickname){
        int index = 0;
        for(ClientPlayerBoard playerBoard : playerBoards){
            if(playerBoard.getPlayerNickname().equals(nickname)) index = playerBoards.indexOf(playerBoard);
        }
        return playerBoards.get(index);
    }

    /**
     * changes one card inside the card grid
     * @param newId new card id
     * @param color of the card to change
     * @param level column of the card to change
     */
    public void changeGridCard(String newId, Color color, int level){
        cards[3-level][color.ordinal()] = newId;
    }

    /**
     * updates the market after the MarketAction
     * @param newMarbles new values of the row/column that has changed
     * @param newFreeMarble freeMarble new value
     * @param pos row/col that has to change
     * @param rowOrCol true if pos represents a row, false otherwise
     */
    public void updateMarket(ArrayList<MarbleColors> newMarbles, MarbleColors newFreeMarble, int pos, boolean rowOrCol){
        for(int i=0; i<newMarbles.size(); i++){
            if(rowOrCol) market[pos][i] = newMarbles.get(i);
            else market[i][pos] = newMarbles.get(i);
        }
        freeMarble = newFreeMarble;
    }

    public ArrayList<ClientPlayerBoard> getPlayerBoards() {
        return playerBoards;
    }

    public MarbleColors[][] getMarket() {
        return market;
    }

    public MarbleColors getFreeMarble() {
        return freeMarble;
    }

    public String[][] getCards() {
        return cards;
    }

    public int getMarketRowsNum(){
        return market.length;
    }

    public int getMarketColumnsNum(){
        return market[0].length;
    }

    /**
     * counts the number of white marbles in one row
     * @param row selected row
     * @return num of white marbles
     */
    public int getWhiteCountRow(int row){
        int count = 0;
        for(int i = 0; i < getMarketRowsNum(); i++){
            for(int j = 0; j < getMarketColumnsNum(); j++){
                if(i==row && market[i][j]==MarbleColors.WHITE) count++;
            }
        }
        return count;
    }

    /**
     * counts the number of white marbles in one column
     * @param col selected column
     * @return num of white marbles
     */
    public int getWhiteCountColumn(int col){
        int count = 0;
        for(int i = 0; i < getMarketRowsNum(); i++){
            for(int j = 0; j < getMarketColumnsNum(); j++){
                if(j==col && market[i][j]==MarbleColors.WHITE) count++;
            }
        }
        return count;
    }
}
