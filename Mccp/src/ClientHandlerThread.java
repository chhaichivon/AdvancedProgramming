import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientHandlerThread extends Thread {

	private Socket connection;

	private static Scanner streamReader;
	private static OutputStreamWriter streamWriter;

	public ClientHandlerThread(Socket connection) {
		this.connection = connection;
	}

	@Override
	public void run() {
		try {
			// Create stream reader and writer
			streamReader = new Scanner(connection.getInputStream());
			streamWriter = new OutputStreamWriter(connection.getOutputStream());

			// Read request from client
			String request = readData();
			if (request.equals("=>list<=#")) {
				responseCurrencyList();
			} else if (request.startsWith("=>convert<=#")) {
				responseCurrencyConvert(request);
			} else {
				writeData("=>invalid<=#=>Invalid request.<=");
			}

			// Close resource
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

	private static void writeData(String data) {
		try {
			streamWriter.write(data + "\n");
			streamWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String readData() {
		return streamReader.nextLine();
	}

	private static void responseCurrencyList() {
		writeData("=>ok<=#=>USD<==>KHR<=");
	}

	private static void responseCurrencyConvert(String request) {
		final Map<String, Double> RATES = new HashMap<String, Double>() {
			{
				put("cny", 7.02608);
				put("jpy", 108.607);
				put("khr", 4060.0);
				put("sgd", 1.37);
				put("thb", 30.1812);
				put("usd", 1.0);
				put("vnd", 23170.01);
			}
		};

		// =>convert<=#=>USD<==>1<==>KHR<=
		// =>USD<==>1<==>KHR<=
		// =>KHR 10000 THB<=
		request = request.replace("=>convert<=#", "");
		String[] parts = request.split("<==>");
		String sourceCurrency = parts[0].replace("=>", "").toLowerCase(); // usd
		String destinationCurrency = parts[2].replace("<=", "").toLowerCase(); // khr
		double amount = Double.parseDouble(parts[1]); // 10000

		double sourceRate = RATES.get(sourceCurrency); // 4000
		double destinationRate = RATES.get(destinationCurrency); // 40

		double convertedAmount = amount * destinationRate / sourceRate;

		String response = "=>ok<=#=>" + convertedAmount + "<=";
		writeData(response);

	}

}
