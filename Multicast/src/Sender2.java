import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Sender2 {
	
	
	public static void main(String[] args) {
		
		try {
			// Create a muliticast socket
			MulticastSocket socket = new MulticastSocket();
			
			// Send data to the group
			InetAddress groupAddress = InetAddress.getByName(Constants.GROUP_ADDRESS);
			String data;
			if(args.length > 0) {
				data = args[0]; 
			} else {
				data = "Hello group";
			}
			
			DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), groupAddress, Constants.GROUP_PORT);
			socket.send(packet);
			
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
