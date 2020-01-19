import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReceiveMessageThread extends Thread {
	
	private Socket connection;
	private AppCallback callback;
	
	

	public ReceiveMessageThread(Socket connection, AppCallback callback) {
		super();
		this.connection = connection;
		this.callback = callback;
	}



	@Override
	public void run() {
		Scanner streamReader;
		try {
			streamReader = new Scanner(connection.getInputStream());
			while(true) {
				String message = streamReader.nextLine();
				if(message.startsWith(":newuser")) {
					callback.onNewUserCame();
				}
				//callback.onMessageReceived(message);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
