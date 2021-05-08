package it.polimi.ingsw.client;

import it.polimi.ingsw.client.representations.ClientGameBoard;
import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.utility.DevCardsParserXML;
import it.polimi.ingsw.utility.LeaderCardsParserXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI implements UserInterface{

    ClientView clientView;
    ClientGameBoard gameboard;
    ArrayList<DevelopmentCard> devCardList = new DevCardsParserXML().devCardsParser();
    ArrayList<LeaderCard> leaderDeck = new LeaderCardsParserXML().leaderCardsParser();

    Scanner scanner = new Scanner(System.in);

    private final Object lock = new Object();

    /**
     * connects the CLI to ClientView
     * @param clientView
     */
    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
        gameboard = clientView.getGameboard();
    }

    /**
     * utility method to find a development card
     * @param id card that I want to find
     * @return the correct card
     */
    public DevelopmentCard findDevCard(String id){
        for (DevelopmentCard card: devCardList){
            if(card.getId().equals(id)) return card;
        }
        return null;
    }

    /**
     * utility method to find a leadercard
     * @param id card that I want to find
     * @return the correct card
     */
    public LeaderCard findLeaderCard(String id){
        for(LeaderCard card : leaderDeck){
            if(card.getId().equals(id)) return card;
        }
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public String login() {
        String nickname;
        boolean done=false;

        do {
            System.out.print("Insert your nickname: ");
            nickname = scanner.nextLine();
            if(nickname.length()<4) System.out.println("Username must have at least 4 characters");
            else if(nickname.length()>15) System.out.println("Username must have at most 15 characters");
            else if(nickname.contains(" ")) System.out.println("Username can't contain blank spaces");
            else done=true;

        } while(!done);


        return nickname;
    }

    @Override
    public void failedLogin() {

    }

    @Override
    public void loser(String nickname) {

    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void winner(String nickname) {
        System.out.println(nickname + " won the game");
    }

    @Override
    public void inGameLobby() {

    }

    @Override
    public void gameFull() {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public void startGame() {

    }

    @Override
    public int playersNumber(int max) {
        String input;
        int value = 0;
        boolean done = false;

        do {
            System.out.print("Choose the number of players, max " + max + ": ");
            input = scanner.nextLine();
            try{
                value = Integer.parseInt(input);
                if(value < 1 || value > max) System.out.println("Invalid number");
                else done=true;
            } catch (NumberFormatException e){
                System.out.println("Not a number!!");
            }
        } while(!done);

        return value;
    }

    @Override
    public ArrayList<String> startingLeaderCardsSelection(ArrayList<String> leaderCardID) {
        ArrayList<String> pickedCards = new ArrayList<>();
        String temp;

        do {
            System.out.print("Choose your starting cards ");
            leaderCardID.forEach(System.out::println);
            System.out.println("Choose a card: ");
            temp = scanner.nextLine();
            if(!leaderCardID.contains(temp)) System.out.println("INVALID ID");
            else{
                pickedCards.add(temp);
                leaderCardID.remove(temp);
            }

        } while(pickedCards.size()<2);
        return pickedCards;
    }

    @Override
    public ArrayList<Resource> startingResources(int amount) {
        ArrayList<Resource> pickedResources = new ArrayList<>();
        String input;

        while(pickedResources.size() < amount){
            System.out.print("Choose your starting resource, amount " + amount + ": ");
            System.out.print("Options: COINS, STONES, SERVANTS, SHIELDS");
            input = scanner.nextLine().toUpperCase();
            try {
                Resource resource = Resource.valueOf(input);
                pickedResources.add(resource);
            } catch ( IllegalArgumentException e ) {
                System.err.println( "No such resource, please try again");
            }
        }

        return pickedResources;
    }

    @Override
    public Actions chooseAction(ArrayList<Actions> possibleActions) {
        Actions choice = null;
        String input;
        boolean done = false;

        do{
            System.out.print("Choose the action you want to do: ");
            for (Actions action : possibleActions) {
                System.out.println(action);
            }
            input = scanner.nextLine().toUpperCase();
            try {
                choice = Actions.valueOf(input);
                done = true;
            } catch ( IllegalArgumentException e ) {
                System.err.println("No such action, please try again");
            }
        } while (!done);


        return choice;
    }

    @Override
    public void marketAction() {
        String input;
        int value = 0;
        boolean done = false;
        boolean rowOrCol = false;
        int whiteMarblesNum;

        ArrayList<Resource> whiteMarblesRes = new ArrayList<>();

        do{
            System.out.print("Type 'row' to pick a row, 'column' to pick a column: ");
            input = scanner.nextLine();
            if(input.matches("(row)|(column)")){
                System.out.print("Choose the number of the " + input + ": ");
                input = scanner.nextLine();
                try{
                    value = Integer.parseInt(input);
                    if(input.equals("col") && value > 0 && value<gameboard.getMarketColumnsNum()){
                        done = true;
                        rowOrCol = false;
                    }
                    else if(input.equals("row") && value > 0 && value<gameboard.getMarketRowsNum()){
                        done = true;
                        rowOrCol = true;
                    }
                    else{
                        System.out.println("The selected " + input + " doesn't exist");
                    }
                } catch (NumberFormatException e){
                    System.out.println("Not a number!!");
                }
            }
            else{
                System.out.println("WRONG INPUT, type 'row' or 'column'");
            }
        } while(!done);

        ClientPlayerBoard activePlayerboard = gameboard.getOnePlayerBoard(clientView.getNickname());

        if(input.equals("col")) whiteMarblesNum = gameboard.getWhiteCountColumn(value);
        else whiteMarblesNum = gameboard.getWhiteCountRow(value);

        if(activePlayerboard.getExchangeBuffsNum() > 1){
            System.out.println("Select what resources you want for the white marbles: ");
            while(whiteMarblesRes.size() < whiteMarblesNum){
                System.out.println("Choose resources " + value + " times from: ");
                for(Resource rss : activePlayerboard.getExchangeBuff())
                    System.out.print(rss);
                input = scanner.nextLine().toUpperCase();
                try {
                    Resource resource = Resource.valueOf(input);
                    whiteMarblesRes.add(resource);
                    value--;
                } catch ( IllegalArgumentException e ) {
                    System.err.println( "No such resource, please try again");
                }
            }
        }
        else if(activePlayerboard.getExchangeBuffsNum() == 1){
            Resource rss = activePlayerboard.getExchangeBuff().get(0);
            for(int i=0; i< whiteMarblesNum; i++){
                whiteMarblesRes.add(rss);
            }
        }


        clientView.marketAction(value, rowOrCol, whiteMarblesRes);
    }

    @Override
    public void useProductionAction() {

    }

    @Override
    public void buyCardAction() {



    }

    @Override
    public void playLeaderCardAction() {
        String input;
        String cardId = null;
        boolean done = false;
        ArrayList<String> hand = gameboard.getOnePlayerBoard(clientView.getNickname()).getHand();
        HashMap<Resource, Integer> wareHouse = new HashMap<>();
        HashMap<Resource, Integer> leaderDepot = new HashMap<>();
        HashMap<Resource, Integer> strongbox = new HashMap<>();
        ClientPlayerBoard active = gameboard.getOnePlayerBoard(clientView.getNickname());

        do {
            System.out.print("Choose what card you want to play: ");
            input = scanner.nextLine();
            if(hand.contains(input)){
                done = true;
                cardId = input;
            }
            else System.out.println("Wrong ID");
        } while(!done);

        int value = 0;

        System.out.println("Select resources you want to pay: ");

        System.out.println("Warehouse");
        for(Integer i :  active.getWarehouse().keySet()){
            do{
                System.out.println("Depot " + i + ": ");
                input = scanner.nextLine();
                try {
                    value = Integer.parseInt(input);
                    if(value < 0 || value > i) System.out.println("You don't have enough resources!");
                    else {
                        if (i < 4) wareHouse.put(active.getWarehouse().get(i).get(0), value);
                        else leaderDepot.put(active.getWarehouse().get(i).get(0), value);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Not a number!!");
                }
            }while (value < 0 || value > i);
        }

        System.out.println("Strongbox");
        for(Resource rss : active.getStrongbox().keySet()){
            do{
                System.out.println(rss + ", amount: ");
                input = scanner.nextLine();
                try{
                    value = Integer.parseInt(input);
                    if(value < 0 || value > active.getStrongbox().get(rss)) System.out.println("Wrong amount!");
                    else strongbox.put(rss, value);
                } catch (NumberFormatException e){
                    System.out.println("Not a number!!");
                }
            } while (value < 0 || value > active.getStrongbox().get(rss));
        }

        clientView.playLeaderCard(cardId, wareHouse, leaderDepot, strongbox);
    }

    @Override
    public void discardLeaderCardAction() {
        String input;
        boolean done = false;
        ArrayList<String> hand = gameboard.getOnePlayerBoard(clientView.getNickname()).getHand();

        do {
            System.out.print("Choose what card you want to discard: ");
            input = scanner.nextLine();
            if(hand.contains(input)) done = true;
            else System.out.println("Wrong ID");
        } while(!done);

        clientView.discardLeaderCard(input);
    }

    @Override
    public void manageResources(ArrayList<Resource> resources) {

    }

    @Override
    public void updateBoard() {

    }
}
