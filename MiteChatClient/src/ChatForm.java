
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class ChatForm implements AppCallback {

	private JFrame frmMiteChat;
	private JList lstFriends;
	private JTextField txtMessage;
	private JLabel lblWelcome;
	private JLabel lblFriend;
	private JButton btnSend;
	private JLabel lblMessage;

	/**
	 * Create the application.
	 */
	public ChatForm() {
		initialize();
		lblWelcome.setText("Welcome, " + AppData.getInstance().getUsername().toUpperCase());
		
		lblFriend = new JLabel("");
		lblFriend.setHorizontalAlignment(SwingConstants.CENTER);
		lblFriend.setBounds(207, 149, 322, 16);
		frmMiteChat.getContentPane().add(lblFriend);
		startServerHandlerThread();
		loadFriendList();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMiteChat = new JFrame();
		frmMiteChat.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				startLogout();
			}
		});
		frmMiteChat.setTitle("MITE Chat");
		frmMiteChat.setBounds(100, 100, 550, 459);
		frmMiteChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMiteChat.getContentPane().setLayout(null);

		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startLogout();
			}
		});
		btnLogout.setBounds(37, 388, 117, 29);
		frmMiteChat.getContentPane().add(btnLogout);

		lstFriends = new JList();
		lstFriends.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				onListItemSelected();
			}
		});
		lstFriends.setBounds(22, 149, 141, 212);
		frmMiteChat.getContentPane().add(lstFriends);

		JLabel lblFriends = new JLabel("Friends");
		lblFriends.setHorizontalAlignment(SwingConstants.CENTER);
		lblFriends.setBounds(62, 121, 61, 16);
		frmMiteChat.getContentPane().add(lblFriends);

		lblMessage = new JLabel("...");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setBounds(207, 176, 322, 16);
		frmMiteChat.getContentPane().add(lblMessage);

		btnSend = new JButton("Send");
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		btnSend.setBounds(412, 221, 117, 29);
		frmMiteChat.getContentPane().add(btnSend);

		txtMessage = new JTextField();
		txtMessage.setBounds(207, 221, 193, 26);
		frmMiteChat.getContentPane().add(txtMessage);
		txtMessage.setColumns(10);
		
		lblWelcome = new JLabel("Welcome, ");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(136, 27, 264, 16);
		frmMiteChat.getContentPane().add(lblWelcome);
	}

	/**
	 * Show frame
	 */

	public void show() {
		frmMiteChat.setVisible(true);
	}

	private void startLogout() {
		String request = ":logout";
		SenderThread senderThread = new SenderThread(AppData.getInstance().getConnection(), request);
		senderThread.start();
		frmMiteChat.dispose();
		LoginForm loginForm = new LoginForm();
		loginForm.show();
	}

	private void startServerHandlerThread() {
		ServerHandlerThread serverHandlerThread = new ServerHandlerThread(AppData.getInstance().getConnection(), this);
		serverHandlerThread.start();
	}

	private void loadFriendList() {
		String request = ":list";
		SenderThread senderThread = new SenderThread(AppData.getInstance().getConnection(), request);
		senderThread.start();
	}

	@Override
	public void onLoginCompleted(boolean status, String message, Socket connection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFriendListRetrieved(String[] friends) {
		System.out.println("onFriendListRetrieved");
		// Display list to user
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for (String friend : friends) {
			if(!friend.equals(AppData.getInstance().getUsername())) {
				listModel.addElement(friend.trim());
			}
		}
		lstFriends.setModel(listModel);
	}
	
	@Override
	public void onMessageReceived(String friend, String message) {
		lblFriend.setText(friend);
		lstFriends.setSelectedValue(friend, true);
		lblMessage.setText(message);
	}
	
	private void onListItemSelected() {
		String username = lstFriends.getSelectedValue().toString();
		lblFriend.setText(username);
		btnSend.setEnabled(true);
	}
	
	private void sendMessage() {
		String message = txtMessage.getText();
		String friend = lstFriends.getSelectedValue().toString();
		String request = String.format(":chat#me=%s&friend=%s&message=%s", AppData.getInstance().getUsername(), friend, message);
		Socket connection = AppData.getInstance().getConnection();
		SenderThread senderThread = new SenderThread(connection, request);
		senderThread.start();
		txtMessage.setText("");
	}
}









