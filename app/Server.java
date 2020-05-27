import java.io.*;
import java.net.*;

public class Server {
    int port;
    ServerSocket server_socket;
    Socket socket;
    String out_file;
    PrintWriter file_writer;
    BufferedReader socket_input;
    PrintWriter socket_output;


    Server(String out_file) throws IOException {
        this.port          = 8080;
        this.server_socket = new ServerSocket(port);
        this.file_writer   = new PrintWriter(new BufferedWriter(new FileWriter(out_file)));
        this.out_file      = out_file;
    }

    public void listen() throws IOException {
        this.socket        = server_socket.accept();
        this.socket_input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket_output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    public void read_file() throws IOException {
        String line;
        while((line = socket_input.readLine()) != null){
            file_writer.println(line);
        }
    }

    public void close() throws IOException {
        file_writer.close();
        socket.close();
        server_socket.close();
    }
    
	public static void main(String[] args) throws IOException {
        String outfile = "./out.txt";
        Server server  = new Server(outfile);

        try {
            server.listen();
            server.read_file();
        } finally {
            server.close();
	   }
	}
}
