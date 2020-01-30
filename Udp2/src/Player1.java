import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class Player1 {

	private JFrame frmPlayer;
	private JLabel lblCar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Player1 window = new Player1();
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
	public Player1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPlayer = new JFrame();
		frmPlayer.setTitle("Player 1");
		frmPlayer.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				
				System.out.println("Mouse move: x=" + e.getX() + ", y=" + e.getY());
				lblCar.setBounds(e.getX(), e.getY(), lblCar.getWidth(), lblCar.getHeight());
				
				
			}
		});
		frmPlayer.setBounds(100, 100, 450, 300);
		frmPlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPlayer.getContentPane().setLayout(null);
		
		lblCar = new JLabel("");
		lblCar.setIcon(new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Udp2/img/car.png"));
		lblCar.setBounds(51, 50, 71, 32);
		frmPlayer.getContentPane().add(lblCar);
	}
}
