import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class Receiver {

	public static void main(String[] args) {
		
		System.setProperty("java.net.preferIPv4Stack", "true");
		
		try {
			MulticastSocket multicastSocket = new MulticastSocket(1234);
			multicastSocket.setTimeToLive(255);
			
			NetworkInterface ni = NetworkInterface.getByName("en0");
			multicastSocket.setNetworkInterface(ni);
			
			InetAddress groupAddress = InetAddress.getByName("230.0.0.0");
			multicastSocket.joinGroup(groupAddress);
			
			DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
			while(true) {
				System.out.println("Waiting for data...");
				multicastSocket.receive(response);		
				String data = new String(response.getData());
				System.out.println("Data: " + data);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
