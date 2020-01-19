import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class Sender {

	public static void main(String[] args) {
		
		try {
			MulticastSocket socket = new MulticastSocket();
			socket.setTimeToLive(255);
			String data = args[0];
			byte[] buffer = data.getBytes();
			
			InetAddress address = InetAddress.getByName("230.0.0.0");
			DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, 1234);
			
			System.out.println("Sending request with lenght: " + buffer.length);
			socket.send(request);
			System.out.println("Success");
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
