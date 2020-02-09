import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.awt.event.MouseEvent;

public class MotoPlayer {

	private JFrame frmCar;
	private JLabel lblCar;
	private JLabel lblMoto;

	private DatagramSocket socket;
	private DatagramPacket carPlayerPacket;
	
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
		
		// Create socket and bind to port 1234
		try {
			socket = new DatagramSocket(1234);
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
				
				lblMoto.setBounds(e.getX(), e.getY(), lblMoto.getWidth(), lblMoto.getHeight());
				
				if(carPlayerPacket == null) {
					return;
				}
				
				// Send X,Y to CarPlayer
				String data = e.getX() + "#" + e.getY();
				DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), carPlayerPacket.getAddress(), carPlayerPacket.getPort());
				try {
					socket.send(packet);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
	
	class ReceiverThread extends Thread {
		@Override
		public void run() {
			
			while(true) {
				// Receive data from CarPlayer
				DatagramPacket packet = new DatagramPacket(new byte[AppConstant.BUFFER_SIZE], AppConstant.BUFFER_SIZE);
				try {
					// Listen data from CarPlayer
					socket.receive(packet);
					
					// Store the data to get address and port for sending back to CarPlayer
					carPlayerPacket = packet;
					
					// Extract X, Y from data. E.g.: 100#200
					String data = new String(packet.getData(), 0, packet.getLength());
					String[] parts = data.split("#");
					int x = Integer.parseInt(parts[0]);
					int y = Integer.parseInt(parts[1]);
					
					lblCar.setBounds(x, y, lblCar.getWidth(), lblCar.getHeight());
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
}











