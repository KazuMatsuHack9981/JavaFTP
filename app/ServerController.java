import java.io.*;
import java.net.*;

public class ServerController {
    public static final int port = 8080;

    public static void main(String args[]) {
        ServerSocket server_socket = null;
        try {
            server_socket = new ServerSocket(port);
            while (true) {
                Socket socket = server_socket.accept();
                new Server(socket).start();
            }
        }
        catch (IOException e) {}
        finally {
            try {
                if (server_socket != null) {
                    System.out.println("closing main server socket");
                    server_socket.close();
                }
            }
            catch (IOException e) {}
        }
    }
}