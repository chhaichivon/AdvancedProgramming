import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ListRetriverThread extends Thread {

	private Socket connection;
	private AppCallback callback;
	
	
	
	public ListRetriverThread(Socket connection, AppCallback callback) {
		super();
		this.connection = connection;
		this.callback = callback;
	}



	@Override
	public void run() {

		String data = ":list\n";
		
		try {
			System.out.println("retrieve list");
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write(data);
			streamWriter.flush();
			
			Scanner streamRreader = new Scanner(connection.getInputStream());
			String response = streamRreader.nextLine();
			// :list?users=abc,xyz
			response = response.replace(":list?users=", "");	// abc,xyz
			String[] users = response.split(",");
			callback.onUserListReceived(users);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}











