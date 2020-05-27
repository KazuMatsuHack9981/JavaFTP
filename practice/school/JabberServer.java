import java.io.*;
import java.net.*;
public class JabberServer {
	 public static int PORT = 8080; // �|�[�g�ԍ���ݒ肷��D
	 public static void main(String[] args) throws IOException {
			if(args.length != 0) {
				 PORT = Integer.parseInt(args[0]);
			}
			ServerSocket s = new ServerSocket(PORT); // �\�P�b�g���쐬����
			System.out.println("Started: " + s);
			try {
				 Socket socket = s.accept(); // �R�l�N�V�����ݒ�v����҂�
				 try {
						System.out.println("Connection accepted: " + socket);
						BufferedReader in =
							 new BufferedReader(
									new InputStreamReader(
										 socket.getInputStream())); // �f�[�^��M�p�o�b�t�@�̐ݒ�
						PrintWriter out =
							 new PrintWriter(
									new BufferedWriter(
										 new OutputStreamWriter(
												socket.getOutputStream())), true); // ���M�o�b�t�@�ݒ�
						while (true) {
							 String str = in.readLine(); // �f�[�^�̎�M
							 if(str.equals("END")) break;
							 System.out.println("Echoing : ");
							 out.println(str); // �f�[�^�̑��M
						}
				 } finally {
						System.out.println("closing...");
						socket.close();
				 }
			} finally {
				 s.close();
			}
	 }
}
