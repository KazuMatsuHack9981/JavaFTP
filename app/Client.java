import java.io.*;
import java.net.*;

public class Client {
    InetAddress    addr;
    int            port;
    Socket         socket;
    BufferedReader file_reader;
    BufferedReader socket_input;
    PrintWriter    socket_output;
    String         send_file;

    Client(String send_file) throws IOException {
        this.addr          = InetAddress.getByName("localhost");
        this.port          = 8080;
        this.send_file     = send_file;
        this.socket        = new Socket(addr, port);
        this.file_reader   = new BufferedReader(new FileReader(send_file));
        this.socket_input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket_output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    public void send(String file) throws IOException {
        String line;
        while((line = file_reader.readLine()) != null) {
            socket_output.println(line);
        }
    }

    public void close() throws IOException {
        file_reader.close();
        socket.close();
    }

	public static void main(String[] args) throws IOException {
        String sendfile = args[0];
        Client client   = new Client(sendfile);

        try {
            client.send(sendfile);
        } finally {
            client.close();
	   }
	}
}