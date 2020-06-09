import java.io.*;
import java.util.Scanner;


public class CommandLine {
    Client client;

    CommandLine() {
        try {client = new Client();}
        catch (IOException e) {}
    }

    private void activate() {
        System.out.println("[*] Starting JavaFTP...\n");
        System.out.println("    _                    _____ _____ ____  ");
        System.out.println("    | | __ ___   ____ _  |  ___|_   _|  _ \\ ");
        System.out.println(" _  | |/ _` \\ \\ / / _` | | |_    | | | |_) |");
        System.out.println("| |_| | (_| |\\ V / (_| | |  _|   | | |  __/ ");
        System.out.println(" \\___/ \\__,_| \\_/ \\__,_| |_|     |_| |_|    ");
        System.out.println("\n  * version 1.0 created by Kazuki Matsuo * \n\n");
    }

    private void exit() {
        System.out.println("\n[*] exiting program...\n");
        try{client.close();}
        catch (IOException e) {}
        System.exit(0);
    }

    private void put(String sendfile) {
        try {client.send(sendfile);}
        catch (IOException e) {System.out.print("\u001b[00;31m[-]\u001b[00m put failed");}
        finally {System.out.println(String.format("\u001b[00;32m[+]\u001b[00m successfully sent %s", sendfile));}
    }

    private void execute(String input) {
        String[] cmd = input.split(" ", 0);
        
        if(cmd[0].equals("exit") || cmd[0].equals("quit")) exit();
        if(cmd[0].equals("put")) {
            if(cmd.length == 2) put(cmd[1]);
            else if(cmd.length < 2) System.out.println("\u001b[00;31m[-]\u001b[00m need to specify filename");
            else System.out.println("\u001b[00;31m[-]\u001b[00m filename must not include space between");
        }
    }

    public static void main(String[] args) {
        CommandLine cli  = new CommandLine();
        Scanner scanner = new Scanner(System.in);

        cli.activate();

        while(true) {
            System.out.print(String.format("JavaFTP > "));
            String input = scanner.nextLine();
            cli.execute(input);
        } 
    }
}