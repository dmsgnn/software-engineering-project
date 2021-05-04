package it.polimi.ingsw.client.representations;

import it.polimi.ingsw.client.MarbleColors;

import java.util.ArrayList;

public class ClientGameBoard {

    private ArrayList<ClientPlayerBoard> playerBoards;
    private MarbleColors[][] market;
    private MarbleColors freeMarble;
    private String[][] cards;

    /**
     * creates a playerboard for each player inside the game
     * @param nicknames of the players in the game
     */
    public void addPlayers(ArrayList<String> nicknames){
        for(String nickname : nicknames){
            playerBoards.add(new ClientPlayerBoard(nickname));
        }
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
     * @param row row of the card to change
     * @param col column of the card to change
     */
    public void changeGridCard(String newId, int row, int col){
        cards[row][col] = newId;
    }

    /**
     * updates the market after the MarketAction
     * @param newMarbles new values of the row/column that has changed
     * @param newFreeMarble freeMarble new value
     * @param pos row/col that has to change
     * @param rowOrCol true if pos represents a row, false otherwise
     */
    public void updateMarket(ArrayList<MarbleColors> newMarbles, MarbleColors newFreeMarble,int pos, boolean rowOrCol){
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
}
