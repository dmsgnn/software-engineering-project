package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.leadercard.LeaderCard;

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

    public ArrayList<LeaderCard> getDeck() {
        return deck;
    }

    /**
     * removes 4 cards from the deck and gives them to the player
     */
    public ArrayList<LeaderCard> draw4(){
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        for (int i=0; i<4; i++){
            leaderCards.add(deck.remove(i));
        }
        return leaderCards;
    }


}

