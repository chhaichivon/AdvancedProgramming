import java.awt.EventQueue;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Client implements AppCallback {

	private JFrame frmClient;
	
	private Socket connection;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JList lstFriends;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frmClient.setVisible(true);
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
		frmClient = new JFrame();
		frmClient.setTitle("Client");
		frmClient.setBounds(100, 100, 472, 477);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClient.getContentPane().setLayout(null);
		
		lstFriends = new JList();
		lstFriends.setBounds(27, 264, 139, 172);
		frmClient.getContentPane().add(lstFriends);
		
		JLabel lblFriends = new JLabel("Friends");
		lblFriends.setHorizontalAlignment(SwingConstants.CENTER);
		lblFriends.setBounds(61, 221, 61, 16);
		frmClient.getContentPane().add(lblFriends);
		
		JLabel label3 = new JLabel("Message");
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		label3.setBounds(246, 221, 125, 16);
		frmClient.getContentPane().add(label3);
		
		JLabel lblMessage = new JLabel("Message");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setBounds(199, 287, 241, 16);
		frmClient.getContentPane().add(lblMessage);
		
		JTextField txtMessage = new JTextField();
		txtMessage.setBounds(192, 341, 130, 26);
		frmClient.getContentPane().add(txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(338, 341, 117, 29);
		frmClient.getContentPane().add(btnSend);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(61, 29, 105, 16);
		frmClient.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(61, 72, 105, 16);
		frmClient.getContentPane().add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(178, 24, 130, 26);
		frmClient.getContentPane().add(txtUsername);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(178, 67, 130, 26);
		frmClient.getContentPane().add(txtPassword);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startSignInThread();
			}
		});
		btnSignIn.setBounds(323, 44, 117, 29);
		frmClient.getContentPane().add(btnSignIn);
	}

	private void startConnectionThread() {
		ConnectionThread connectionThread = new ConnectionThread("localhost", this);
		connectionThread.start();
	}
	
	private void startSignInThread() {
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		SignInThread signInThread = new SignInThread(connection, username, password, this);
		signInThread.start();
	}
	
	@Override
	public void onConnectionConnected(Socket connection) {
		System.out.println("onConnectionConnected");
		this.connection = connection;
	}

	@Override
	public void onSignInCompleted(boolean success) {
		System.out.println("onSignInCompleted");
		// retrieve user list
		ListRetriverThread listRetriverThread = new ListRetriverThread(connection, this);
		listRetriverThread.start();
	}

	@Override
	public void onUserListReceived(String[] users) {
		System.out.println("onUserListReceived");
		// Display list to user
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(String user:users) {
			listModel.addElement(user);
		}
		lstFriends.setModel(listModel);
	}

	@Override
	public void onMessageSent(boolean success) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageReceived(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignOutCompleted() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onNewUserCame() {
		//ListRetriverThread listRetriverThread = new ListRetriverThread(connection, this);
		//listRetriverThread.start();
	}
}
