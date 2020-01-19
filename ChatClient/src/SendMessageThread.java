import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendMessageThread extends Thread {

	private Socket connection;
	private String message;
	private String username;
	private AppCallback callback;

	public SendMessageThread(Socket connection, String message, String username, AppCallback callback) {
		super();
		this.connection = connection;
		this.message = message;
		this.username = username;
		this.callback = callback;
	}

	@Override
	public void run() {

		String data = String.format(":send?username=%s&message=%s\n", username, message);

		try {

			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write(data);
			streamWriter.flush();
			callback.onMessageSent(true);

		} catch (IOException e) {
			callback.onMessageSent(false);
			e.printStackTrace();
		}

	}

}
