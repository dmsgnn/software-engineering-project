package it.polimi.ingsw.messages.serverToClient;

import it.polimi.ingsw.client.ClientView;

public class DevCardGrid implements ServerMessage{
    private String[][] devCardId;

    public DevCardGrid(String[][] devCardId) {
        this.devCardId = devCardId;
    }

    @Override
    public void handleMessage(ClientView clientView) { }
}
