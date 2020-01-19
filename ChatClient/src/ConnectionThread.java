import java.io.IOException;
import java.net.Socket;

public class ConnectionThread extends Thread {

	private String serverAddress;
	private AppCallback callback;
	
	public ConnectionThread(String serverAddress, AppCallback callback) {
		super();
		this.serverAddress = serverAddress;
		this.callback = callback;
	}

	@Override
	public void run() {
		
		try {
			Socket connection = new Socket(serverAddress, 1234);
			callback.onConnectionConnected(connection);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
