import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Client implements ConnectionCallback, MessageCallback {

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
					Client window = new Client();
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
	public Client() {
		initialize();
		startConnectionThread();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatServer = new JFrame();
		frmChatServer.setTitle("Chat Client");
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
				sendMessage();
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
	
	private void startConnectionThread() {
		ConnectionThread connectionThread = new ConnectionThread(this);
		connectionThread.start();
	}

	@Override
	public void onConnected(Socket connection) {
		this.connection = connection;
		// start receiver thread
		startReceiverThread(connection);
	}
	
	private void startReceiverThread(Socket connection) {
		ReceiverThread receiverThread = new ReceiverThread(connection, this);
		receiverThread.start();
	}

	@Override
	public void onMessageReceived(String message) {
		lblMessage.setText(message);
	}
	
	private void sendMessage() {
		String message = txtMessage.getText();
		SenderThread senderThread = new SenderThread(connection, message);
		senderThread.start();
	}
	
}











