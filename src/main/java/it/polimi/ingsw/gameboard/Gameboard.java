package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.exceptions.NoCardsLeftException;
import it.polimi.ingsw.exceptions.WrongLevelException;
import it.polimi.ingsw.utility.DevCardsParserXML;
import it.polimi.ingsw.utility.MarketParserXML;

import java.util.ArrayList;

public class Gameboard {
    private Market marketBoard;
    private DevelopmentCardDeck[][] cardGrid;
    private int cardColumns;
    private int cardRows;

    public Gameboard(){
        marketBoard = new MarketParserXML().marketParser();
        ArrayList<DevelopmentCardDeck> deckList = new DevCardsParserXML().devCardsParser();
        cardColumns = Color.values().length;
        cardRows = deckList.size()/cardColumns;
        cardGrid = new DevelopmentCardDeck[cardRows][cardColumns];

        int level;
        Color color;
        for (DevelopmentCardDeck developmentCardDeck : deckList) {
            level = developmentCardDeck.getLevel();
            color = developmentCardDeck.getColor();
            cardGrid[cardRows - level][color.ordinal()] = developmentCardDeck;
        }
    }

    public Market getMarketBoard() {
        return marketBoard;
    }

    public DevelopmentCardDeck[][] getCardGrid() {
        return cardGrid;
    }

    public int getCardColumns() {
        return cardColumns;
    }

    public int getCardRows() {
        return cardRows;
    }

    /**
     *
     * @param color of the deck
     * @param level of the deck
     * @return the first card of the deck with the requested level and color
     * @throws NoCardsLeftException
     * @throws WrongLevelException
     */
    public DevelopmentCard viewCard(Color color, int level) throws NoCardsLeftException, WrongLevelException {
        if(level <= 0 || level > cardRows) throw new WrongLevelException();
        return cardGrid[cardRows - level][color.ordinal()].lookFirst();
    }

    /**
     * removes nad returns the first card of the specified deck
     * @param color of the deck
     * @param level of the deck
     * @return the first card of the deck with the requested level and color
     * @throws NoCardsLeftException
     * @throws WrongLevelException
     */
    public DevelopmentCard buyCard(Color color, int level) throws NoCardsLeftException, WrongLevelException {
        if(level <= 0 || level > cardRows) throw new WrongLevelException();
        return cardGrid[cardRows - level][color.ordinal()].removeFirst();
    }

    /**
     * controls if one column is empty, this method is needed for solo mode
     * @return true if one column of cardGrid is empty
     */
    public boolean isOneColumnEmpty(){

        for(int j = 0; j < cardColumns; j++){
            boolean flag = true;
            for(int i = 0; i < cardRows && flag; i++){
                flag = cardGrid[i][j].isEmpty();
            }
            if(flag) return true;
        }
        return false;
    }
}
