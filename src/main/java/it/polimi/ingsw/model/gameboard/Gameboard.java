package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCardDeck;
import it.polimi.ingsw.utility.DevCardsParserXML;
import it.polimi.ingsw.utility.LeaderCardsParserXML;
import it.polimi.ingsw.utility.MarketParserXML;

import java.util.ArrayList;

public class Gameboard {
    private final Market marketBoard;
    private final DevelopmentCardDeck[][] cardGrid;
    private final int cardColumns;
    private final int cardRows;
    private final LeaderDeck leaderDeck;

    public Gameboard(Game game){
        marketBoard = new MarketParserXML().marketParser(game);
        leaderDeck = new LeaderDeck(new LeaderCardsParserXML().leaderCardsParser());

        ArrayList<DevelopmentCard> devCards = new DevCardsParserXML().devCardsParser();

        //builds a list containing the decks for the cardGrid
        ArrayList<DevelopmentCardDeck> devCardDeckList = new ArrayList<>();
        for(int level = 1; level <= 3; level++){
            for(Color color: Color.values()){
                int finalLevel = level;
                ArrayList<DevelopmentCard> tempDeck = devCards.stream().filter(x -> x.getColor() == color).filter(x -> x.getLevel() == finalLevel).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
                devCardDeckList.add(new DevelopmentCardDeck(tempDeck, level, color));
            }
        }

        cardColumns = Color.values().length;
        cardRows = devCardDeckList.size()/cardColumns;
        cardGrid = new DevelopmentCardDeck[cardRows][cardColumns];

        int level;
        Color color;
        for (DevelopmentCardDeck developmentCardDeck : devCardDeckList) {
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

    public LeaderDeck getLeaderDeck() {
        return leaderDeck;
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

    /**
     * removes cards from the development card grid with the specified parameters
     * @param color of the cards to remove
     * @param amount number of cards to remove
     */
    public void removeLowestLevel(Color color, int amount){
        int level = 1;
        int removed = 0;
        while(removed<amount){
            try {
                cardGrid[cardRows - level][color.ordinal()].removeFirst();
                removed++;
            } catch (NoCardsLeftException e) {
                level++;
                if(level==4) return;
            }
        }
    }
}
