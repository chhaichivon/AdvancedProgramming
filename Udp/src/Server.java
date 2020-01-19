import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		/*try {
			ServerSocket serverSocket = new ServerSocket(1234);
			Socket connection = serverSocket.accept();
			Scanner streamReader = new Scanner(connection.getInputStream());
			String data = streamReader.nextLine();
			System.out.println("Request: " + data);
			streamReader.close();
			connection.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		
		
		try {
			DatagramSocket socket = new DatagramSocket(1234);

			byte[] buffer = new byte[10];
			
			while(true) {
				
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				
				System.out.println("Waiting for request...");
				socket.receive(request);
				
				String data = new String(request.getData());
				System.out.println("Request: " + data);
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
}
