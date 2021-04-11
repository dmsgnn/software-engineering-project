package it.polimi.ingsw.singleplayer;

import it.polimi.ingsw.Game;

public class ShuffleToken extends Token {
    private final Game game;
    private final int faithGain;
    private final int shuffle;
    public ShuffleToken(Game game, int faithGain, int shuffle) {
        this.game = game;
        this.faithGain = faithGain;
        this.shuffle = shuffle;
    }

    /**
     * increases the position of Lorenzo of a faithGain quantity
     * and shuffles the tokenDeck.
     */
    public void activateEffect(){
        for(int i=0; i<faithGain; i++){
            game.getLorenzo().getTrack().increasePosition();
        }
        for(int i=0; i<shuffle; i++){
            game.getLorenzo().getTokens().shuffle();
        }
    }
}
