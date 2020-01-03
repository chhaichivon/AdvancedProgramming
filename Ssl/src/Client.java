import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Client {
	
	private static Scanner streamReader;
	private static OutputStreamWriter streamWriter;
	
	public static void main(String[] args) {
		
		// Specifying Trust Store Info
		System.setProperty("javax.net.ssl.trustStore", "/Users/leapkh/Desktop/SSL_Client/MiteTrustStore.key");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		
		try {
			// Creating Socket and Connecting to the Server
			SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket connection = (SSLSocket) socketFactory.createSocket("localhost", 1234);
			
			// Send receive data
			streamReader = new Scanner(connection.getInputStream());
			streamWriter = new OutputStreamWriter(connection.getOutputStream());
			
			writeData("Good morning Server.");
			
			String response = readData();
			System.out.println("Response: " + response);
			
			// Close resource
			connection.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void writeData(String data) {
		try {
			streamWriter.write(data + "\n");
			streamWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String readData() {
		return streamReader.nextLine();
	}

}
