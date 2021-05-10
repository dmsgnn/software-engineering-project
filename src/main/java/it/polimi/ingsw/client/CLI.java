package it.polimi.ingsw.client;

import it.polimi.ingsw.client.representations.ClientGameBoard;
import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.utility.DevCardsParserXML;
import it.polimi.ingsw.utility.LeaderCardsParserXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class CLI implements UserInterface{

    ClientView clientView;
    ClientGameBoard gameboard;
    ArrayList<DevelopmentCard> devCardList = new DevCardsParserXML().devCardsParser();
    ArrayList<LeaderCard> leaderDeck = new LeaderCardsParserXML().leaderCardsParser();

    Scanner scanner = new Scanner(System.in);

    private final Object lock = new Object();

    /**
     * utility method to get the selected card level
     * @param id of the card
     * @return level of the card
     */
    private int getDevCardLevel(String id){
        int level = 0;
        for(DevelopmentCard card : devCardList){
            if(card.getId().equals(id)) level = card.getLevel();
        }
        return level;
    }

    /**
     * utility method to get the selected card color
     * @param id of the card
     * @return color of the card
     */
    private Color getDevCardColor(String id){
        Color color = null;
        for(DevelopmentCard card : devCardList){
            if(card.getId().equals(id)) color = card.getColor();
        }
        return color;
    }

    /**
     * connects the CLI to ClientView
     * @param clientView of the client
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
    private DevelopmentCard findDevCard(String id){
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
    private LeaderCard findLeaderCard(String id){
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
    public Map<Integer, ArrayList<Resource>> startingResources(int amount) {
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

        return placeWarehouseRes(pickedResources, false);

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
        String input;
        int value;
        ArrayList<Integer> devCardSlots = new ArrayList<>();
        ArrayList<Integer> leaderCardSlots = new ArrayList<>();
        ArrayList<Resource> leaderGain = new ArrayList<>();
        ArrayList<Resource> boardProduction = new ArrayList<>();
        boolean done = false;
        ClientPlayerBoard active = gameboard.getOnePlayerBoard(clientView.getNickname());
        HashMap<Resource, Integer> warehouse;
        HashMap<Resource, Integer> leaderDepot;
        HashMap<Resource, Integer> strongbox;

        do {
            System.out.print("Select the slots you want to use for production, type 'quit' to end the selection: ");
            input = scanner.nextLine();
            if(input.equals("quit")){
                done = true;
            }
            else {
                try{
                    value = Integer.parseInt(input);
                    if(!active.getDevCardSlot().containsKey(value)) System.out.println("Invalid number");
                    else if(devCardSlots.contains(value-1)) System.out.println("Slot already selected");
                    else {
                        devCardSlots.add(value-1);
                    }
                } catch (NumberFormatException e){
                    System.out.println("Not a number!!");
                }
            }
        } while(!done);

        done = false;

        if(active.isProductionBuffActive()){
            do {
                System.out.print("Select the leader production card slot you want to use for production, type 'quit' to end the selection: ");
                input = scanner.nextLine();
                if(input.equals("quit")){
                    done = true;
                }
                else {
                    try{
                        value = Integer.parseInt(input);
                        if(value > active.getPlayedCards().size() || value < 1) System.out.println("Invalid number");
                        else if(leaderCardSlots.contains(value-1)) System.out.println("Slot already selected");
                        else {
                            Resource resource = null;
                            while(resource==null){
                                System.out.print("Choose what resource you want to obtain: ");
                                System.out.print("Options: COINS, STONES, SERVANTS, SHIELDS");
                                input = scanner.nextLine().toUpperCase();
                                try {
                                    resource = Resource.valueOf(input);
                                    leaderGain.add(resource);
                                } catch ( IllegalArgumentException e ) {
                                    System.err.println( "No such resource, please try again");
                                }
                            }
                            leaderCardSlots.add(value-1);
                        }
                    } catch (NumberFormatException e){
                        System.out.println("Not a number!!");
                    }
                }
            } while(!done);
            if(leaderCardSlots.size()==1) leaderCardSlots.set(0, 0);
        }

        int picked = 0;

        do{
            System.out.print("Do you want to use the your board production? (type 'yes' or 'no'): ");
            input = scanner.nextLine();
            if(input.matches("(yes)")){
                do {
                    System.out.print((picked<2) ? "Choose the resources you want to pay: ": "Choose what resource you want to gain: ");
                    System.out.print("Options: COINS, STONES, SERVANTS, SHIELDS");
                    input = scanner.nextLine().toUpperCase();
                    try {
                        Resource resource = Resource.valueOf(input);
                        boardProduction.add(resource);
                        picked++;
                    } catch ( IllegalArgumentException e ) {
                        System.err.println( "No such resource, please try again");
                    }
                } while(picked < 3);
            }
            else if(!input.matches("(yes)|(no)")){
                System.out.println("WRONG INPUT, type 'yes' or 'no'");
            }
        }while (!input.matches("(yes)|(no)"));

        System.out.println("Select what resources you want to pay: ");

        warehouse = warehousePayment();
        leaderDepot = leaderdepotPayment();
        strongbox = strongboxPayment();

        clientView.useProduction(devCardSlots, leaderCardSlots, leaderGain, boardProduction, warehouse, leaderDepot, strongbox);
    }

    @Override
    public void buyCardAction() {
        String input;
        String cardId = null;
        int slot = 0;
        boolean done = false;
        HashMap<Resource, Integer> warehouse;
        HashMap<Resource, Integer> leaderDepot;
        HashMap<Resource, Integer> strongbox;

        do {
            System.out.print("Choose the ID of the card that you want to buy: ");
            input = scanner.nextLine();
            if(!gameboard.correctCardId(input)) System.out.println("Wrong ID");
            else{
                cardId = input;
                done = true;
            }
        } while(!done);

        done = false;
        do{
            try{
                slot = Integer.parseInt(input);
                if(slot < 1 || slot > 3) System.out.println("Wrong slot number");
                else done=true;
            } catch (NumberFormatException e){
                System.out.println("Not a number!!");
            }
        }while (!done);

        System.out.println("Select what resources you want to pay: ");

        warehouse = warehousePayment();
        leaderDepot = leaderdepotPayment();
        strongbox = strongboxPayment();

        

        clientView.buyDevCard(getDevCardColor(cardId), getDevCardLevel(cardId), slot, warehouse, leaderDepot, strongbox);
    }

    @Override
    public void playLeaderCardAction() {
        String input;
        String cardId = null;
        boolean done = false;
        ArrayList<String> hand = gameboard.getOnePlayerBoard(clientView.getNickname()).getHand();
        HashMap<Resource, Integer> warehouse;
        HashMap<Resource, Integer> leaderDepot;
        HashMap<Resource, Integer> strongbox;

        do {
            System.out.print("Choose what card you want to play: ");
            input = scanner.nextLine();
            if(hand.contains(input)){
                done = true;
                cardId = input;
            }
            else System.out.println("Wrong ID");
        } while(!done);

        System.out.println("Select what resources you want to pay: ");

        warehouse = warehousePayment();
        leaderDepot = leaderdepotPayment();
        strongbox = strongboxPayment();


        clientView.playLeaderCard(cardId, warehouse, leaderDepot, strongbox);
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

    public Map<Integer, ArrayList<Resource>> placeWarehouseRes(ArrayList<Resource> resources, boolean manage){
        String input;
        Resource resource = null;
        boolean done;
        int value = 0;
        Map<Integer, ArrayList<Resource>> newWarehouse = new HashMap<>();

        System.out.print("Choose your new warehouse configuration, you have these " + (manage? "additional resources: " : "starting resources: "));
        System.out.println(resources);
        for(int i = 1; i < 4; i++){
            done = false;
            do{
                System.out.println("Depot " + i + " new resource: ");
                try {
                    input = scanner.nextLine();
                    resource = Resource.valueOf(input);
                    done = true;
                } catch (IllegalArgumentException e) {
                    System.err.println( "No such resource, please try again");
                }
            }while (!done);
            done = false;
            do{
                System.out.println("Depot " + i + " new amount: ");
                try {
                    input = scanner.nextLine();
                    value = Integer.parseInt(input);
                    if(value<0 || value > i) System.out.println("Invalid number!");
                    else done= true;
                } catch (NumberFormatException e) {
                    System.out.println("Not a number!!");
                }
            }while (!done);
            ArrayList<Resource> temp = new ArrayList<>();
            for(int j = 0; j<value; j++) temp.add(resource);
            newWarehouse.put(i, temp);
        }
        return newWarehouse;
    }

    @Override
    public void manageResources(ArrayList<Resource> resources) {
        String input;
        Resource resource = null;
        boolean done;
        int value = 0;
        Map<Integer, ArrayList<Resource>> newWarehouse;
        ClientPlayerBoard active = gameboard.getOnePlayerBoard(clientView.getNickname());

        newWarehouse = placeWarehouseRes(resources, true);

        for(Integer i : active.getWarehouse().keySet()){
            if(i > 3){
                done = false;
                System.out.print("Choose your new leaderdepot" + (i-3) + " resources amount: ");
                do{
                    System.out.println("Depot " + i + " new amount: ");
                    try {
                        input = scanner.nextLine();
                        value = Integer.parseInt(input);
                        if(value<0 || value > 2) System.out.println("Invalid number!");
                        else done= true;
                    } catch (NumberFormatException e) {
                        System.out.println("Not a number!!");
                    }
                }while (!done);
                ArrayList<Resource> temp = new ArrayList<>();
                for(int j = 0; j<value; j++) temp.add(active.getWarehouseResource(i));
                newWarehouse.put(i, temp);
            }
        }

        ArrayList<Resource> sum = new ArrayList<>();
        for(Integer i : newWarehouse.keySet()){
            sum.addAll(newWarehouse.get(i));
        }
        ArrayList<Resource> totalRes = new ArrayList<>();
        totalRes.addAll(resources);
        totalRes.addAll(active.storedWarehouseRes());

        totalRes.removeAll(sum);
        Map<Resource, Integer> discard;
        discard = totalRes.stream().collect(groupingBy(Function.identity(), Collectors.reducing(0, x -> 1, Integer::sum)));

        clientView.sendManageResourcesReply(newWarehouse, discard);
    }

    private HashMap<Resource, Integer> warehousePayment(){
        HashMap<Resource, Integer> warehouse = new HashMap<>();
        String input;
        int value;
        boolean done = false;
        ClientPlayerBoard active = gameboard.getOnePlayerBoard(clientView.getNickname());

        System.out.println("Warehouse");
        for(int i = 1; i < 4; i++){
            do{
                System.out.println("Depot " + i + ": ");
                input = scanner.nextLine();
                try {
                    value = Integer.parseInt(input);
                    if(value < 0 || value > i) System.out.println("Invalid num!");
                    else if(value > active.getWarehouse().get(i).size()) System.out.println("You don't have enough resources");
                    else {
                        warehouse.put(active.getWarehouse().get(i).get(0), value);
                        done = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Not a number!!");
                }
            }while (!done);
        }
        return warehouse;
    }

    private HashMap<Resource, Integer> leaderdepotPayment(){
        HashMap<Resource, Integer> leaderdepot = new HashMap<>();
        String input;
        int value;
        boolean done = false;
        ClientPlayerBoard active = gameboard.getOnePlayerBoard(clientView.getNickname());

        System.out.println("Leaderdepots");
        for(Integer i : active.getWarehouse().keySet()){
            if(i > 3){
                do{
                    System.out.println("Leaderdepot " + (i-3) + ": ");
                    input = scanner.nextLine();
                    try {
                        value = Integer.parseInt(input);
                        if(value < 0 || value > i) System.out.println("Invalid num!");
                        else if(value > active.getWarehouse().get(i).size()) System.out.println("You don't have enough resources");
                        else {
                            leaderdepot.put(active.getWarehouse().get(i).get(0), value);
                            done = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Not a number!!");
                    }
                }while (!done);
            }
        }
        return leaderdepot;
    }

    private HashMap<Resource, Integer> strongboxPayment(){
        HashMap<Resource, Integer> strongbox = new HashMap<>();
        String input;
        int value = 0;
        ClientPlayerBoard active = gameboard.getOnePlayerBoard(clientView.getNickname());

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
        return strongbox;
    }

    @Override
    public void updateBoard() {

    }
}
