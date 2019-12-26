import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {

		try {
			// Bind to port
			ServerSocket serverSocket = new ServerSocket(1234);

			while (true) {
				// Listen for client
				System.out.println("Wait for client...");
				Socket connection = serverSocket.accept();

				// Create a thread to handle a client
				//// Using method 1: subclassing
				ClientHandlerThread clientHandlerThread = new ClientHandlerThread(connection);
				clientHandlerThread.start();

				//// Using method 2: using Runnable
				// ClientHandlerRunnable clientHandlerRunnable = new
				//// ClientHandlerRunnable(connection);
				// Thread clientHandlerThread2 = new Thread(clientHandlerRunnable);
				// clientHandlerThread2.start();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
