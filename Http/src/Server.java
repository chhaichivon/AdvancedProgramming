import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Server {
	
	public static void main(String[] args) {

		try {
			// Bind port
			ServerSocket serverSocket = new ServerSocket(1234);
			
			while(true) {
				// Listen for a client
				Socket connection = serverSocket.accept();

				// Receive request from client
				InputStream inputStream = connection.getInputStream();
				Scanner streamReader = new Scanner(inputStream);
				while(true) {
					String request = streamReader.nextLine();
					System.out.println(request);
					if(request.isEmpty()) {
						break;
					}
				}
				// Send response to client
				OutputStream outputStream = connection.getOutputStream();
				OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
				streamWriter.write("HTTP/1.1 200 OK\r\n");
				String date = new Date().toString();
				streamWriter.write("Date: " + date + "\r\n");
				streamWriter.write("Server: MITE Server\r\n");
				streamWriter.write("Content-Length: 39\r\n");
				streamWriter.write("\r\n");
				streamWriter.write("Welcome to Advanced Programming course!\r\n");
				streamWriter.write("\r\n");
				streamWriter.flush();
				
				// Close connection
				streamReader.close();
				connection.close();
			}

		} catch (IOException e) {
		e.printStackTrace();
			if(e.getMessage().toLowerCase().contains("permission")) {
				System.out.println("No permisiion");
			}
		}

	}

}
