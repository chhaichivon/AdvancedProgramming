
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class LoginThread extends Thread {

	private String username;
	private String password;
	private AppCallback callback;

	public LoginThread(String username, String password, AppCallback callback) {
		super();
		this.username = username;
		this.password = password;
		this.callback = callback;
	}

	@Override
	public void run() {
		try {
			Socket connection = new Socket("localhost", 1234);
			
			String request = String.format(":login#username=%s&password=%s", username, password);
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write(request + "\n");
			streamWriter.flush();

			Scanner streamReader = new Scanner(connection.getInputStream());
			String response = streamReader.nextLine();
			if (response.startsWith(":success")) {
				callback.onLoginCompleted(true, null, connection);
			} else {
				callback.onLoginCompleted(false, "Incorrect username or password.", null);
			}
		} catch (IOException e) {
			e.printStackTrace();
			callback.onLoginCompleted(false, "Error while connecting to the server.", null);
		}
	}

}
