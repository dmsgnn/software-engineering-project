package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

import java.util.Map;

public class FaithTrackMessage implements ServerMessage{
    private final Map<String, Integer> vaticanPosition;
    private final Map<String,Integer> position;
    private final boolean report;

    public FaithTrackMessage(Map<String, Integer> takeReport, Map<String, Integer> position, boolean report) {
        this.vaticanPosition = takeReport;
        this.position = position;
        this.report = report;
    }


    @Override
    public void handleMessage(ClientView clientView) {
        clientView.faithTrackUpdate(vaticanPosition, position, report);

    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
