import java.io.*;
import java.net.*;
public class JabberClient {
	public static void main(String[] args) throws IOException {
		InetAddress addr =
			InetAddress.getByName("localhost"); // IP �A�h���X�ւ̕ϊ�
		System.out.println("addr = " + addr);
		Socket socket =
			new Socket(addr, Integer.parseInt(args[0])); // �\�P�b�g�̐���
		try {
			System.out.println("socket = " + socket);
			BufferedReader in =
				new BufferedReader(
					new InputStreamReader(socket.getInputStream())); // �f�[�^��M�p�o�b�t�@�̐ݒ�
			PrintWriter out = 
				new PrintWriter(
					new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())), true); // ���M�o�b�t�@�ݒ�
			for(int i = 0; i < 10; i++) {
				out.println("howdy " + i); // �f�[�^���M
				String str = in.readLine(); // �f�[�^��M
				System.out.println(str);
			}
			out.println("END");
		} finally {
			System.out.println("closing...");
			socket.close();
		}
	}
}
