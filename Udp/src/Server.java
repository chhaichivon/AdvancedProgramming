import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

	public static void main(String[] args) {
		
		try {
			// Bind port
			DatagramSocket socket = new DatagramSocket(1234);
			
			while(true) {
				// Listen for data from client
				byte[] buffer = new byte[1024];
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				socket.receive(request);
				
				// Send data to client
				String data = "Hello client";
				byte[] responseBuffer = data.getBytes();
				DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length, request.getAddress(), request.getPort());
				socket.send(response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
