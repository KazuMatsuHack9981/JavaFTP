import java.io.*;
import java.net.*;
public class Server {
	public static final int PORT = 8080;
	public static void main(String[] args) throws IOException {
		String htmlfile = "./index.html";
		ServerSocket s = new ServerSocket(PORT);
		//System.out.println("Started: " + s);
		BufferedReader reader;
		try {
			Socket socket = s.accept(); // コネクション設定要求を待つ
			try {
				String line;
				//System.out.println("Connection accepted: " + socket);
				reader = new BufferedReader(new FileReader(htmlfile));
				PrintWriter out =
							 new PrintWriter(
									new BufferedWriter(
										 new OutputStreamWriter(
												socket.getOutputStream())), true);
				while((line = reader.readLine()) != null) {
					out.println(line);
				}
				reader.close();
			} finally {
				   //System.out.println("closing...");
				   socket.close();
			}
	   } finally {
			s.close();
	   }
	}
}