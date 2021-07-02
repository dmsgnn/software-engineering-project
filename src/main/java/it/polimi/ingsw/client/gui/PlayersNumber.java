package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * scene that allows the first player to select the number of players to play with
 */
public class PlayersNumber {
    private static View clientView;
    private static GUI gui;

    public static void setGui(GUI gui) {
        PlayersNumber.gui = gui;
        PlayersNumber.clientView = gui.getClientView();
    }

    @FXML
    private Button buttonOne;
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
