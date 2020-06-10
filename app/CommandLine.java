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
        System.out.println("\u001b[00;33m    _                    \u001b[00;31m_____ _____ ____  \u001b[00m");
        System.out.println("\u001b[00;33m    | | __ ___   ____ _  \u001b[00;31m|  ___|_   _|  _ \\ \u001b[00m");
        System.out.println(" _  | |/ _` \\ \\ / / _` | | |_    | | | |_) |");
        System.out.println("\u001b[00;33m| |_| | (_| |\\ V / (_| | \u001b[00;31m|  _|   | | |  __/ \u001b[00m");
        System.out.println("\u001b[00;33m \\___/ \\__,_| \\_/ \\__,_| \u001b[00;31m|_|     |_| |_|    \u001b[00m");
        System.out.println("\n  * version 1.0 created by Kazuki Matsuo * \n\n");
    }

    private void exit() {
        System.out.println("\n\n[*] exiting program...\n");
        try{client.close();}
        catch (IOException e) {}
        System.exit(0);
    }

    private void put(String sendfile) {
        try {client.send(sendfile);}
        catch (IOException e) {System.out.println("\u001b[00;31m[-]\u001b[00m put failed"); return;}
        System.out.println(String.format("\u001b[00;32m[+]\u001b[00m successfully sent %s", sendfile));
    }

    private void get(String getfile) {
        try {client.get_file(getfile);}
        catch (IOException e) {System.out.println("\u001b[00;31m[-]\u001b[00m get failed"); return;}
        System.out.println(String.format("\u001b[00;32m[+]\u001b[00m successfully received %s", getfile));
    }

    private void signup(String username, String password) {
        try {
            String status = client.signup(username, password);
            if(status.equals("success")) {System.out.println(String.format("\u001b[00;32m[+]\u001b[00m signup succeeded as %s:%s", username, password));}
            else if(status.equals("exists")) {System.out.println("\u001b[00;31m[-]\u001b[00m that username already exists");}
            else {System.out.println(status); System.out.println("\u001b[00;31m[-]\u001b[00m signup failed");}
        }
        catch (IOException e) {System.out.println("\u001b[00;31m[-]\u001b[00m signup failed");}
    }

    private void execute(String input) {
        String[] cmd = input.split(" ", 0);
        
        if(cmd[0].equals("exit") || cmd[0].equals("quit")) exit();
        if(cmd[0].equals("put")) {
            if(cmd.length == 2) put(cmd[1]);
            else if(cmd.length < 2) System.out.println("\u001b[00;31m[-]\u001b[00m need to specify filename");
            else System.out.println("\u001b[00;31m[-]\u001b[00m filename must not include space between");
        }
        if(cmd[0].equals("get")) {
            if(cmd.length == 2) get(cmd[1]);
            else if(cmd.length < 2) System.out.println("\u001b[00;31m[-]\u001b[00m need to specify filename");
            else System.out.println("\u001b[00;31m[-]\u001b[00m filename must not include space between");
        }
        if(cmd[0].equals("signup")) {
            if(cmd.length == 3) signup(cmd[1], cmd[2]);
            else System.out.println("\u001b[00;31m[-]\u001b[00m invalid format (signup [username] [password])");
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