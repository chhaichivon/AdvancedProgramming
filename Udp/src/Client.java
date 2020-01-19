import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		
		/*try {
			System.out.println("Connect to server...");
			Socket connection = new Socket("52.77.255.22", 1234);
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write("Hi server\n");
			streamWriter.flush();
			connection.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		try {
			DatagramSocket socket = new DatagramSocket(0);
			socket.setSoTimeout(3000);
			String data = args[0];
			byte[] buffer = data.getBytes();
			
			InetAddress address = InetAddress.getByName("52.77.255.22");
			DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, 1234);
			
			System.out.println("Sending request with lenght: " + buffer.length);
			socket.send(request);
			System.out.println("Success");
			
			socket.close();
			
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
	
}
