import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class BindThread extends Thread {
	
	private ConnectionCallback callback;
	
	public BindThread(ConnectionCallback callback) {
		super();
		this.callback = callback;
	}

	@Override
	public void run() {
		
		
		try {
			// Config key
			SSLContext context = SSLContext.getInstance("SSL");
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			KeyStore keyStore = KeyStore.getInstance("JKS");
			FileInputStream fileInputStream = new FileInputStream("./libs/ChatServer.jks");
			char[] password = "123456".toCharArray();
			keyStore.load(fileInputStream, password);
			keyManagerFactory.init(keyStore, password);
			context.init(keyManagerFactory.getKeyManagers(), null, null);
			SSLServerSocketFactory serverSocketFactory = context.getServerSocketFactory();
			SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(1234);
			
			// Listen for client
			Socket connection = serverSocket.accept();
			
			// Notify to MainThread
			callback.onConnected(connection);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
