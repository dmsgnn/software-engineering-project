package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Map;

import static java.lang.System.exit;

public class Winner {
    private static ClientView clientView;
    private static GUI gui;

    public static void setGui(GUI gui) {
        Winner.gui = gui;
        Winner.clientView = gui.getClientView();
    }

    @FXML
    private ImageView first;
    @FXML
    private ImageView second;
    @FXML
    private ImageView third;
    @FXML
    private ImageView fourth;
    @FXML
    private Label firstName;
    @FXML
    private Label secondName;
    @FXML
    private Label thirdName;
    @FXML
    private Label fourthName;
    @FXML
    private Label firstPoints;
    @FXML
    private Label secondPoints;
    @FXML
    private Label thirdPoints;
    @FXML
    private Label fourthPoints;


    /**
     * sets the nickames of the player in the final scene sorted by their final score
     * @param scores the map of nicknames and scores sorted by scores
     */
    public void multiEnd(Map<String, Integer> scores){
        third.setVisible(false);
        fourth.setVisible(false);
        int i=1;
        System.out.println(scores);
        for(Map.Entry<String, Integer> map: scores.entrySet()){
            if(i==1){
                firstName.setText(map.getKey());
                firstPoints.setText(String.valueOf(map.getValue()));
            }
            if(i==2){
                secondName.setText(map.getKey());
                secondPoints.setText(String.valueOf(map.getValue()));
            }
            if(i==3){
                thirdName.setText(map.getKey());
                thirdPoints.setText(String.valueOf(map.getValue()));
                third.setVisible(true);
            }
            if(i==4){
                fourthName.setText(map.getKey());
                fourthPoints.setText(String.valueOf(map.getValue()));
                fourth.setVisible(true);
            }
            i++;
        }
    }

    public void singleEnd(String nickname, int score, boolean lorenzoWon){
        if(lorenzoWon){
            firstName.setText("Lorenzo");
            secondName.setText(nickname);
            secondPoints.setText(String.valueOf(score));
        }
        else {
            firstName.setText(nickname);
            secondName.setText("Lorenzo");
            firstPoints.setText(String.valueOf(score));
        }
        third.setVisible(false);
        fourth.setVisible(false);
    }

    public void quit(){
        exit(0);
    }

}
