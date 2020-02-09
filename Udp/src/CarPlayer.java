import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.awt.event.MouseEvent;

public class CarPlayer {

	private JFrame frmCar;
	private JLabel lblCar;
	private JLabel lblMoto;
	
	private DatagramSocket socket;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CarPlayer window = new CarPlayer();
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
	public CarPlayer() {
		initialize();
		
		// Create socket
		try {
			socket = new DatagramSocket(0);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		// Start ReceiverThread
		ReceiverThread thread = new ReceiverThread();
		thread.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCar = new JFrame();
		frmCar.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				
				lblCar.setBounds(e.getX(), e.getY(), lblCar.getWidth(), lblCar.getHeight());
				
				// Send X,Y to MotoPlayer
				try {
					String data = e.getX() + "#" + e.getY();
					InetAddress motoPlayerAddress = InetAddress.getByName("localhost");
					DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), motoPlayerAddress, 1234);
					socket.send(packet);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		frmCar.setTitle("Car");
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
	
	class ReceiverThread extends Thread {
		@Override
		public void run() {
			while(true) {
				// Receive data from CarPlayer
				DatagramPacket packet = new DatagramPacket(new byte[AppConstant.BUFFER_SIZE], AppConstant.BUFFER_SIZE);
				try {
					// Listen data from CarPlayer
					socket.receive(packet);
					
					// Extract X, Y from data. E.g.: 100#200
					String data = new String(packet.getData(), 0, packet.getLength());
					String[] parts = data.split("#");
					int x = Integer.parseInt(parts[0]);
					int y = Integer.parseInt(parts[1]);
					
					lblMoto.setBounds(x, y, lblMoto.getWidth(), lblMoto.getHeight());
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}








