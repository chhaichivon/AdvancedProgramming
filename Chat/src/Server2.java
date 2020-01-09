import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class Server2 {

	private JFrame frmChatServer;
	private JTextField txtMessage;
	private JLabel lblMessage;
	
	private Socket connection;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server2 window = new Server2();
					window.frmChatServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Server2() {
		initialize();
		startBindThread();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatServer = new JFrame();
		frmChatServer.setTitle("Chat Server 2");
		frmChatServer.setBounds(100, 100, 450, 300);
		frmChatServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatServer.getContentPane().setLayout(null);
		
		JLabel lblMe = new JLabel("Me");
		lblMe.setHorizontalAlignment(SwingConstants.CENTER);
		lblMe.setBounds(49, 49, 80, 16);
		frmChatServer.getContentPane().add(lblMe);
		
		txtMessage = new JTextField();
		txtMessage.setBounds(23, 77, 130, 26);
		frmChatServer.getContentPane().add(txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SenderThread senderThread = new SenderThread();
				senderThread.start();
			}
		});
		btnSend.setBounds(33, 115, 117, 29);
		frmChatServer.getContentPane().add(btnSend);
		
		JLabel lblFriend = new JLabel("Friend");
		lblFriend.setHorizontalAlignment(SwingConstants.CENTER);
		lblFriend.setBounds(235, 49, 183, 16);
		frmChatServer.getContentPane().add(lblFriend);
		
		lblMessage = new JLabel("...");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setBounds(235, 82, 183, 16);
		frmChatServer.getContentPane().add(lblMessage);
	}
	
	private void startBindThread() {
		BindThread bindThread = new BindThread();
		bindThread.start();
	}
	
	class BindThread extends Thread {
		@Override
		public void run() {
			
			try {
				// Bind to port 1234
				ServerSocket serverSocket = new ServerSocket(1234);
				
				// Listen for client
				connection = serverSocket.accept();
				
				// Start receiver thread
				ReceiverThread receiverThread = new ReceiverThread();
				receiverThread.start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class ReceiverThread extends Thread {
		@Override
		public void run() {
			try {
				// receive message from client
				Scanner streamReader = new Scanner(connection.getInputStream());
				while(true) {
					String message = streamReader.nextLine();
					
					// Update UI
					lblMessage.setText(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class SenderThread extends Thread {
		@Override
		public void run() {
			try {
				OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
				String message = txtMessage.getText();
				streamWriter.write(message + "\n");
				streamWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}











