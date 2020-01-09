import java.net.Socket;

public interface ConnectionCallback {
	
	void onConnected(Socket connection);

}
