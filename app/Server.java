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

    public String read_file() throws IOException {
        String line;
        String out_file = "./datas/" + socket_input.readLine();
        PrintWriter file_writer = new PrintWriter(new BufferedWriter(new FileWriter(out_file)));
        
        while((line = socket_input.readLine()) != null){
            file_writer.println(line);
        }

        file_writer.close();
        return out_file;
    }

    public void send(String file) throws IOException {
        String line;
        BufferedReader file_reader = new BufferedReader(new FileReader("./datas/"+file));
        socket_output.println(file);
        while((line = file_reader.readLine()) != null) {
            socket_output.println(line);
        }
        file_reader.close();
    }

    public void close() throws IOException {
        socket.close();
        server_socket.close();
    }
    
	public static void main(String[] args) throws IOException {
        Server server  = new Server();

        try {
            server.listen();
            server.read_file();
        } finally {
            server.close();
        }
	}
}
