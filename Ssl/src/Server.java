import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class Server {
	
	private static Scanner streamReader;
	private static OutputStreamWriter streamWriter;
	
	public static void main(String[] args) {
		
		
		try {
			// Creating Context
			SSLContext context = SSLContext.getInstance("SSL");
			
			// Creating KeyManagerFactory 
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			
			// Creating KeyStore 
			KeyStore keyStore = KeyStore.getInstance("JKS");
			
			// Filling the KeyStore 
			FileInputStream fileInputStream = new FileInputStream("/Users/leapkh/Desktop/SSL/MiteServer.key");
			char[] password = "123456".toCharArray();
			keyStore.load(fileInputStream, password);
			
			// Initializing the KeyManagerFactory 
			keyManagerFactory.init(keyStore, password);
			
			// Initializing the Context
			context.init(keyManagerFactory.getKeyManagers(), null, null);
			
			// Creating Server Socket
			SSLServerSocketFactory factory = context.getServerSocketFactory();
			SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(1234);
			
			while(true) {
				// Listen for client
				System.out.println("Listen for client...");
				SSLSocket connection = (SSLSocket) serverSocket.accept();
				
				// Send receive data
				streamReader = new Scanner(connection.getInputStream());
				streamWriter = new OutputStreamWriter(connection.getOutputStream());
				
				String request = readData();
				System.out.println("Request: " + request);
				
				writeData("Good morning client");
			}
			
			
		} catch (Exception e) {
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













