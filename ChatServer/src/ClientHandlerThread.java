import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandlerThread extends Thread {

	private Socket connection;
	private AppCabllback callback;
	
	private String username;
	
	public ClientHandlerThread(Socket connection, AppCabllback callback) {
		super();
		this.connection = connection;
		this.callback = callback;
	}

	@Override
	public void run() {
		try {
			Scanner streamReader = new Scanner(connection.getInputStream());
			while(true) {
				String request = streamReader.nextLine();
				if(request.startsWith(":signin")) {
					System.out.println("signin");
					handleSignIn(request);
				} else if(request.startsWith(":list")) {
					System.out.println("list");
					callback.onClientListRequest(username);
				} else {
					System.out.println("unknown");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleSignIn(String request) {
		request = request.replace(":signin#", ""); // username=abc&password=123
		String[] parts = request.split("&");
		username = parts[0].replace("username=", "");
		String password = parts[1].replace("password=", "");
		System.out.println("User sign in with username=" + username + ", password=" + password);
		callback.onClientSignInCompelted(username, connection);
	}

	
}















