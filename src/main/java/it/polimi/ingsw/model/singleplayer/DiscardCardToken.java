package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.gameboard.Color;

public class DiscardCardToken extends Token {
    private final String id;
    private final Color color;
    private final Game game;
    private final int discardQuantity;

    public DiscardCardToken(Game game, Color color, int discardQuantity, String id) {
        this.id = id;
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
