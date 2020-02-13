import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class BicyclePlayer {

	private JFrame frmCar;
	private JLabel lblCar;
	private JLabel lblMoto;
	private JLabel lblBicycle;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BicyclePlayer window = new BicyclePlayer();
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
	public BicyclePlayer() {
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
				
				lblBicycle.setBounds(e.getX(), e.getY(), lblBicycle.getWidth(), lblBicycle.getHeight());
			}
		});
		frmCar.setTitle("Bicycle");
		frmCar.setBounds(100, 100, 450, 300);
		frmCar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCar.getContentPane().setLayout(null);
		
		lblCar = new JLabel("");
		lblCar.setIcon(new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Multicast/img/car.png"));
		lblCar.setBounds(39, 46, 71, 32);
		frmCar.getContentPane().add(lblCar);
		
		lblMoto = new JLabel("");
		lblMoto.setIcon(new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Multicast/img/motorcycle.png"));
		lblMoto.setBounds(18, 117, 48, 28);
		frmCar.getContentPane().add(lblMoto);
		
		lblBicycle = new JLabel("");
		lblBicycle.setIcon(new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Multicast/img/bicycle.png"));
		lblBicycle.setBounds(18, 184, 48, 28);
		frmCar.getContentPane().add(lblBicycle);
	}
}








