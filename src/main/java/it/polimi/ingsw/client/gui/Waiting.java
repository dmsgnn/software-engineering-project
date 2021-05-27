package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Waiting {
    private static ClientView clientView;
    private static GUI gui;

    public static void setGui(GUI gui) {
        Waiting.gui = gui;
        Waiting.clientView = gui.getClientView();
    }

    @FXML
    private Label message;


    public void setWaitingMessage(String text){
        message.setText(text);
    }
}
