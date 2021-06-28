package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.leadercard.LeaderCard;

import java.util.ArrayList;
import java.util.Random;

public class LeaderDeck {
    private final ArrayList<LeaderCard> deck = new ArrayList<>();

    public LeaderDeck(ArrayList<LeaderCard> deck) {

        Random rand = new Random();
        int randomIndex;
        int b= deck.size();
        for (int i=0; i < b; i++ ){
            randomIndex = rand.nextInt(deck.size());
            this.deck.add(deck.get(randomIndex));
            deck.remove(randomIndex);
        }
    }

    /**
     * removes 4 cards from the deck and gives them to the player
     */
    public ArrayList<LeaderCard> draw4(){
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        for (int i=0; i<4; i++){
            leaderCards.add(deck.remove(0));
        }
        return leaderCards;
    }


}

