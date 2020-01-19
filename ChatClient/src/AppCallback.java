import java.net.Socket;

public interface AppCallback {

	void onConnectionConnected(Socket connection);
	
	void onSignInCompleted(boolean success);
	
	void onUserListReceived(String[] users);
	
	void onMessageSent(boolean success);
	
	void onMessageReceived(String message);
	
	void onSignOutCompleted();
	
	void onNewUserCame();
	
}
