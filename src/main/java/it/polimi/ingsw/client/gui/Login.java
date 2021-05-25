package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Login {

    private ClientView clientView;
    private GUI gui;

    @FXML
    private Button button;
    @FXML
    private TextField username;
    @FXML
    private Label invalidUsername;

    public void userLogin(){
        String user = username.getText();

        if (user.length() < 4)
            invalidUsername.setText("Username must have at least 4 characters");
        else if (user.length() > 15)
            invalidUsername.setText("Username must have at most 15 characters");
        else if (user.contains(" "))
            invalidUsername.setText("Username can't contains blank spaces");
        else
            invalidUsername.setText("ok");
    }
}
