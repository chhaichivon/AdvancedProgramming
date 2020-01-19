import java.net.Socket;

public interface AppCabllback {

	void onClientConnected(Socket connection);
	
	void onClientSignInCompelted(String username, Socket connection);
	
	void onClientListRequest(String username);
	
}
