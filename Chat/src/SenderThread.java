import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SenderThread extends Thread {

	private Socket connection;
	private String message;
	
	
	
	public SenderThread(Socket connection, String message) {
		super();
		this.connection = connection;
		this.message = message;
	}

	@Override
	public void run() {
		try {
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write(message + "\n");
			streamWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
