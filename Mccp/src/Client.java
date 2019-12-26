import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		try {
			// Open connection
			Socket connection = new Socket("localhost", 1234);
			Writer streamWriter = new OutputStreamWriter(connection.getOutputStream());
			Scanner streamReader = new Scanner(connection.getInputStream());

			// Read input from keyboard
			Scanner keyboardReader = new Scanner(System.in);
			System.out.println("**********************");
			System.out.println("    MCCP Client   ");
			System.out.println("**********************");
			System.out.println("Please select an action:");
			System.out.println("  1, List supported currencies");
			System.out.println("  2, Convert currencies");
			while(true) {
				System.out.print("Enter number: ");
				String actionNumber = keyboardReader.nextLine();
				if (actionNumber.equals("1")) {
					requestCurrencyList(streamWriter, streamReader);
					break;
				} else if(actionNumber.equals("2")) {
					processConvertRequest(streamWriter, streamReader, keyboardReader);
					break;
				} else {
					System.out.println("Invalid input.");
				}				
			}

			// Close connection
			// streamReader.close();
			keyboardReader.close();
			connection.close();
			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void requestCurrencyList(Writer streamWriter, Scanner streamReader) {
		try {
			streamWriter.write("=>list<=#\n");
			streamWriter.flush();

			String response = streamReader.nextLine();
			String[] parts = response.split("#");
			if (parts.length != 2) {
				System.out.println("Unexpected error.");
				return;
			}
			String status = parts[0];
			if (status.equals("=>ok<=")) {
				processListResponse(parts[1]);
			} else if (status.equals("=>invalid<=") || status.equals("=>error<=") || status.equals("=>unknown<=")) {
				processNonOkResponse(parts[1]);
			} else {
				System.out.println("Unexpected error.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void processConvertRequest(Writer streamWriter, Scanner streamReader, Scanner keyboardReader) {
		System.out.print("Please enter source currency: ");
		String sourceCurrency = keyboardReader.nextLine();
		System.out.print("Please enter amount: ");
		String amount = keyboardReader.nextLine();
		System.out.print("Please enter destination currency: ");
		String destinationCurrency = keyboardReader.nextLine();
		String request = String.format("=>convert<=#=>%s<==>%s<==>%s<=\n", sourceCurrency, amount, destinationCurrency);
		try {
			streamWriter.write(request);
			streamWriter.flush();
			
			String response = streamReader.nextLine();
			String[] parts = response.split("#");
			if (parts.length != 2) {
				System.out.println("Unexpected error.");
				return;
			}
			String status = parts[0];
			if (status.equals("=>ok<=")) {
				processConvertResponse(parts[1], destinationCurrency);
			} else if (status.equals("=>invalid<=") || status.equals("=>error<=") || status.equals("=>unknown<=")) {
				processNonOkResponse(parts[1]);
			} else {
				System.out.println("Unexpected error.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void processListResponse(String data) {
		String[] dataValues = data.split("=>");
		for(int i=1; i<dataValues.length; i++) {
			String value = dataValues[i].replace("<=", "").toUpperCase();
			System.out.println(" - " + value);
		}
	}

	private static void processNonOkResponse(String data) {
		String message = data.replace("=>", "").replace("<=", "");
		System.out.println(message);
	}
	
	private static void processConvertResponse(String data, String destinationCurrency) {
		String message = data.replace("=>", "").replace("<=", "");
		double amount = Double.parseDouble(message);
		String formatedAmount = new DecimalFormat("#,###.00").format(amount);
		String displayMessage = String.format("Result: %s %s", formatedAmount, destinationCurrency.toUpperCase());
		System.out.println(displayMessage);
	}

}
