import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginForm {

	private JFrame frmMiteGame;
	private JTextField txtName;
	private JComboBox cbVehicle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Set IPv4 for Mac
		System.setProperty("java.net.preferIPv4Stack", "true");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm window = new LoginForm();
					window.frmMiteGame.setVisible(true);
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
		frmMiteGame = new JFrame();
		frmMiteGame.setTitle("MITE Game");
		frmMiteGame.setBounds(100, 100, 450, 300);
		frmMiteGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMiteGame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(198, 45, 61, 16);
		frmMiteGame.getContentPane().add(lblNewLabel);
		
		JLabel lblChooseVeh = new JLabel("Choose vehicle");
		lblChooseVeh.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseVeh.setBounds(71, 108, 125, 16);
		frmMiteGame.getContentPane().add(lblChooseVeh);
		
		JLabel lblTypeYourName = new JLabel("Type your name");
		lblTypeYourName.setHorizontalAlignment(SwingConstants.CENTER);
		lblTypeYourName.setBounds(71, 156, 125, 16);
		frmMiteGame.getContentPane().add(lblTypeYourName);
		
		cbVehicle = new JComboBox();
		cbVehicle.setModel(new DefaultComboBoxModel(new String[] {"Bicycle", "Moto", "Car"}));
		cbVehicle.setBounds(208, 104, 115, 27);
		frmMiteGame.getContentPane().add(cbVehicle);
		
		txtName = new JTextField();
		txtName.setBounds(208, 151, 179, 26);
		frmMiteGame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int vehicleType = cbVehicle.getSelectedIndex();
				String name = txtName.getText();
				
				PlayerForm playerForm = new PlayerForm();
				playerForm.setInfo(vehicleType, name);
				playerForm.show();
				frmMiteGame.dispose();
				
			}
		});
		btnPlay.setBounds(174, 207, 117, 29);
		frmMiteGame.getContentPane().add(btnPlay);
	}
}
