
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ClientHandlerThread extends Thread {

	private Socket connection;
	private AppCallback callback;

	private String username;

	public ClientHandlerThread(Socket connection, AppCallback callback) {
		super();
		this.connection = connection;
		this.callback = callback;
	}

	@Override
	public void run() {
		try {
			Scanner streamReader = new Scanner(connection.getInputStream());
			boolean result = true;
			while (result) {
				String request = streamReader.nextLine();
				result = handleClientRequest(request);
			}
			streamReader.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private boolean handleClientRequest(String request) {
		if (request.startsWith(":login")) {
			return handleLoginRequest(request);
		} else if (request.startsWith(":logout")) {
			return handleClientLogout();
		}else if(request.startsWith(":list")) {
			return handleListRequest();
		}else if(request.startsWith(":chat")) {
			return handleChatRequest(request);
		} else {
			return true;
		}
	}

	private boolean handleLoginRequest(String request) {
		request = request.replace(":login#", ""); // username=abc&password=123
		String[] parts = request.split("&");
		username = parts[0].replace("username=", "");
		String password = parts[1].replace("password=", "");
		System.out.println("User sign in with username=" + username + ", password=" + password);
		boolean result = verifyLogin(username, password);
		if (result) {
			callback.onClientLoggedIn(username, connection);
			String response = ":success";
			sendData(response);
		} else {
			String response = ":fail#message=Incorrect username or password.";
			sendData(response);
		}
		return result;
	}

	private boolean verifyLogin(String username, String password) {
		// TODO verify user
		return true;
	}

	private boolean handleClientLogout() {
		System.out.println("Client logout.");
		callback.onClientLoggedOut(username);
		return false;
	}
	
	private boolean handleListRequest() {
		System.out.println("handleListRequest");
		callback.onFriendListRequest(username);
		return true;
	}
	
	private boolean handleChatRequest(String request) {
		// :chat#me=abc&friend=xyz&message=hello
		request = request.replace(":chat#", "");
		String[] parts = request.split("&");
		String me = parts[0].replace("me=", "");
		String friend = parts[1].replace("friend=", "");
		String message = parts[2].replace("message=", "");
		callback.onChatRequest(me, friend, message);
		
		return true;
	}

	public void sendData(String data) {
		try {
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write(data + "\n");
			streamWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
