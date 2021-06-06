package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.playerboard.faithTrack.FaithTrack;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.playerboard.faithTrack.LorenzoFaithTrack;

public class LorenzoAI {
    private final Game game;
    private final FaithTrack track;
    private final TokenDeck tokens;

    public LorenzoAI(Game game) {
        this.game = game;
        this.track = new LorenzoFaithTrack(game);
        this.tokens = new TokenDeck(game);
    }

    public FaithTrack getTrack() {
        return track;
    }

    public TokenDeck getTokens() {
        return tokens;
    }

    /**
     * activates the effect of the Token at the top of the Deck
     */
    public String drawToken(){
        Token token = tokens.pickTop();
        token.activateEffect();
        return token.getClientMessage();
    }

    /**
     * checks if the game is ended, this means that Lorenzo wins
     * @return true if the game is ended
     */
    public boolean checkEndGame(){
        if(track.getPosition() == 24){
            return true;
        }
        return game.getBoard().isOneColumnEmpty();
    }
}

