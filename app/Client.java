import java.io.*;
import java.net.*;
public class Client {
	public static void main(String[] args) throws IOException {
        InetAddress addr = InetAddress.getByName("localhost");
        System.out.println("addr = " + addr);
        
        Socket socket   = new Socket(addr, 8080);
        String sendfile = args[0];

        try {
            System.out.println("Connection accepted: " + socket);
            
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(sendfile));
            BufferedReader in     = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out       = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            
            while((line = reader.readLine()) != null) {
                out.println(line);
            }
            reader.close();
        } finally {
                System.out.println("closing...");
                socket.close();
	   }
	}
}