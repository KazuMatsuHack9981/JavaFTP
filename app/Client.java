import java.io.*;
import java.net.*;

public class Client {
    InetAddress      addr;
    int              port;
    Socket           socket;
    BufferedReader   socket_input;
    PrintWriter      socket_output;

    Client() throws IOException {
        this.addr          = InetAddress.getByName("localhost");
        this.port          = 8080;
        this.socket        = new Socket(addr, port);
        this.socket_input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket_output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    public void get_file(String getfile) throws IOException {
        socket_output.println("get");

        String line;
        
        socket_output.println(getfile);
        String out_file = socket_input.readLine();
        PrintWriter file_writer = new PrintWriter(new BufferedWriter(new FileWriter(out_file)));
        
        while((line = socket_input.readLine()) != null){
            if(line.equals("|EOF|")) break;
            file_writer.println(line);
        }
        file_writer.close();
    }
    
    public void send(String file) throws IOException {
        socket_output.println("put");
        
        String line;
        BufferedReader file_reader = new BufferedReader(new FileReader(file));
        socket_output.println(file);
        while((line = file_reader.readLine()) != null) {
            socket_output.println(line);
        }
        socket_output.println("|EOF|");
        file_reader.close();
    }

    public void close() throws IOException {
        socket.close();
    }

    public String signup(String username, String password) throws IOException {
        socket_output.println("signup");
        socket_output.println(username);
        socket_output.println(password);
        String status = socket_input.readLine();
        return status;
    }
    
    public String login(String username, String password) throws IOException {
        socket_output.println("login");
        socket_output.println(username);
        socket_output.println(password);
        String status = socket_input.readLine();
        return status;
    }

    public String delete(String filename) throws IOException {
        socket_output.println("delete");
        socket_output.println(filename);
        String status = socket_input.readLine();
        return status;
    }

	public static void main(String[] args) throws IOException {
        String sendfile = args[0];
        Client client   = new Client();

        try {
            client.send(sendfile);
        } finally {
            client.close();
        }
	}
}