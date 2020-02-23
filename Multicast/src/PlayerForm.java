import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class PlayerForm {

	private JFrame frmPlayer;
	
	private int vehicleType;
	private String name;
	private JLabel lblCurrentPlayer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerForm window = new PlayerForm();
					window.frmPlayer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PlayerForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPlayer = new JFrame();
		frmPlayer.setTitle("Player");
		frmPlayer.setBounds(100, 100, 700, 500);
		frmPlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPlayer.getContentPane().setLayout(null);
		frmPlayer.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				lblCurrentPlayer.setBounds(e.getX(), e.getY(), lblCurrentPlayer.getWidth(), lblCurrentPlayer.getHeight());
			}
		});
		
		lblCurrentPlayer = new JLabel("Player");
		lblCurrentPlayer.setIcon(new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Multicast/img/bicycle.png"));
		lblCurrentPlayer.setBounds(98, 59, 150, 32);
		frmPlayer.getContentPane().add(lblCurrentPlayer);
	}
	
	public void show() {
		// Set current player and form title
		String vehicle;
		if(vehicleType ==0) {
			vehicle = "Bicycle";
		} else if(vehicleType == 1) {
			vehicle = "Moto";
		} else {
			vehicle = "Car";
		}
		frmPlayer.setTitle(name + "(" + vehicle + ")");
		lblCurrentPlayer.setText(name);
		lblCurrentPlayer.setIcon(getVehicleIcon(vehicleType));
		
		// Show Form
		frmPlayer.setVisible(true);

	}

	public void setInfo(int vehicleType, String name) {
		this.vehicleType = vehicleType;
		this.name = name;
	}
	
	private void addVehicle(int vehicleType, String name) {
		JLabel lblOtherPlayer = new JLabel(name);
		lblOtherPlayer.setIcon(getVehicleIcon(vehicleType));
		lblOtherPlayer.setBounds(350, 250, 150, 32);
		frmPlayer.getContentPane().add(lblOtherPlayer);
	}
	
	private ImageIcon getVehicleIcon(int vehicleType) {
		if(vehicleType ==0) {
			return new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Multicast/img/bicycle.png");
		} else if(vehicleType == 1) {
			return new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Multicast/img/motorcycle.png");
		} else {
			return new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Multicast/img/car.png");
		}
	}

}
