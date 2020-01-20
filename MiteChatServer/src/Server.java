

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server implements AppCallback {

	private HashMap<String, Socket> clients;

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		clients = new HashMap<String, Socket>();
		try {
			ServerSocket serverSocket = new ServerSocket(1234);
			while (true) {
				System.out.println("Wait for client...");
				Socket connection = serverSocket.accept();
				ClientHandlerThread clientHandlerThread = new ClientHandlerThread(connection, this);
				clientHandlerThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClientLoggedIn(String username, Socket connection) {
		System.out.println("Client logged in.");
		clients.put(username, connection);
		sendFriendListResponse();
	}

	@Override
	public void onClientLoggedOut(String username) {
		System.out.println("Client logged out.");
		clients.remove(username);
		sendFriendListResponse();
	}

	@Override
	public void onFriendListRequest(String username) {
		System.out.println("onFriendListRequest");
		sendFriendListResponse(username);
	}
	
	@Override
	public void onChatRequest(String me, String friend, String message) {
		String response = String.format(":chat#friend=%s&message=%s", me, message);
		Socket connection = clients.get(friend);
		SenderThread senderThread = new SenderThread(connection, response);
		senderThread.start();
	}
	
	private void sendFriendListResponse(String username) {
		String response = ":list#friends=";
		for(String friend : clients.keySet()) {
			response += friend + ",";
		}
		Socket connection = clients.get(username);
		SenderThread senderThread = new SenderThread(connection, response);
		senderThread.start();
	}
	
	private void sendFriendListResponse() {
		for(String username:clients.keySet()) {
			sendFriendListResponse(username);
		}
	}

}
