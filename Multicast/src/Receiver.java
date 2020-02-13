import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver {

	public static void main(String[] args) {
		
		System.setProperty("java.net.preferIPv4Stack", "true");
		
		try {
			// Create socket
			MulticastSocket socket = new MulticastSocket(Constants.GROUP_PORT);
			
			// Join create
			InetAddress groupAddress = InetAddress.getByName(Constants.GROUP_ADDRESS);
			socket.joinGroup(groupAddress);
			
			// Receive data
			while(true) {
				System.out.println("Waiting data from group...");
				DatagramPacket packet = new DatagramPacket(new byte[Constants.BUFFER_SIZE], Constants.BUFFER_SIZE);
				socket.receive(packet);
				String data = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Data: " + data);
				System.out.println("Address: " + packet.getAddress());
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
