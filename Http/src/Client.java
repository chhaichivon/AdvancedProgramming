import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
public static void main(String[] args) {
		
		try {
			// Connect to server
			Socket connection = new Socket("localhost", 80);
			
			// Send request to server
			OutputStream outputStream = connection.getOutputStream();
			OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
			streamWriter.write("GET /rupp/mite/index.php HTTP/1.1\r\n");
			streamWriter.write("Host: localhost\r\n");
			streamWriter.write("Connection: close\r\n");
			streamWriter.write("\r\n");
			streamWriter.flush();
			
			// Receive response from server
			InputStream inputStream = connection.getInputStream();
			Scanner streamReader = new Scanner(inputStream);
			while(streamReader.hasNext()) {
				String response = streamReader.nextLine();
				System.out.println(response);				
			}
			
			// Close connection
			streamReader.close();
			connection.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
