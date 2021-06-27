package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


public class LeaderCardSelection {

    private static View clientView;
    private static GUI gui;
    private static ArrayList<String> leaderSelection;
    public Label label;
    private ArrayList<String> leaderId;
    private int numOfLeaderSelected;

    public static void setGui(GUI gui){
        LeaderCardSelection.gui = gui;
        LeaderCardSelection.clientView = gui.getClientView();
        LeaderCardSelection.leaderSelection = new ArrayList<>();
    }

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;

    @FXML
    private ImageView leader1;
    @FXML
    private ImageView leader2;
    @FXML
    private ImageView leader3;
    @FXML
    private ImageView leader4;


    public void selectLeaderCards(ArrayList<String> leaderCardID) {
        this.leaderId = leaderCardID;
        this.numOfLeaderSelected =0;
        setLabel("Choose the leader cards");

        for(int i=0; i<leaderId.size();i++){
            ImageView leader = new ImageView();
            Image leaderImage = new Image("/graphics/leaderCards/" + leaderId.get(i) + ".png");
            leader.setImage(leaderImage);
            if (i==0){
                leader1.setImage(leaderImage);
            }
            else if (i==1){
                leader2.setImage(leaderImage);
            }
            else if(i==2){
                leader3.setImage(leaderImage);
            }
            else if (i==3){
                leader4.setImage(leaderImage);
            }
        }

    }

    public void setLabel(String message){
        label.setText(message);
    }

    public void select1(){
        String id = leaderId.get(0);
        leaderSelection.add(0,id);
        button1.setDisable(true);
        numOfLeaderSelected++;
        counter();

    }
    public void select2(){
        String id = leaderId.get(1);
        leaderSelection.add(0,id);
        button2.setDisable(true);
        numOfLeaderSelected++;
        counter();
    }
    public void select3(){
        String id = leaderId.get(2);
        leaderSelection.add(0,id);
        button3.setDisable(true);
        numOfLeaderSelected++;
        counter();
    }
    public void select4(){
        String id = leaderId.get(3);
        leaderSelection.add(0,id);
        button4.setDisable(true);
        numOfLeaderSelected++;
        counter();
    }

    private void counter(){
        if (numOfLeaderSelected == 2 ){
            button1.setDisable(true);
            button2.setDisable(true);
            button3.setDisable(true);
            button4.setDisable(true);
            clientView.sendStartingCards(leaderSelection);
        }
    }
}
