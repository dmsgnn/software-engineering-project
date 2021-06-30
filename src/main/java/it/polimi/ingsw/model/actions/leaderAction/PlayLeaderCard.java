package it.polimi.ingsw.model.actions.leaderAction;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.Actions;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.playerboard.depot.BaseDepot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayLeaderCard extends Actions {
    private final LeaderCard leaderCard;
    private final ArrayList<LeaderCard> cardsHand;

    public PlayLeaderCard(ArrayList<LeaderCard> cardsHand, LeaderCard leaderCard) {
        this.cardsHand = cardsHand;
        this.leaderCard = leaderCard;


    }

    @Override
    public void doAction(PlayerBoard playerBoard) throws InvalidActionException {
        if (!validAction(playerBoard)) throw new InvalidActionException();
        else{
            playLeaderCard(playerBoard);

        }

    }

    @Override
    public boolean validAction(PlayerBoard playerBoard) {
        boolean check = false;
        for (LeaderCard card : cardsHand) {
            if (card.equals(leaderCard)) {
                check = true;
                break;
            }
        }
        if (!check) return false;
        return leaderCard.getRequirements().checkRequirements(playerBoard);
    }

    /**
     * activates the selected leader card
     */
    public void playLeaderCard(PlayerBoard playerBoard) {
        playerBoard.addLeaderCard(leaderCard);
        leaderCard.activateCard(playerBoard);
        for (int i = 0; i < cardsHand.size(); i++) {
            if (cardsHand.get(i).equals(leaderCard)) {
                cardsHand.remove(i);
                break;
            }
        }
    }
}
