import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class MotoPlayer {

	private JFrame frmCar;
	private JLabel lblCar;
	private JLabel lblMoto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MotoPlayer window = new MotoPlayer();
					window.frmCar.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MotoPlayer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCar = new JFrame();
		frmCar.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				
				lblMoto.setBounds(e.getX(), e.getY(), lblMoto.getWidth(), lblMoto.getHeight());
				
			}
		});
		frmCar.setTitle("Moto");
		frmCar.setBounds(100, 100, 450, 300);
		frmCar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCar.getContentPane().setLayout(null);
		
		lblCar = new JLabel("");
		lblCar.setIcon(new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Udp/img/car.png"));
		lblCar.setBounds(39, 46, 71, 32);
		frmCar.getContentPane().add(lblCar);
		
		lblMoto = new JLabel("");
		lblMoto.setIcon(new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Udp/img/motorcycle.png"));
		lblMoto.setBounds(18, 117, 48, 28);
		frmCar.getContentPane().add(lblMoto);
	}
}
