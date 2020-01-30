import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {

	public static void main(String[] args) {
		
		try {
			DatagramSocket socket = new DatagramSocket(0);
			socket.setSoTimeout(3000);
			
			// Send data to server
			String data = "Hello server";
			byte[] buffer = data.getBytes();
			InetAddress serverAddress = InetAddress.getByName("localhost");
			DatagramPacket request = new DatagramPacket(buffer, buffer.length, serverAddress, 1234);
			socket.send(request);
			
			// Receive data from server
			byte[] responseBuffer = new byte[1024];
			DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length);
			System.out.println("Receiving data from server...");
			socket.receive(response);
			String responseData = new String(response.getData());
			System.out.println("Response: " + responseData);
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
