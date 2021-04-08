package it.polimi.ingsw;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.playerboard.PlayerBoard;

import java.util.ArrayList;

public class Player {
    private Game game;
    private String nickname;
    private int victoryPoints;
    private int turnOrder;
    private PlayerBoard playerBoard;
    private ArrayList<LeaderCard> starting4;
    private ArrayList<LeaderCard> cardsHand;

    public Player(String nickname, int turnOrder, Game game) throws ZeroCapacityException {
        this.game = game;
        this.turnOrder = turnOrder;
        this.nickname = nickname;
        this.victoryPoints = 0;
        this.playerBoard = new PlayerBoard(this, game);
        this.cardsHand = new ArrayList<LeaderCard>();
        this.starting4 = new ArrayList<LeaderCard>();
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * increases the victory points of the player
     * @param amount is the quantity of victory point to add to the player
     */
    public void increaseVictoryPoints (int amount){
        victoryPoints += amount;
    }

    /**
     * checks if the player is the firstPlayer
     * @return boolean true if the player is the firstPlayer
     */
    public boolean isFirst(){
        return turnOrder == 0;
    }

    public void take4cards(){
        game.getBoard().getLeaderDeck().draw4(game.getActivePlayer());

    }
    public void setStarting4(ArrayList<LeaderCard> starting4) {
        this.starting4 = starting4;
    }
    public void pickStartingLeaderCards(ArrayList<LeaderCard> leaderCards) {
        cardsHand.add(leaderCards.get(0));
        cardsHand.add(leaderCards.get(1));
    }

    public void discardLeaderCard(LeaderCard leaderCard){
        for (int i=0; i<cardsHand.size(); i++) {
            if (cardsHand.get(i).equals(leaderCard)) {
                cardsHand.remove(i);
            }
        }
    }

    public void calcVictoryPoints(){
        int counter=0;
        counter +=playerBoard.getWarehouse().resourceCounter();
        for (Resource resource: Resource.values()){
            counter += playerBoard.getStrongbox().getValue(resource);
        }
        increaseVictoryPoints(counter/5);
        increaseVictoryPoints(playerBoard.getFaithTrack().getVictoryPoints());
        for (int i=0; i<playerBoard.getLeaderCards().size();i++){
            increaseVictoryPoints(playerBoard.getLeaderCards().get(i).getVictoryPoints());
        }
        for (int i=0; i<playerBoard.getSlots().size();i++){
            for (int j=0; j<playerBoard.getSlots().get(i).numOfCards();j++){
                increaseVictoryPoints(playerBoard.getSlots().get(i).getCard(j).getVictoryPoints());
            }
        }



    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }
    public int getTurnOrder() {
        return turnOrder;
    }
    public ArrayList<LeaderCard> getStarting4() {
        return starting4;
    }
    public ArrayList<LeaderCard> getCardsHand() {
        return cardsHand;
    }

}
