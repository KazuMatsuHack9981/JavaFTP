import java.io.*;
import java.net.*;
public class FileClient {
	public static void main(String[] args) throws IOException {
		InetAddress addr =
			InetAddress.getByName("localhost"); // IP �A�h���X�ւ̕ϊ�
		System.out.println("addr = " + addr);
		Socket socket =
			new Socket(addr, Integer.parseInt(args[0])); // �\�P�b�g�̐���
		try {
            String line;
			System.out.println("socket = " + socket);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while((line = in.readLine()) != null){
				System.out.println(line);
			}
		} finally {
			System.out.println("closing...");
			socket.close();
		}
	}
}
