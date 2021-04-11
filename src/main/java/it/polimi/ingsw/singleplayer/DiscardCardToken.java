package it.polimi.ingsw.singleplayer;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.gameboard.Color;

public class DiscardCardToken extends Token {
    private final Color color;
    private final Game game;
    private final int discardQuantity;

    public DiscardCardToken(Game game, Color color, int discardQuantity) {
        this.game = game;
        this.color = color;
        this.discardQuantity = discardQuantity;
    }

    /**
     * discards as many development cards as discard quantity.
     * (the level of discarded card have to be lowest possible)
     */
    public void activateEffect(){
        game.getBoard().removeLowestLevel(color, discardQuantity);
    }
}
