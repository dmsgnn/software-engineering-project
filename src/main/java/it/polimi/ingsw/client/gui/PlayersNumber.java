package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PlayersNumber {
    private static ClientView clientView;
    private static GUI gui;

    public static void setGui(GUI gui) {
        PlayersNumber.gui = gui;
        PlayersNumber.clientView = gui.getClientView();
    }

    @FXML
    private Button buttoneOne;
    @FXML
    private Button buttonTwo;
    @FXML
    private Button buttonThree;
    @FXML
    private Button buttonFour;

    public void onePlayer(){
        clientView.sendNumOfPlayers(1);
    }

    public void twoPlayers(){
        clientView.sendNumOfPlayers(2);
        gui.loginDone();
    }

    public void threePlayers(){
        clientView.sendNumOfPlayers(3);
        gui.loginDone();
    }

    public void fourPlayers(){
        clientView.sendNumOfPlayers(4);
        gui.loginDone();

    }
}
