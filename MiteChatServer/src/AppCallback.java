

import java.net.Socket;

public interface AppCallback {

	void onClientLoggedIn(String username, Socket connection);
	
	void onClientLoggedOut(String username);
	
	void onFriendListRequest(String username);
	
	void onChatRequest(String me, String friend, String message);
	
}
