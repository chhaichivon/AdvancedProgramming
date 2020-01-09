import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReceiverThread extends Thread {
	
	private Socket connection;
	private MessageCallback callback;
	
	public ReceiverThread(Socket connection, MessageCallback callback) {
		super();
		this.connection = connection;
		this.callback = callback;
	}


	@Override
	public void run() {
	
		try {
			Scanner streamReader = new Scanner(connection.getInputStream());
			while(true) {
				String message = streamReader.nextLine();
				callback.onMessageReceived(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
