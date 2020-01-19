import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BindThread extends Thread {

	private AppCabllback callback;
	
	public BindThread(AppCabllback callback) {
		super();
		this.callback = callback;
	}



	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(1234);
			while(true) {
				System.out.println("Waiting for client...");
				Socket connection = serverSocket.accept();
				callback.onClientConnected(connection);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
