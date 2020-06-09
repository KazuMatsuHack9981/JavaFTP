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

    public String read_file() throws IOException {
        String line;
        String out_file = socket_input.readLine();
        PrintWriter file_writer = new PrintWriter(new BufferedWriter(new FileWriter(out_file)));
        
        while((line = socket_input.readLine()) != null){
            file_writer.println(line);
        }

        file_writer.close();
        return out_file;
    }
    
    public void send(String file) throws IOException {
        String line;
        BufferedReader file_reader = new BufferedReader(new FileReader(file));
        socket_output.println(file);
        while((line = file_reader.readLine()) != null) {
            socket_output.println(line);
        }
        file_reader.close();
    }

    public void close() throws IOException {
        socket.close();
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