import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver2 {
	
	public static void main(String[] args) {
		
		// Set IPv4 for Mac
		System.setProperty("java.net.preferIPv4Stack", "true");
		
		try {
			// Create a multicast socket
			MulticastSocket socket = new MulticastSocket(Constants.GROUP_PORT);
			
			// Join a multicast group
			InetAddress group = InetAddress.getByName(Constants.GROUP_ADDRESS);
			socket.joinGroup(group);
			
			// Listen for data from the group
			while(true) {
				DatagramPacket packet = new DatagramPacket(new byte[Constants.BUFFER_SIZE], Constants.BUFFER_SIZE);
				socket.receive(packet);
				String data = new String(packet.getData(), 0, packet.getLength());
				String ip = packet.getAddress().toString();
				System.out.println(ip + ": " + data);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
