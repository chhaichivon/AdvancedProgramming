import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class ClientGui {

	private JFrame frame;
	private JTextField txtFrom;
	private JLabel lblSource;
	private JTextField txtTo;
	private JLabel lblTo;
	private JButton btnConvert;
	private JLabel lblResult;
	private JLabel lblAmount;
	private JTextField txtAmount;
	
	private static Scanner streamReader;
	private static OutputStreamWriter streamWriter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGui window = new ClientGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblMccpClient = new JLabel("MCCP Client");
		lblMccpClient.setHorizontalAlignment(SwingConstants.CENTER);
		lblMccpClient.setBounds(155, 36, 124, 16);
		frame.getContentPane().add(lblMccpClient);
		
		JButton btnShowSupportedCurrencies = new JButton("Show Supported Currencies");
		btnShowSupportedCurrencies.setBounds(113, 96, 210, 29);
		frame.getContentPane().add(btnShowSupportedCurrencies);
		
		txtFrom = new JTextField();
		txtFrom.setBounds(6, 194, 74, 26);
		frame.getContentPane().add(txtFrom);
		txtFrom.setColumns(10);
		
		lblSource = new JLabel("From");
		lblSource.setHorizontalAlignment(SwingConstants.CENTER);
		lblSource.setBounds(6, 166, 74, 16);
		frame.getContentPane().add(lblSource);
		
		txtTo = new JTextField();
		txtTo.setColumns(10);
		txtTo.setBounds(180, 194, 74, 26);
		frame.getContentPane().add(txtTo);
		
		lblTo = new JLabel("To");
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setBounds(155, 168, 124, 16);
		frame.getContentPane().add(lblTo);
		
		btnConvert = new JButton("Convert");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CurrencyConverterThread currencyConverterThread = new CurrencyConverterThread();
				currencyConverterThread.start();
				
				
			}
		});
		btnConvert.setBounds(277, 180, 117, 29);
		frame.getContentPane().add(btnConvert);
		
		lblResult = new JLabel("Result:");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setBounds(277, 221, 124, 16);
		frame.getContentPane().add(lblResult);
		
		lblAmount = new JLabel("Amount");
		lblAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmount.setBounds(89, 166, 74, 16);
		frame.getContentPane().add(lblAmount);
		
		txtAmount = new JTextField();
		txtAmount.setColumns(10);
		txtAmount.setBounds(92, 194, 74, 26);
		frame.getContentPane().add(txtAmount);
	}
	
	private void writeData(String data) {
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
	
	class CurrencyConverterThread extends Thread {
		
		@Override
		public void run() {
			// Get data from user input
			String fromCurrency = txtFrom.getText();
			String toCurrency = txtTo.getText();
			String amount = txtAmount.getText();
			
			try {
				// Connect to server
				Socket connection = new Socket("localhost", 1234);
				
				// Create stream reader and writer
				streamReader = new Scanner(connection.getInputStream());
				streamWriter = new OutputStreamWriter(connection.getOutputStream());
				
				// Send request to server
				String request = String.format("=>convert<=#=>%s<==>%s<==>%s<=", fromCurrency, amount, toCurrency);
				writeData(request);
				
				// Read response from server
				String response = readData();  // =>ok<=#=>4060.0<=
				String[] parts = response.split("<=#=>");  // =>ok      4060.0<=
				String result = parts[1].replace("<=", "");
				lblResult.setText("Result: " + result);
				
				// Close connection
				connection.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
}





