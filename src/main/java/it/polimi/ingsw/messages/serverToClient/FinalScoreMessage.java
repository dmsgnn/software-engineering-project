package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

import java.util.Map;

/**
 * this class informs the client that the game is over and the final scores
 */
public class FinalScoreMessage implements ServerMessage{
    private final Map<String,Integer> finalScores;
    boolean lorenzoWin;

    public FinalScoreMessage(Map<String, Integer> finalScores, boolean lorenzoWin) {
        this.finalScores = finalScores;
        this.lorenzoWin = lorenzoWin;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.finalScoresUpdate(finalScores, lorenzoWin);
    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
