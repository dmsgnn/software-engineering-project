package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.GUI;

public class ClientMain {
    public static void main(String []args) {
        //needed to know if the user wants to play with the cli or gui interface
        String ui = null;
        //ip entered by the user from command line
        String ip = null;
        int port = -1;

        for (String arg : args) {
            if (arg.toLowerCase().matches("(cli)|(gui)"))
                ui = arg.toLowerCase();
            else if (arg.matches("^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$"))
                ip = arg;
            else if (arg.matches("(6553[0-5]|655[0-2][0-9]|65[0-4][0-9][0-9]|6[0-4][0-9][0-9][0-9]|[1-5](\\d){4}|[1-9](\\d){0,3})"))
                port = Integer.parseInt(arg);
        }

        //default values
        if (ip == null)
            ip = "127.0.0.1";
        if (port <= 1024)
            port = 4000;

        System.out.println("ip: " + ip);
        System.out.println("port number: " + port);

        //ui initialization
        if(ui!=null && ui.equals("cli")) {
            System.out.println("You're going to play with the CLI interface");
            CLI cli = new CLI();
            ClientView clientView = new ClientView(ip, port, cli);
            cli.setClientView(clientView);
            cli.begin();
        }
        else{
            System.out.println("You're going to play with the GUI interface");
            GUI gui = new GUI();
            ClientView clientView = new ClientView(ip, port, gui);
            gui.setClientView(clientView);
            new Thread(gui::begin).start();
        }
    }
}
