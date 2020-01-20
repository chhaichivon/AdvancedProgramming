import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandlerThread extends Thread {

	private Socket connection;
	private AppCallback callback;

	

	public ServerHandlerThread(Socket connection, AppCallback callback) {
		super();
		this.connection = connection;
		this.callback = callback;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Scanner streamReader = new Scanner(connection.getInputStream());
				String response = streamReader.nextLine();
				handleServerResponse(response);
			} catch (IOException ex) {
				break;
			}
		}
	}

	private void handleServerResponse(String response) {
		if (response.startsWith(":list")) {
			handleFriendListResponse(response);
		} else if (response.startsWith(":chat")) {
			handleChatResponse(response);
		}
	}

	private void handleFriendListResponse(String response) {
		// :list#users=abc,xyz
		response = response.replace(":list#friends=", ""); // abc,xyz
		String[] friends = response.split(",");
		callback.onFriendListRetrieved(friends);
	}

	private void handleChatResponse(String response) {
		// :chat#friend=abc&message=hello
		response = response.replace(":chat#", "");
		String[] parts = response.split("&");
		String friend = parts[0].replace("friend=", "");
		String message = parts[1].replace("message=", "");
		callback.onMessageReceived(friend, message);
	}

}
