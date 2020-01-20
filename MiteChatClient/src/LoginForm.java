

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class LoginForm implements AppCallback {

	private JFrame frmMiteChat;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JButton btnLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm window = new LoginForm();
					window.frmMiteChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMiteChat = new JFrame();
		frmMiteChat.setTitle("MITE Chat");
		frmMiteChat.setBounds(100, 100, 450, 300);
		frmMiteChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMiteChat.getContentPane().setLayout(null);
		
		JLabel lblLoginForm = new JLabel("Login Form");
		lblLoginForm.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginForm.setBounds(168, 48, 113, 16);
		frmMiteChat.getContentPane().add(lblLoginForm);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(78, 107, 113, 16);
		frmMiteChat.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(78, 142, 113, 16);
		frmMiteChat.getContentPane().add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(203, 102, 130, 26);
		frmMiteChat.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(203, 137, 130, 26);
		frmMiteChat.getContentPane().add(txtPassword);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startLogin();
			}
		});
		btnLogin.setBounds(168, 200, 117, 29);
		frmMiteChat.getContentPane().add(btnLogin);
	}
	
	/**
	 * Show frame
	 */
	
	public void show() {
		frmMiteChat.setVisible(true);
	}
	
	private void startLogin() {
		btnLogin.setText("Processing...");
		btnLogin.setEnabled(false);
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		LoginThread loginThread = new LoginThread(username, password, this);
		loginThread.start();
	}

	@Override
	public void onLoginCompleted(boolean status, String message, Socket connection) {
		if(status) {
			AppData.getInstance().setUsername(txtUsername.getText());
			AppData.getInstance().setConnection(connection);
			frmMiteChat.dispose();
			ChatForm chatForm = new ChatForm();
			chatForm.show();
		} else {
			btnLogin.setText("Login");
			btnLogin.setEnabled(true);
			JOptionPane.showMessageDialog(null, message);
		}
	}

	@Override
	public void onFriendListRetrieved(String[] friends) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageReceived(String username, String message) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
