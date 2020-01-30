import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
	
	public static void main(String[] args) {
		
		try {
			DatagramSocket socket = new DatagramSocket(1234);
			
			while(true) {
				// Create a packet object for receiving data
				byte[] buffer = new byte[1024];
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				
				// Waiting data from client
				System.out.println("Waiting data from client...");
				socket.receive(request);
				
				String data = new String(request.getData());
				System.out.println("Request: " + data);
				
				// Create a packet object for sending data
				String responseData = "Hello client";
				byte[] responseBuffer = responseData.getBytes();
				DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length, request.getAddress(), request.getPort());
				
				// Send data to client
				socket.send(response);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}









