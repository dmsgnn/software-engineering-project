package it.polimi.ingsw.singleplayer;

import it.polimi.ingsw.Game;

public class FaithToken extends Token {
    private final Game game;
    private final int faithGain;
    public FaithToken(Game game, int faithGain) {
        this.game = game;
        this.faithGain = faithGain;
    }

    /**
     * increases the position of Lorenzo of a faithGain quantity.
     */
    public void activateEffect(){
        for(int i=0; i<faithGain; i++){
            game.getLorenzo().getTrack().increasePosition();
        }
    }
}
