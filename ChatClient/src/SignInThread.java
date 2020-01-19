import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class SignInThread extends Thread {
	
	private Socket connection;
	private String username;
	private String password;
	private AppCallback callback;
	
	public SignInThread(Socket connection, String username, String password, AppCallback callback) {
		super();
		this.connection = connection;
		this.username = username;
		this.password = password;
		this.callback = callback;
	}

	@Override
	public void run() {
		String data = String.format(":signin#username=%s&password=%s\n", username, password);
		
		try {
			
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write(data);
			streamWriter.flush();
			
			callback.onSignInCompleted(true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

























