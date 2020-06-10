import java.io.*;
import java.net.*;

public class Server {
    int port;
    ServerSocket server_socket;
    Socket socket;
    BufferedReader socket_input;
    PrintWriter socket_output;


    Server() throws IOException {
        this.port          = 8080;
        this.server_socket = new ServerSocket(port);
    }

    public void listen() throws IOException {
        this.socket        = server_socket.accept();
        this.socket_input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket_output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    public String get_file() throws IOException {
        String line;
        String out_file = "./datas/" + socket_input.readLine();
        PrintWriter file_writer = new PrintWriter(new BufferedWriter(new FileWriter(out_file)));
        
        while((line = socket_input.readLine()) != null){
            file_writer.println(line);
        }

        file_writer.close();
        return out_file;
    }

    public void send() throws IOException {
        String line;
        String file_name = socket_input.readLine();
        
        BufferedReader file_reader = new BufferedReader(new FileReader("./datas/"+file_name));
        
        socket_output.println(file_name);

        while((line = file_reader.readLine()) != null) {
            socket_output.println(line);
        }
        socket_output.println("|EOF|");
        file_reader.close();
    }

    public void close() throws IOException {
        socket.close();
        server_socket.close();
    }

    public String signup(String username, String password) {
        File dir = new File(String.format("./datas/ %s", username));
        String user_file = "./userdatas/users.csv";
        
        if(dir.exists()) return "exists";
        else {
            try {
                PrintWriter file_writer = new PrintWriter(new BufferedWriter(new FileWriter(user_file, true)));

                dir.mkdir();
                file_writer.println(String.format("%s,%s", username, password));
                file_writer.close();
                return "success";
            }
            catch (FileNotFoundException e) {return "FileNotFound";}
            catch (IOException e) {return "IOException";}
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
                            return "success";
                        }
                        else{
                            file_reader.close();
                            return "incorrect_password";
                        }
                    }
                }
                file_reader.close();
                return "user_not_found";
            }
            catch (IOException e) {
                return "fail";
            }
        }
        catch(FileNotFoundException e) {return "FileNotFound";}
    }

    public void recv_command() {
        String cmd="none";
        try {
            cmd = socket_input.readLine();

            if(cmd.equals("get")) {
                System.out.println("[*] recieved get");
                send();
            }
            if(cmd.equals("put")) {
                System.out.println("[*] recieved put");
                get_file();
            }
            if(cmd.equals("signup")) {
                System.out.println("[*] recieved signup");
                String username = socket_input.readLine();
                String password = socket_input.readLine();
                String status = signup(username, password);
                socket_output.println(status);
            }
            if(cmd.equals("login")) {
                System.out.println("[*] recieved login");
                String username = socket_input.readLine();
                String password = socket_input.readLine();
                String status   = login(username, password);
                socket_output.println(status);    
            }
            return;
        }
        catch (IOException e){}
    }
    
	public static void main(String[] args) throws IOException {
        Server server = new Server();

        try {
            server.listen();
            while(true) {
                server.recv_command();
            }
        } finally {
            server.close();
        }
	}
}
