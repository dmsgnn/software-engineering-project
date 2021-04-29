package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Game;

public class FaithToken extends Token {
    private final String id;
    private final Game game;
    private final int faithGain;

    public FaithToken(Game game, int faithGain, String id) {
        this.id = id;
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
