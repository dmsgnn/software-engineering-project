package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.representations.ClientGameBoard;
import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.controller.Error;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.utility.DevCardsParserXML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CLI implements UserInterface {

    View clientView;
    ClientGameBoard gameboard;
    ArrayList<DevelopmentCard> devCardList;
    private boolean myTurn;
    Scanner scanner;

    public CLI(){
        scanner = new Scanner(System.in);
        devCardList = new DevCardsParserXML().devCardsParser();
        myTurn=false;
    }

    /**
     * connects the CLI to ClientView
     * @param clientView of the client
     */
    public void setClientView(View clientView) {
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
            if(card.getId().equals(id)) {
                return card;
            }
        }
        return null;
    }

    /**
     * initialization
     */
    @Override
    public void begin() {
        System.out.println("\nWelcome to..." + "\n");
        System.out.println("8b   d8            w                             d8b ");
        System.out.println("8YbmdP8 .d88 d88b w8ww .d88b 8d8b d88b    .d8b.  8'  ");
        System.out.println("8  \"  8 8  8 `Yb.  8   8.dP' 8P   `Yb.    8' .8 w8ww ");
        System.out.println("8     8 `Y88 Y88P  Y8P `Y88P 8    Y88P    `Y8P'  8   \n");
        System.out.println("                      w                                ");
        System.out.println("8d8b .d88b 8d8b. .d88 w d88b d88b .d88 8d8b. .d8b .d88b");
        System.out.println("8P   8.dP' 8P Y8 8  8 8 `Yb. `Yb. 8  8 8P Y8 8    8.dP'");
        System.out.println("8    `Y88P 8   8 `Y88 8 Y88P Y88P `Y88 8   8 `Y8P `Y88P");
        clientView.startConnection();
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clientView.login(); // the login method below could directly be called
    }

    /**
     * called to tell the other player who is performing the current turn
     */
    @Override
    public void startTurnNotification(String player) {
        System.out.println(player + "'s turn");
    }

    /**
     * called to notify that this is the last round
     */
    @Override
    public void lastRound(){
        System.out.println("------------------------------------");
        System.out.println("             Last round!            ");
        System.out.println("------------------------------------");
    }

    /**
     * called to show the scores of each player
     * @param finalScores score of each player
     */
    @Override
    public void scoreboard(Map<String, Integer> finalScores){
        clearScreen();
        int counter = 1;
        System.out.println("------------------------------------");
        for (String nickname : finalScores.keySet()){
            System.out.println(counter + ") " + nickname + " score: " + finalScores.get(nickname));
            counter++;
        }
        System.out.println("------------------------------------");
        System.exit(0);
    }

    /**
     * called to show the scores in single player
     * @param score player score
     * @param lorenzoHasWon true if Lorenzo won the game, false if he lost
     * @param nickname the nickname of the player
     */
    @Override
    public void lorenzoScoreboard(String nickname, int score, boolean lorenzoHasWon){
        if(lorenzoHasWon) System.out.println("Lorenzo il Magnifico won the game");
        else System.out.println("You won the game");
        System.out.println("\nScore: " + score);
        System.exit(0);
    }

    /**
     * login into a match
     */
    @Override
    public void login() {
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


        clientView.sendLogin(nickname);
    }

    /**
     * handles login errors
     */
    @Override
    public void failedLogin() {
        System.out.println("Nickname already taken");
        this.login();
    }

    /**
     * called when the login is done
     */
    @Override
    public void loginDone() {
        System.out.println("Correct nickname, other players are joining...");
        inputThread();
    }

    /**
     * Manages a losing player
     * @param nickname Name of loser
     */
    @Override
    public void loser(String nickname) {

    }

    /**
     * called to manage the error message
     * @param errorType contains the error message
     */
    @Override
    public void manageError(Error errorType) {
        switch (errorType){
            case STARTING_RESOURCES:
                System.out.println("Wrong placement of starting resources!!!\n");
                break;
            case STARTING_LEADER_CARD:
                System.out.println("Wrong cards selected!!!\n");
                break;
            case STARTING_MANAGE_RESOURCES:
                System.out.println("Wrong starting resources placement!!!\n");
                break;
            case INVALID_ACTION:
                updateBoard("Invalid action!!\n");
                break;
            case MANAGE_RESOURCES:
                updateBoard("Wrong resources management!!!\n");
                break;
        }
    }

    /**
     * Manages a winning player
     * @param nickname Name of winner
     */
    @Override
    public void winner(String nickname) {
        System.out.println(nickname + " won the game");

        clientView.disconnect();
    }

    /**
     * called if a player correctly joins a game
     */
    @Override
    public void inGameLobby() {

    }

    /**
     * called if the player tries to join a full game
     */
    @Override
    public void gameFull() {

    }

    /**
     * called when the player has correctly selected his starting cards
     */
    @Override
    public void endLeadercardSetup() {
        System.out.println("Correct leader card selection, waiting for other players...");
        inputThread();
    }

    /**
     * called when the player has correctly selected his starting resources
     */
    @Override
    public void endStartingResourcesSetup() {
        System.out.println("Correct starting resources selection, waiting for other players...");
        inputThread();
    }

    /**
     * called when the player ends their turn
     */
    @Override
    public void endTurn() {
        if(clientView.getGameboard().getNumOfPlayers()>1)System.out.println("Waiting for other players...");
        inputThread();
    }

    /**
     * handles the disconnection of one player
     * @param nickname of the player that disconnected
     */
    @Override
    public void handleDisconnection(String nickname) {
        System.out.println(ColorCLI.RED + nickname + " disconnected from the game!" + ColorCLI.RESET);
    }

    /**
     * handles the reconnection of one player
     * @param nickname of the player that reconnected
     */
    @Override
    public void handleReconnection(String nickname) {
        System.out.println(ColorCLI.GREEN + nickname + " reconnected to the game!" + ColorCLI.RESET);
    }

    /**
     * reads player's input when it's not his turn
     */
    private void inputThread(){
        myTurn=false;
        Thread t = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (!myTurn){
                try {
                    if(reader.ready()){
                        reader.readLine();
                        System.out.println("Not your turn!");
                    }
                    else Thread.sleep(500);
                } catch (IOException | InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * called to begin a game, it displays the necessary information to begin a game
     */
    @Override
    public void startGame() {

    }

    /**
     * called only for the first player to make him choose the number of players
     * @param max possible number of players
     */
    @Override
    public void playersNumber(int max) {
        myTurn=true;
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
        clientView.sendNumOfPlayers(value);
        inputThread();
    }

    /**
     * called to allow the players to choose their starting leadercards
     * @param leaderCardID list of the cards ID that the player can choose from
     */
    @Override
    public void startingLeaderCardsSelection(ArrayList<String> leaderCardID) {
        myTurn=true;
        ArrayList<String> pickedCards = new ArrayList<>();
        String input;
        StringBuilder builder = new StringBuilder();
        System.out.print("\nSelect two starting cards, type 1, 2, 3 or 4: \n");

        leaderCardID.stream().map(s -> Objects.requireNonNull(clientView.findLeaderCard(s)).drawTop()).forEach(builder::append);
        builder.append("\n");
        leaderCardID.stream().map(s -> Objects.requireNonNull(clientView.findLeaderCard(s)).drawRequirements()).forEach(builder::append);
        builder.append("\n");
        leaderCardID.stream().map(s -> Objects.requireNonNull(clientView.findLeaderCard(s)).drawVictoryPoints()).forEach(builder::append);
        builder.append("\n");
        leaderCardID.stream().map(s -> Objects.requireNonNull(clientView.findLeaderCard(s)).drawAbility()).forEach(builder::append);
        builder.append("\n");
        leaderCardID.stream().map(s -> Objects.requireNonNull(clientView.findLeaderCard(s)).drawBottom()).forEach(builder::append);
        System.out.println(builder);

        int counter=0;
        ArrayList<Integer> ids = new ArrayList<>();
        int num;
        while(counter<2){
            input = scanner.nextLine();
            try{
                num = Integer.parseInt(input);
                if(num <= 0 || num >4) System.out.println("This card doesn't exist");
                else if(ids.contains(num-1)) System.out.println("Choose a different number");
                else {
                    ids.add(num-1);
                    counter++;
                }
            } catch (NumberFormatException e){
                System.out.println("Not a number!!");
            }
        }

        pickedCards.add(leaderCardID.get(ids.get(0)));
        pickedCards.add(leaderCardID.get(ids.get(1)));
        clientView.sendStartingCards(pickedCards);

    }

    /**
     * called to make the players chose their starting resources
     * @param amount num of starting resources
     */
    @Override
    public void startingResources(int amount) {
        myTurn=true;
        ArrayList<Resource> pickedResources = new ArrayList<>();
        String input;
        int counter=amount;

        while(pickedResources.size() < amount){
            System.out.print("Choose your starting resource, amount " + counter + "\n");
            System.out.print("Options: COINS, STONES, SERVANTS, SHIELDS: ");
            input = scanner.nextLine().toUpperCase();
            try {
                Resource resource = Resource.valueOf(input);
                pickedResources.add(resource);
                counter--;
            } catch ( IllegalArgumentException e ) {
                System.out.println("No such resource, please try again");
            }
        }

        clientView.sendStartingResources(placeWarehouseRes(pickedResources, false));

    }

    /**
     * called to make the player choose the action to perform
     * @param possibleActions list of possible actions
     */
    @Override
    public void chooseAction(ArrayList<Actions> possibleActions) {
        myTurn=true;
        Actions choice = null;
        int actionIndex;
        String input;
        boolean done = false;

        if(possibleActions.size()==1 && possibleActions.contains(Actions.ENDTURN)) clientView.sendAction(Actions.ENDTURN);
        else{
            System.out.print("Choose the action you want to do: \n");
            int i =0;
            for (Actions action : possibleActions) {
                i++;
                System.out.println(i + ")" + action);
            }
            do{
                input = scanner.nextLine();
                try{
                    actionIndex = Integer.parseInt(input);
                    if(actionIndex <= 0 || actionIndex > possibleActions.size()) System.out.println("Invalid number");
                    else {
                        choice = possibleActions.get(actionIndex -1);
                        done = true;
                    }
                } catch (NumberFormatException e){
                    System.out.println("Not a number!!");
                }
            } while (!done);

            clientView.sendAction(choice);
        }
    }

    /**
     * called to make the player perform the market action
     */
    @Override
    public void marketAction() {
        String input;
        String tempColRow = null;
        int value = 0;
        boolean done = false;
        boolean rowOrCol = false;
        int whiteMarblesNum;

        ArrayList<Resource> whiteMarblesRes = new ArrayList<>();

        System.out.print("Type 'row' to pick a row, 'column' to pick a column: ");
        do{
            input = scanner.nextLine();
            if(input.matches("(row)|(column)")){
                tempColRow =input;
                System.out.print("Choose the number of the " + tempColRow + ": ");
                input = scanner.nextLine();
                try{
                    value = Integer.parseInt(input);
                    if(tempColRow.equals("column") && value > 0 && value<=gameboard.getMarketColumnsNum()){
                        value=value-1;
                        done = true;
                        rowOrCol = false;
                    }
                    else if(tempColRow.equals("row") && value > 0 && value<=gameboard.getMarketRowsNum()){
                        value=value-1;
                        done = true;
                        rowOrCol = true;
                    }
                    else{
                        System.out.println("The selected " + tempColRow + " doesn't exist");
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

        if(tempColRow.equals("column")) whiteMarblesNum = gameboard.getWhiteCountColumn(value);
        else whiteMarblesNum = gameboard.getWhiteCountRow(value);


        if(activePlayerboard.getExchangeBuffsNum() > 1){
            System.out.println("Select what resources you want for the white marbles: ");
            while(whiteMarblesNum>0){
                System.out.println("Choose resources " + whiteMarblesNum + " times from: ");
                for(Resource rss : activePlayerboard.getExchangeBuff())
                    System.out.print(rss + " ");
                input = scanner.nextLine().toUpperCase();
                try {
                    Resource resource = Resource.valueOf(input);
                    if(!activePlayerboard.getExchangeBuff().contains(resource)) System.out.println("You can't choose this resource");
                    else{
                       whiteMarblesRes.add(resource);
                       whiteMarblesNum--;
                    }
                } catch ( IllegalArgumentException e ) {
                    System.out.println( "No such resource, please try again");
                }
            }
        }
        else if(activePlayerboard.getExchangeBuffsNum() == 1){
            Resource rss = activePlayerboard.getExchangeBuff().get(0);
            for(int i=0; i< whiteMarblesNum; i++){
                whiteMarblesRes.add(rss);
            }
        }
        String username = clientView.getNickname();

        clientView.marketAction(value, rowOrCol, whiteMarblesRes,username);
    }

    /**
     * called to make the player perform the production action
     */
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
                    if(!active.getDevCardSlot().containsKey(value-1)) System.out.println("Invalid number");
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
        String yesOrNo="no";

        if(active.isProductionBuffActive()){
            do {
                System.out.println("Do you want to use your leader cards production? (type 'yes' or 'no'): ");
                input = scanner.nextLine();
                if (input.matches("(yes)")) {
                    yesOrNo = "yes";
                    if (active.getProductionBuff().size() == 1) {
                        Resource resource = null;
                        while (resource == null) {
                            System.out.print("Choose what resource you want to obtain");
                            System.out.print("Options: COINS, STONES, SERVANTS, SHIELDS: ");
                            input = scanner.nextLine().toUpperCase();
                            try {
                                resource = Resource.valueOf(input);
                                leaderGain.add(resource);
                            } catch (IllegalArgumentException e) {
                                System.out.println("No such resource, please try again");
                            }
                        }
                        leaderCardSlots.add(0);
                    }
                    else{
                        do {
                            System.out.print("Select the leader card slot you want to use for production, type 'quit' to end the selection: ");
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
                                            input = scanner.nextLine().toUpperCase();
                                            try {
                                                resource = Resource.valueOf(input);
                                                leaderGain.add(resource);
                                            } catch ( IllegalArgumentException e ) {
                                                System.out.println("No such resource, please try again");
                                            }
                                        }
                                        leaderCardSlots.add(value-1);
                                    }
                                } catch (NumberFormatException e){
                                    System.out.println("Not a number!!");
                                }
                            }
                        } while(!done || leaderCardSlots.size()<2);
                    }
                }
                else if(!input.matches("(yes)|(no)")){
                    System.out.println("WRONG INPUT, type 'yes' or 'no'");
                }
            }while (!yesOrNo.matches("(yes)|(no)"));
        }


        int picked = 0;
        yesOrNo="no";
        do{
            System.out.print("Do you want to use the your board production? (type 'yes' or 'no'): ");
            input = scanner.nextLine();
            if(input.matches("(yes)")){
                yesOrNo = "yes";
                do {
                    System.out.print((picked<2) ? "Choose the resources you want to pay ": "Choose what resource you want to gain ");
                    input = scanner.nextLine().toUpperCase();
                    try {
                        Resource resource = Resource.valueOf(input);
                        boardProduction.add(resource);
                        picked++;
                    } catch ( IllegalArgumentException e ) {
                        System.out.println( "No such resource, please try again");
                    }
                } while(picked < 3);
            }
            else if(!input.matches("(yes)|(no)")){
                System.out.println("WRONG INPUT, type 'yes' or 'no'");
            }
        }while (!yesOrNo.matches("(yes)|(no)"));

        System.out.println("Select what resources you want to pay: ");

        warehouse = warehousePayment();
        leaderDepot = leaderdepotPayment();
        strongbox = strongboxPayment();
        String username = clientView.getNickname();


        clientView.useProduction(devCardSlots, leaderCardSlots, leaderGain, boardProduction, warehouse, leaderDepot, strongbox,username);
    }

    /**
     * called to make the player perform the buyDevCard action
     */
    @Override
    public void buyCardAction() {
        String input;
        Color cardColor = null;
        int cardLevel = 0;
        int slot = 0;
        boolean done = false;
        HashMap<Resource, Integer> warehouse;
        HashMap<Resource, Integer> leaderDepot;
        HashMap<Resource, Integer> strongbox;

        do {
            System.out.print("Choose the color of the card that you want to buy: \n");
            input = scanner.nextLine().toUpperCase();
            try {
                cardColor = Color.valueOf(input);
                done = true;
            } catch ( IllegalArgumentException e ) {
                System.out.println( "No such color, please try again");
            }
        } while(!done);

        done = false;
        do{
            System.out.println("Choose the level of the card that you want to buy: ");
            input = scanner.nextLine();
            try{
                cardLevel = Integer.parseInt(input);
                if(cardLevel < 1 || cardLevel > 3) System.out.println("Wrong level");
                else done=true;
            } catch (NumberFormatException e){
                System.out.println("Not a number!!");
            }
        }while (!done);

        done = false;
        do{
            System.out.println("Select what slot you want to place your card in: ");
            input = scanner.nextLine();
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
        String username = clientView.getNickname();

        clientView.buyDevCard(cardColor, cardLevel, slot-1, warehouse, leaderDepot, strongbox,username);
    }

    /**
     * called to make the player perform the play leadercard action
     */
    @Override
    public void playLeaderCardAction() {
        String username = clientView.getNickname();
        String input;
        int cardSlot = 0;
        boolean done = false;
        ArrayList<String> hand = gameboard.getOnePlayerBoard(clientView.getNickname()).getHand();

        if(gameboard.getOnePlayerBoard(clientView.getNickname()).getHandSize()==1) clientView.playLeaderCard(hand.get(0),username);
        else {
            do {
                System.out.print("Choose the number of the card that you want to play: ");
                input = scanner.nextLine();
                try {
                    cardSlot = Integer.parseInt(input);
                    if (cardSlot < 1 || cardSlot > hand.size()) System.out.println("Wrong number");
                    else {
                        cardSlot = cardSlot - 1;
                        done = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Not a number!!");
                }
            } while (!done);
            clientView.playLeaderCard(hand.get(cardSlot),username);
        }
    }

    /**
     * called to make the player perform the discard leadercard action
     */
    @Override
    public void discardLeaderCardAction() {
        String username = clientView.getNickname();
        String input;
        int cardSlot = 0;
        boolean done = false;
        ArrayList<String> hand = gameboard.getOnePlayerBoard(clientView.getNickname()).getHand();

        if(gameboard.getOnePlayerBoard(clientView.getNickname()).getHandSize()==1) clientView.discardLeaderCard(hand.get(0),username);
        else{
            do {
                System.out.print("Choose the number of the card that you want to discard: ");
                input = scanner.nextLine();
                try{
                    cardSlot = Integer.parseInt(input);
                    if(cardSlot < 1 || cardSlot > hand.size()) System.out.println("Wrong number");
                    else{
                        cardSlot = cardSlot -1;
                        done=true;
                    }
                } catch (NumberFormatException e){
                    System.out.println("Not a number!!");
                }
            } while(!done);

            clientView.discardLeaderCard(hand.get(cardSlot),username);
        }
    }

    /**
     * method to arrange the resources inside the warehouse
     * @param resources new resources to place
     * @param manage true if manage resources action, false if starting resources action
     * @return new warehouse configuration
     */
    private Map<Integer, ArrayList<Resource>> placeWarehouseRes(ArrayList<Resource> resources, boolean manage){
        String input;
        Resource resource = null;
        boolean done;
        int value = 0;
        Map<Integer, ArrayList<Resource>> newWarehouse = new HashMap<>();

        for(int i= 0; i < 3; i++){
            newWarehouse.put(i, new ArrayList<>());
        }

        if(resources.size()==0 && !manage) return newWarehouse;

        System.out.print("Choose your new warehouse configuration, you have these " + (manage? "additional resources: " : "starting resources: "));
        System.out.println(resources);
        int depotCont;
        for(int i = 0; i < 3; i++){
            depotCont=i+1;
            done = false;
            do{
                System.out.println("Depot " + depotCont + " new amount: ");
                try {
                    input = scanner.nextLine();
                    value = Integer.parseInt(input);
                    if(value<0 || value > depotCont) System.out.println("Invalid number!");
                    else done= true;
                } catch (NumberFormatException e) {
                    System.out.println("Not a number!!");
                }
            }while (!done);
            if(value>0){
                done = false;
                do{
                    System.out.println("Depot " + depotCont + " new resource: ");
                    try {
                        input = scanner.nextLine().toUpperCase();
                        resource = Resource.valueOf(input);
                        done = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println( "No such resource, please try again");
                    }
                }while (!done);
            }
            ArrayList<Resource> temp = new ArrayList<>();
            for(int j = 0; j<value; j++) temp.add(resource);
            newWarehouse.put(i, temp);
        }
        return newWarehouse;
    }

    /**
     * called to make the player manage his resources during the market action
     * @param resources list of resources to manage
     */
    @Override
    public void manageResources(ArrayList<Resource> resources) {
        String input;
        boolean done;
        int value = 0;
        Map<Integer, ArrayList<Resource>> newWarehouse;
        ClientPlayerBoard active = gameboard.getOnePlayerBoard(clientView.getNickname());

        newWarehouse = placeWarehouseRes(resources, true);

        int depotCont;
        for(Integer i : active.getWarehouse().keySet()){
            if(i > 2){
                depotCont=i-2;
                done = false;
                System.out.print("Choose your new leaderdepot " + depotCont + " " + active.getWarehouseResource(i) + " amount: ");
                do{
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

        ArrayList<Resource> discardList = new ArrayList<>();
        discardList.addAll(resources);
        discardList.addAll(active.storedWarehouseRes());
        for (Resource resource : sum) {
            discardList.remove(resource);
        }

        Map<Resource, Integer> discardMap = new HashMap<>();
        for(Resource rss: Resource.values()){
            discardMap.put(rss, 0);
        }
        for (Resource rss : discardList) {
            discardMap.put(rss, discardMap.get(rss) + 1);
        }

        clientView.sendManageResourcesReply(newWarehouse, discardMap);
    }

    /**
     * utility method to create the warehousePayment map
     * @return map containing the resources that the player wants to pay from his warehouse
     */
    private HashMap<Resource, Integer> warehousePayment(){
        HashMap<Resource, Integer> warehouse = new HashMap<>();
        String input;
        int value;
        boolean done = false;
        ClientPlayerBoard active = gameboard.getOnePlayerBoard(clientView.getNickname());

        if(!active.isWarehouseEmpty()) {
            System.out.println("Warehouse: ");
            int depotCont;
            for (int i = 0; i < 3; i++) {
                depotCont = i + 1;
                do {
                    if (active.isDepotEmpty(i)) {
                        done = true;
                    } else {
                        System.out.println("Depot " + depotCont + ": ");
                        input = scanner.nextLine();
                        try {
                            value = Integer.parseInt(input);
                            if (value < 0 || value > depotCont) System.out.println("Invalid num!");
                            else if (value > active.getWarehouse().get(i).size())
                                System.out.println("You don't have enough resources");
                            else {
                                warehouse.put(active.getWarehouseResource(i), value);
                                done = true;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Not a number!!");
                        }
                    }
                } while (!done);
            }
        }
        return warehouse;
    }

    /**
     * utility method to create the leaderdepotPayment map
     * @return map containing the resources that the player wants to pay from his leadeDepot
     */
    private HashMap<Resource, Integer> leaderdepotPayment(){
        HashMap<Resource, Integer> leaderdepot = new HashMap<>();
        String input;
        int value;
        boolean done = false;
        ClientPlayerBoard active = gameboard.getOnePlayerBoard(clientView.getNickname());

        int depotCont;
        for(Integer i : active.getWarehouse().keySet()){
            if(i > 2){
                depotCont=i-2;
                do{
                    if(active.isDepotEmpty(i)){
                        done=true;
                    }
                    else {
                        System.out.println("Leaderdepot " + depotCont + ": ");
                        input = scanner.nextLine();
                        try {
                            value = Integer.parseInt(input);
                            if (value < 0 || value > 2) System.out.println("Invalid num!");
                            else if (value > active.getWarehouse().get(i).size())
                                System.out.println("You don't have enough resources");
                            else {
                                leaderdepot.put(active.getWarehouseResource(i), value);
                                done = true;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Not a number!!");
                        }
                    }
                }while (!done);
            }
        }
        return leaderdepot;
    }

    /**
     * utility method to create the strongboxPayment map
     * @return map containing the resources that the player wants to pay from his strongbox
     */
    private HashMap<Resource, Integer> strongboxPayment(){
        HashMap<Resource, Integer> strongbox = new HashMap<>();
        String input;
        int value = 0;
        ClientPlayerBoard active = gameboard.getOnePlayerBoard(clientView.getNickname());

        if(!active.isStrongboxEmpty()) {
            System.out.println("Strongbox: ");
            for (Resource rss : active.getStrongbox().keySet()) {
                if (active.getStrongbox().get(rss) > 0) {
                    do {
                        System.out.println(rss + ", amount: ");
                        input = scanner.nextLine();
                        try {
                            value = Integer.parseInt(input);
                            if (value < 0 || value > active.getStrongbox().get(rss))
                                System.out.println("Wrong amount!");
                            else strongbox.put(rss, value);
                        } catch (NumberFormatException e) {
                            System.out.println("Not a number!!");
                        }
                    } while (value < 0 || value > active.getStrongbox().get(rss));
                }
            }
        }
        return strongbox;
    }

    /**
     * utility method to draw the gameboard
     * @return gameboard string
     */
    private String strBuilderGameboard(){
        String[][] cardGrid = clientView.getGameboard().getCards();
        MarbleColors[][] market= clientView.getGameboard().getMarket();
        MarbleColors free = clientView.getGameboard().getFreeMarble();
        StringBuilder builder = new StringBuilder();
        int marketRow=0;

        for(int i = 0; i<3; i++){
            for(int j = 0; j < 4; j++){
                DevelopmentCard card = findDevCard(cardGrid[i][j]);
                if (card != null) {
                    builder.append(card.drawTop());
                }
                else builder.append("           ");
            }
            if(marketRow>3) builder.append("\n"); //already added the market
            else {
                builder.append("  ");
                for (int j = 0; marketRow < 3 && j < 4; j++) {
                    builder.append(ColorCLI.marbleColor(market[marketRow][j])).append(" ●");
                }
                builder.append(ColorCLI.RESET).append(" < 1");
                builder.append("  ");
                builder.append(ColorCLI.RESET).append("free: ").append(ColorCLI.marbleColor(free)).append(" ●\n");
                marketRow++;
            }

            for(int j=0; j < 4; j++){
                DevelopmentCard card = findDevCard(cardGrid[i][j]);
                if (card != null) {
                    builder.append(card.drawLevelAndPoints());
                }
                else builder.append("           ");
            }
            if(marketRow>3) builder.append("\n"); //already added the market
            else {
                builder.append("  ");
                for (int j = 0; marketRow < 3 && j < 4; j++) {
                    builder.append(ColorCLI.marbleColor(market[marketRow][j])).append(" ●");
                }
                builder.append(ColorCLI.RESET).append(" < 2\n");
                marketRow++;
            }

            for(int j=0; j< 4; j++){
                DevelopmentCard card = findDevCard(cardGrid[i][j]);
                if (card != null) {
                    builder.append(card.drawRequirements());
                }
                else builder.append("           ");
            }
            if(marketRow>3) builder.append("\n"); //already added the market
            else {
                builder.append("  ");
                for (int j = 0; marketRow < 3 && j < 4; j++) {
                    builder.append(ColorCLI.marbleColor(market[marketRow][j])).append(" ●");
                }
                builder.append(ColorCLI.RESET).append(" < 3\n");
                marketRow++;
            }

            for(int j=0; j< 4; j++){
                DevelopmentCard card = findDevCard(cardGrid[i][j]);
                if (card != null) {
                    builder.append(card.drawProdCostAndGain());
                }
                else builder.append("           ");
            }
            if(marketRow>3) builder.append("\n"); //already added the market
            else {
                builder.append(ColorCLI.RESET).append("   ^ ^ ^ ^");
                builder.append("\n");
            }

            for(int j=0; j< 4; j++){
                DevelopmentCard card = findDevCard(cardGrid[i][j]);
                if (card != null) {
                    builder.append(card.drawBottom());
                }
                else builder.append("           ");
            }
            if(marketRow>3) builder.append("\n"); //already added the market
            else {
                builder.append(ColorCLI.RESET).append("   1 2 3 4");
                builder.append("\n");
                marketRow++;
            }
        }
        builder.append(ColorCLI.RESET);
        return builder.toString();
    }

    /**
     * utility method to draw lorenzo's faithtrack
     * @return lorenzo's faithtrack string
     */
    private String strBuilderLorenzo(){
        int position = clientView.getGameboard().getPlayerBoards().get(0).getLorenzoPosition();
        StringBuilder lorenzo = new StringBuilder();
        lorenzo.append("Lorenzo's faith track\n");
        lorenzo.append("|");
        for(int i=0; i<25; i++){
            if(i == position)
                lorenzo.append("x");
            else
                lorenzo.append(" ");
            lorenzo.append("|");
        }
        return lorenzo.toString();
    }

    /**
     * utility method to draw the playerboard
     * @return playerboard string
     */
    private String strBuilderPlayerboard(ClientPlayerBoard board){
        StringBuilder playerboard = new StringBuilder();
        if(board.getPlayerNickname().equals(clientView.getNickname()))
            playerboard.append(ColorCLI.GREEN).append("Playerboard of ").append(board.getPlayerNickname()).append(ColorCLI.RESET).append("\n");
        else{
            if(!board.isConnected()){
                playerboard.append(ColorCLI.RED);
            }
            playerboard.append("Playerboard of ").append(board.getPlayerNickname()).append("\n").append(ColorCLI.RESET);
        }
        //faith track
        playerboard.append("       1     2     4     6     9    12    16    20\n");
        playerboard.append("|");
        for(int i=0; i<25; i++){
            if(i == board.getPlayerPosition())
                playerboard.append(ColorCLI.RESET).append("x");
            else
                playerboard.append(" ");
            if(i == 7 || i == 8 || i == 15 || i == 16 || i == 23 || i == 24)
                playerboard.append(ColorCLI.RED).append("|");
            else if(i >= 4 && i < 7 || i >= 11 && i < 15 || i >= 18)
                playerboard.append(ColorCLI.YELLOW).append("|");
            else
                playerboard.append(ColorCLI.RESET).append("|");
        }
        playerboard.append(ColorCLI.RESET).append("\n");
        playerboard.append("            |");
        if(board.getVaticanReports().containsKey(8)) {
            if (board.getVaticanReports().get(8))
                playerboard.append(ColorCLI.GREEN).append("2").append(ColorCLI.RESET);
            else
                playerboard.append(ColorCLI.RED).append("3").append(ColorCLI.RESET);
        }
        else {
            playerboard.append(ColorCLI.RESET).append("2");
        }
        playerboard.append("|           |");
        if(board.getVaticanReports().containsKey(16)) {
            if (board.getVaticanReports().get(16))
                playerboard.append(ColorCLI.GREEN).append("3").append(ColorCLI.RESET);
            else
                playerboard.append(ColorCLI.RED).append("2").append(ColorCLI.RESET);
        }
        else {
            playerboard.append(ColorCLI.RESET).append("3");
        }
        playerboard.append(ColorCLI.RESET).append("|             |");
        if(board.getVaticanReports().containsKey(24)) {
            if (board.getVaticanReports().get(24))
                playerboard.append(ColorCLI.GREEN).append("4").append(ColorCLI.RESET);
            else
                playerboard.append(ColorCLI.RED).append("4");
        }
        else {
            playerboard.append(ColorCLI.RESET).append("4").append(ColorCLI.RESET);
        }
        playerboard.append(ColorCLI.RESET).append("|\n");
        //playerBoard production and top of cards
        playerboard.append(" 1?+1? -> 1?   ");
        for(int i=0; i<3; i++){
            if(!board.isSlotEmpty(i))
                playerboard.append(Objects.requireNonNull(findDevCard(board.slotCard(i, board.slotSize(i)))).drawTop()).append(ColorCLI.RESET);
            else
                playerboard.append("          ").append(ColorCLI.RESET);
        }
        playerboard.append("   ");
        //leader card first line
        for(int i=0; i<board.getPlayedCards().size(); i++) {
            playerboard.append(ColorCLI.GREEN).append("●").append(ColorCLI.RESET).append(Objects.requireNonNull(clientView.findLeaderCard(board.getPlayedCards().get(i))).drawTop());
        }
        if(board.getPlayerNickname().equals(clientView.getNickname())){
            for (int i = 0; i < board.getHand().size(); i++) {
                playerboard.append(Objects.requireNonNull(clientView.findLeaderCard(board.getHand().get(i))).drawTop());
            }
        }
        else{
            for(int i=0; i<board.getHandSize(); i++){
                playerboard.append("╔═════════╗");
            }
        }
        playerboard.append("\n");
        //depot and dev cards, first line
        playerboard.append("  |");
        if(board.isDepotEmpty(0))
            playerboard.append(" |");
        else
            playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(0))).append("■").append(ColorCLI.RESET).append("|");
        playerboard.append("   ");
        if(board.getWarehouse().size()>=4){
            if(board.getWarehouse().get(3).isEmpty()){
                playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(3))).append("| | |  ").append(ColorCLI.RESET);
            }
            else if(board.getWarehouse().get(3).size() == 1){
                playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(3))).append("|").append("■").append("|").append(" |  ").append(ColorCLI.RESET);
            }
            else{
                playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(3))).append("|").append("■").append("|").append("■").append("|  ").append(ColorCLI.RESET);
            }
        }
        else{
            playerboard.append("       ");
        }
        for(int i=0; i<3; i++){
            if(!board.isSlotEmpty(i))
                playerboard.append(Objects.requireNonNull(findDevCard(board.slotCard(i, board.slotSize(i)))).drawLevelAndPoints()).append(ColorCLI.RESET);
            else
                playerboard.append("          ").append(ColorCLI.RESET);
        }
        playerboard.append("   ");
        //leader card second line
        for(int i=0; i<board.getPlayedCards().size(); i++) {
            playerboard.append(" ").append(Objects.requireNonNull(clientView.findLeaderCard(board.getPlayedCards().get(i))).drawRequirements());
        }
        if(board.getPlayerNickname().equals(clientView.getNickname())){
            for (int i = 0; i < board.getHand().size(); i++) {
                playerboard.append(Objects.requireNonNull(clientView.findLeaderCard(board.getHand().get(i))).drawRequirements());
            }
        }
        else{
            for(int i=0; i<board.getHandSize(); i++){
                playerboard.append("║         ║");
            }
        }
        playerboard.append("\n");
        //depot and dev cards, second line
        playerboard.append(" |");
        int secondDepotSize = board.getWarehouse().get(1).size();
        if(secondDepotSize == 0)
            playerboard.append(" | |");
        else if(secondDepotSize==1)
            playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(1))).append("■").append(ColorCLI.RESET).append("| |");
        else
            playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(1))).append("■").append(ColorCLI.RESET).append("|").append(ColorCLI.resourceColor(board.getWarehouseResource(1))).append("■").append(ColorCLI.RESET).append("|");
        playerboard.append("  ");
        if(board.getWarehouse().size()==5){
            if(board.getWarehouse().get(4).isEmpty()){
                playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(4))).append("| | |  ").append(ColorCLI.RESET);
            }
            else if(board.getWarehouse().get(4).size() == 1){
                playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(4))).append("|").append("■").append("|").append(" |  ").append(ColorCLI.RESET);
            }
            else{
                playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(4))).append("|").append("■").append("|").append("■").append("|  ").append(ColorCLI.RESET);
            }
        }
        else {
            playerboard.append("       ");
        }
        for(int i=0; i<3; i++){
            if(!board.isSlotEmpty(i))
                playerboard.append(Objects.requireNonNull(findDevCard(board.slotCard(i, board.slotSize(i)))).drawRequirements()).append(ColorCLI.RESET);
            else
                playerboard.append("          ").append(ColorCLI.RESET);
        }
        playerboard.append("   ");
        //leader card third line
        for(int i=0; i<board.getPlayedCards().size(); i++) {
            playerboard.append(" ").append(Objects.requireNonNull(clientView.findLeaderCard(board.getPlayedCards().get(i))).drawVictoryPoints());
        }
        if(board.getPlayerNickname().equals(clientView.getNickname())){
            for (int i = 0; i < board.getHand().size(); i++) {
                playerboard.append(Objects.requireNonNull(clientView.findLeaderCard(board.getHand().get(i))).drawVictoryPoints());
            }
        }
        else{
            for(int i=0; i<board.getHandSize(); i++){
                playerboard.append("║         ║");
            }
        }
        playerboard.append("\n");
        //depot and dev cards, third line
        playerboard.append("|");
        int thirdDepotSize = board.getWarehouse().get(2).size();
        if(thirdDepotSize == 0)
            playerboard.append(" | | |        ");
        else if(thirdDepotSize==1)
            playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(2))).append("■").append(ColorCLI.RESET).append("| | |        ");
        else if(thirdDepotSize==2)
            playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(2))).append("■").append(ColorCLI.RESET).append("|").append(ColorCLI.resourceColor(board.getWarehouseResource(2))).append("■").append(ColorCLI.RESET).append("| |        ");
        else
            playerboard.append(ColorCLI.resourceColor(board.getWarehouseResource(2))).append("■").append(ColorCLI.RESET).append("|").append(ColorCLI.resourceColor(board.getWarehouseResource(2))).append("■").append(ColorCLI.RESET).append("|").append(ColorCLI.resourceColor(board.getWarehouseResource(2))).append("■").append(ColorCLI.RESET).append("|        ");
        for(int i=0; i<3; i++){
            if(!board.isSlotEmpty(i))
                playerboard.append(Objects.requireNonNull(findDevCard(board.slotCard(i, board.slotSize(i)))).drawProdCostAndGain()).append(ColorCLI.RESET);
            else
                playerboard.append("          ").append(ColorCLI.RESET);
        }
        playerboard.append("   ");
        //leader card fourth line
        for(int i=0; i<board.getPlayedCards().size(); i++) {
            playerboard.append(" ").append(Objects.requireNonNull(clientView.findLeaderCard(board.getPlayedCards().get(i))).drawAbility());
        }
        if(board.getPlayerNickname().equals(clientView.getNickname())){
            for (int i = 0; i < board.getHand().size(); i++) {
                playerboard.append(Objects.requireNonNull(clientView.findLeaderCard(board.getHand().get(i))).drawAbility());
            }
        }
        else{
            for(int i=0; i<board.getHandSize(); i++){
                playerboard.append("║         ║");
            }
        }
        playerboard.append("\n");
        //last line
        playerboard.append(" ").append(ColorCLI.resourceColor(Resource.COINS)).append("■  ");
        playerboard.append(" ").append(ColorCLI.resourceColor(Resource.SHIELDS)).append("■  ");
        playerboard.append(" ").append(ColorCLI.resourceColor(Resource.SERVANTS)).append("■  ");
        playerboard.append(" ").append(ColorCLI.resourceColor(Resource.STONES)).append("■ ");
        for(int i=0; i<3; i++){
            if(!board.isSlotEmpty(i))
                playerboard.append(Objects.requireNonNull(findDevCard(board.slotCard(i, board.slotSize(i)))).drawBottom()).append(ColorCLI.RESET);

            else
                playerboard.append("          ").append(ColorCLI.RESET);
        }
        playerboard.append("   ");
        //leader card last line
        for(int i=0; i<board.getPlayedCards().size(); i++) {
            playerboard.append(" ").append(Objects.requireNonNull(clientView.findLeaderCard(board.getPlayedCards().get(i))).drawBottom());
        }
        if(board.getPlayerNickname().equals(clientView.getNickname())){
            for (int i = 0; i < board.getHand().size(); i++) {
                playerboard.append(Objects.requireNonNull(clientView.findLeaderCard(board.getHand().get(i))).drawBottom());
            }
        }
        else{
            for(int i=0; i<board.getHandSize(); i++){
                playerboard.append("╚═════════╝");
            }
        }
        playerboard.append("\n");
        //strongbox resources counter
        playerboard.append(" ");
        playerboard.append(ColorCLI.RESET).append(board.getStrongbox().get(Resource.COINS));
        if(board.getStrongbox().get(Resource.COINS)>9)
            playerboard.append("  ");
        else
            playerboard.append("   ");
        playerboard.append(board.getStrongbox().get(Resource.SHIELDS));
        if(board.getStrongbox().get(Resource.SHIELDS)>9)
            playerboard.append("  ");
        else
            playerboard.append("   ");
        playerboard.append(board.getStrongbox().get(Resource.SERVANTS));
        if(board.getStrongbox().get(Resource.SERVANTS)>9)
            playerboard.append("  ");
        else
            playerboard.append("   ");
        playerboard.append(board.getStrongbox().get(Resource.STONES));
        if(board.getStrongbox().get(Resource.STONES)<9)
            playerboard.append(" ");
        for(int i=0; i<3; i++){
            if(board.slotSize(i)>=2)
                playerboard.append(Objects.requireNonNull(findDevCard(board.slotCard(i, board.slotSize(i)-1))).drawLevelAndPoints()).append(ColorCLI.RESET);
            else
                playerboard.append("          ").append(ColorCLI.RESET);
        }
        playerboard.append("\n");
        playerboard.append("               ");
        for(int i=0; i<3; i++){
            if(board.slotSize(i)==3)
                playerboard.append(Objects.requireNonNull(findDevCard(board.slotCard(i, board.slotSize(i)-2))).drawLevelAndPoints()).append(ColorCLI.RESET);
            else
                playerboard.append("          ").append(ColorCLI.RESET);
        }

        return playerboard.toString();
    }

    /**
     * utility method to clear the screen
     */
    private void clearScreen() {
        //for(int i=0; i<30; i++) System.out.println("\n");
        System.out.println("\033[H\033[2J");
    }

    /**
     * called to print the new board
     */
    @Override
    public void updateBoard(String message) {
        clearScreen();
        System.out.println(strBuilderGameboard());
        if(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getLorenzoPosition() >= 0 ){
            System.out.println(strBuilderLorenzo());
        }
        for(ClientPlayerBoard board : gameboard.getPlayerBoards())
            System.out.println(strBuilderPlayerboard(board));

        if(!message.equals("")) System.out.println(ColorCLI.YELLOW + message + ColorCLI.RESET);
    }
}
