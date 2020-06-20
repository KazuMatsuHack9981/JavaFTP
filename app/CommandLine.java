import java.io.*;
import java.util.Scanner;



public class CommandLine {
    Client         client;
    MessageModule  message;
    Status         status;

    CommandLine() {
        try {
            client  = new Client();
            message = new MessageModule();
            status  = new Status();
        }
        catch (IOException e) {}
    }

    private void activate() {
        message.print_log("Starting JavaFTP");
        message.newline(1);
        message.print_banner();
    }

    private void exit() {
        message.newline(2);
        message.print_log("exiting program...");
        message.newline(1);

        try{client.close();}
        catch (IOException e) {}
        System.exit(0);
    }

    private void put(String sendfile) {
        try {client.send(sendfile);}
        catch (IOException e) {message.print_err("put failed"); return;}
        message.print_ok(String.format("successfully sent %s", sendfile));
    }

    private void get(String getfile) {
        try {client.get_file(getfile);}
        catch (IOException e) {message.print_err("get failed"); return;}
        message.print_ok(String.format("successfully received %s", getfile));
    }

    private void signup(String username, String password) {
        try {
            String stats = client.signup(username, password);
            if(stats.equals(status.success)) {message.print_ok(String.format("signup succeeded as %s:%s", username, password));}
            else if(stats.equals(status.file_exists)) {message.print_err("that username already exists");}
            else {message.print_err("signup failed");}
        }
        catch (IOException e) {message.print_err("signup failed");}
    }

    private void login(String username, String password) {
        try {
            String stats = client.login(username, password);
            if(stats.equals(status.success)) {message.print_ok(String.format("login succeeded as %s", username));}
            else if(stats.equals(status.incorrect_password)) {message.print_err("password is incorrect");}
            else if(stats.equals(status.user_not_found)) {message.print_err("user not found");}
            else {message.print_err("login failed");}
        }
        catch (IOException e) {message.print_err("login failed");}
    }

    private void delete(String filename) {
        try {
            String stats = client.delete(filename);
            if(stats.equals(status.success)) {message.print_ok(String.format("successfully deleted %s", filename));}
            else if(stats.equals(status.file_not_found)) {message.print_err("file not found");}
            else {message.print_err("delete failed");}
        }
        catch (IOException e) {message.print_err("delete failed");}
    }

    private void execute(String input) {
        String[] cmd = input.split(" ", 0);
        
        if(cmd[0].equals("exit") || cmd[0].equals("quit")) exit();
        if(cmd[0].equals("put")) {
            if(cmd.length == 2) put(cmd[1]);
            else message.print_err("invalid format (put [filename]])");
        }
        if(cmd[0].equals("get")) {
            if(cmd.length == 2) get(cmd[1]);
            else message.print_err("invalid format (get [filename]])");
        }
        if(cmd[0].equals("signup")) {
            if(cmd.length == 3) signup(cmd[1], cmd[2]);
            else message.print_err("invalid format (signup [username] [password])");
        }
        if(cmd[0].equals("login")) {
            if(cmd.length == 3) login(cmd[1], cmd[2]);
            else message.print_err("invalid format (login [username] [password])");
        }
        if(cmd[0].equals("delete")) {
            if(cmd.length == 2) delete(cmd[1]);
            else message.print_err("invalid format (delete [filename]])");
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