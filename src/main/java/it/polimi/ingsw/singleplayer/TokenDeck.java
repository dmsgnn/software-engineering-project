package it.polimi.ingsw.singleplayer;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.utility.TokensParserXML;

import java.util.ArrayList;
import java.util.Collections;

public class TokenDeck {
    private final ArrayList<Token> deck;
    private final ArrayList<Token> usedTokens;

    public TokenDeck(Game game) {
        deck = new TokensParserXML().tokensParser(game);
        usedTokens = new ArrayList<>();
        Collections.shuffle(deck);
    }

    public ArrayList<Token> getDeck() {
        return deck;
    }

    public ArrayList<Token> getUsedTokens() {
        return usedTokens;
    }

    /**
     * adds the top token in the used tokens, then remove it from the deck.
     * @return the top Token of the deck.
     */
    public Token pickTop(){
        usedTokens.add(deck.get(deck.size()-1));
        return deck.remove(deck.size()-1);
    }

    /**
     * moves all the tokens from usedTokens to deck.
     * Then shuffles the token deck in a random way.
     */
    public void shuffle() {
        for(int i= usedTokens.size()-1; i>=0; i--){
            deck.add(usedTokens.get(i));
            usedTokens.remove(i);
        }
        Collections.shuffle(deck);
    }
}
