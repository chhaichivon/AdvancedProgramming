import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Client {

	public static void main(String[] args) {
		
		System.setProperty("javax.net.ssl.trustStore", "/Users/leapkh/Desktop/Temporary/SSL/Client/MyStrustStore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		
		SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		try {
			// Connect to server
			SSLSocket connection = (SSLSocket) socketFactory.createSocket("localhost", 1234);
			
			// Send request to server
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write("Hi server\n");
			streamWriter.flush();
			
			// Read response from server
			Scanner streamReader = new Scanner(connection.getInputStream());
			String response = streamReader.nextLine();
			System.out.println("Response: " + response);
			
			connection.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
