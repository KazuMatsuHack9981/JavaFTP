import java.io.*;
import java.net.*;

public class Server extends Thread {
    Socket          socket;
    BufferedReader  socket_input;
    PrintWriter     socket_output;
    String          current_user;
    String          out_dir;
    MessageModule   message;
    Status          status;
    static int      num_of_thread = 0;


    Server(Socket socket) throws IOException {
        this.socket        = socket;
        this.current_user  = "|none|";
        this.out_dir       = "./datas/";
        this.message       = new MessageModule();
        this.status        = new Status();
    }

    public void create_stream() throws IOException {
        this.socket_input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket_output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    public synchronized String get_file() throws IOException {
        String line;
        String out_file = out_dir + socket_input.readLine();
        PrintWriter file_writer = new PrintWriter(new BufferedWriter(new FileWriter(out_file)));
        
        while((line = socket_input.readLine()) != null){
            if(line.equals("|EOF|")) break;
            file_writer.println(line);
        }

        file_writer.close();
        return out_file;
    }

    public synchronized void send() throws IOException {
        String line;
        String file_name = socket_input.readLine();
        BufferedReader file_reader = new BufferedReader(new FileReader(out_dir+file_name));
        
        socket_output.println(file_name);

        while((line = file_reader.readLine()) != null) {
            socket_output.println(line);
        }
        socket_output.println("|EOF|");
        file_reader.close();
    }

    public synchronized String signup(String username, String password) {
        File dir = new File(String.format("./datas/%s", username));
        String user_file = "./userdatas/users.csv";
        
        if(dir.exists()) return status.file_exists;
        else {
            try {
                PrintWriter file_writer = new PrintWriter(new BufferedWriter(new FileWriter(user_file, true)));

                dir.mkdir();
                file_writer.println(String.format("%s,%s", username, password));
                file_writer.close();
                return status.success;
            }
            catch (FileNotFoundException e) {
                message.print_err("FileNotFoundException in 'signup'");
                return status.fail;
            }
            catch (IOException e) {
                message.print_err("IOException in 'signup'");
                return status.fail;
            }
        }
    }

    public String login(String username, String password) {
        try {
            BufferedReader file_reader = new BufferedReader(new FileReader("./userdatas/users.csv"));
            String line;

            try {
                while((line = file_reader.readLine()) != null) {
                    String[] user_pass = line.split(",", 0);
                    if(user_pass[0].equals(username)) {
                        if(user_pass[1].equals(password)){
                            file_reader.close();
                            current_user = username;
                            out_dir = ("./datas/" + username + "/");
                            return status.success;
                        }
                        else{
                            file_reader.close();
                            return status.incorrect_password;
                        }
                    }
                }
                file_reader.close();
                return status.user_not_found;
            }
            catch (IOException e) {
                message.print_err("IOException in 'login'");
                return status.fail;
            }
        }
        catch(FileNotFoundException e) {
            message.print_err("FileNotFoundException in 'login'");
            return status.fail;
        }
    }

    public synchronized String delete() throws IOException {
        String filename = socket_input.readLine();
        File file = new File(String.format(out_dir+filename));

        if(!file.exists()) return status.file_not_found;
        if(file.delete()) return status.success;
        message.print_err("failed to delete file in 'delete'");
        return status.fail;
    }

    public String recv_command() {
        String cmd="none";
        try {
            cmd = socket_input.readLine();

            if(cmd==null) {
                num_of_thread -= 1;
                message.print_log(String.format("closing... thread remaining : %s", String.valueOf(num_of_thread)));
                return status.done;
            }
            if(cmd.equals("get")) {
                message.print_log("recieved get");
                send();
            }
            if(cmd.equals("put")) {
                message.print_log("recieved put");
                get_file();
            }
            if(cmd.equals("signup")) {
                message.print_log("recieved signup");
                String username = socket_input.readLine();
                String password = socket_input.readLine();
                String stats = signup(username, password);
                socket_output.println(stats);
            }
            if(cmd.equals("login")) {
                message.print_log("recieved login");
                String username = socket_input.readLine();
                String password = socket_input.readLine();
                String stats = login(username, password);
                socket_output.println(stats);    
            }
            if(cmd.equals("delete")) {
                message.print_log("recieved delete");
                String stats = delete();
                socket_output.println(stats);
            }
            return status.not_done;
        }
        catch (IOException e){message.print_err("IOException in 'recv_command'");}
        return status.not_done;
    } 

    @Override
    public void run() {
        try {
            num_of_thread += 1;
            message.print_log(String.format("creating thread%s...", String.valueOf(num_of_thread)));
            create_stream();
            while(true) {
                String stats = recv_command();
                if(stats.equals(status.done)) break;
            }
        }
        catch (IOException e){message.print_err("IOException in 'run'");}
    }
}
