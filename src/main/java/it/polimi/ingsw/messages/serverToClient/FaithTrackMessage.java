package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.PingReceiver;

import java.util.ArrayList;
import java.util.Map;

/**
 * this class tells the client that there has been an increase in faith
 */
public class FaithTrackMessage implements ServerMessage{
    private final ArrayList<String> activatedVatican;
    private final Map<String,Integer> position;
    private final boolean report;
    private final int vaticanReportPos;

    public FaithTrackMessage(ArrayList<String> takeReport, Map<String, Integer> position, boolean report, int vaticanReportPos) {
        this.activatedVatican = takeReport;
        this.position = position;
        this.report = report;
        this.vaticanReportPos=vaticanReportPos;
    }


    @Override
    public void handleMessage(ClientView clientView) {
        clientView.faithTrackUpdate(activatedVatican, position, report, vaticanReportPos);

    }

    @Override
    public void handleMessage(PingReceiver pingManager) {

    }
}
