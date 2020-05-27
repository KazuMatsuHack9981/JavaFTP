import java.io.*;
import java.net.*;
public class Server {
    public static final int PORT = 8080;
    
	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(PORT);
        System.out.println("Started: " + s);
        String outfile = "./out.txt";
        
        try {
            Socket socket = s.accept();
            try {
                System.out.println("socket = " + socket);

                String line;
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outfile)));
                BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                while((line = in.readLine()) != null){
                    writer.println(line);
                    System.out.println(line);
                }
                writer.close();
            } finally {
                System.out.println("closing...");
                socket.close();
            } 
        } finally {
            s.close();
        }
	}
}
