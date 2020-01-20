

import java.net.Socket;

public interface AppCallback {

	void onLoginCompleted(boolean status, String message, Socket connection);
	
	void onFriendListRetrieved(String[] friends);
	
	void onMessageReceived(String friend, String message);
	
}
