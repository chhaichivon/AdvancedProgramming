import java.net.Socket;
import java.util.HashMap;

public class Server implements AppCabllback {

	private HashMap<String, Socket> clients = new HashMap<String, Socket>();
	
	public static void main(String[] args) {
		Server server = new Server();
	}

	public Server() {
		BindThread bindThread = new BindThread(this);
		bindThread.start();
	}

	@Override
	public void onClientConnected(Socket connection) {
		ClientHandlerThread signInHandlerThread = new ClientHandlerThread(connection, this);
		signInHandlerThread.start();
	}

	@Override
	public void onClientSignInCompelted(String username, Socket connection) {
		clients.put(username, connection);
		
		// Notify to all client that there're new user
		//String data = ":newuser";
		//SenderThread senderThread = new SenderThread(connection, data);
		//senderThread.start();
	}
	
	@Override
	public void onClientListRequest(String username) {
		System.out.println("onClientListRequest");
		String response = ":list?users=";
		for(String user : clients.keySet()) {
			response += user + ",";
		}
		Socket connection = clients.get(username);
		SenderThread senderThread = new SenderThread(connection, response);
		senderThread.start();
	}
	
}















