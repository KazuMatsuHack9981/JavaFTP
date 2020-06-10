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
        if(dir.exists()) return "exists";
        else {
            dir.mkdir();
            return "success";
        }
    }

    public void recv_command() {
        String cmd="none";
        try {
            cmd = socket_input.readLine();

            if(cmd.equals("get")) {
                System.out.println("[*] recieved get");
                send();
            }
            else if(cmd.equals("put")) {
                System.out.println("[*] recieved put");
                get_file();
            }
            else if(cmd.equals("signup")) {
                System.out.println("[*] recieved signup");
                String username = socket_input.readLine();
                String password = socket_input.readLine();
                String status = signup(username, password);
                socket_output.println(status);
            }
            else {
                System.out.println(cmd);
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
