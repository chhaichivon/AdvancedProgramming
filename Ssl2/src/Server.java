import java.io.FileInputStream;
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
	
	public static void main(String[] args) {
		
		try {
			// Creating Context
			SSLContext context = SSLContext.getInstance("SSL");
			
			// Creating KeyManagerFactory 
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			
			// Creating KeyStore 
			KeyStore keyStore = KeyStore.getInstance("JKS");
			
			// Filling the KeyStore 
			FileInputStream fileInputStream = new FileInputStream("/Users/leapkh/Desktop/Temporary/SSL/MyServer.jks");
			char[] password = "123456".toCharArray();
			keyStore.load(fileInputStream, password);
			
			// Initializing the KeyManagerFactory 
			keyManagerFactory.init(keyStore, password);
			
			// Initializing the Context
			context.init(keyManagerFactory.getKeyManagers(), null, null);
			
			// Creating Server Socket
			SSLServerSocketFactory serverSocketFactory = context.getServerSocketFactory();
			SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(1234);
			
			while(true) {
				// Listen for client
				System.out.println("Waiting for client...");
				SSLSocket connection = (SSLSocket) serverSocket.accept();
				
				// Read request from client
				Scanner streamReader = new Scanner(connection.getInputStream());
				String request = streamReader.nextLine();
				System.out.println("Request: " + request);
				
				// Send response to client
				OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
				streamWriter.write("Hi client\n");
				streamWriter.flush();
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}










