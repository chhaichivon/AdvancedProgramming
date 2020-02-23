import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.awt.event.MouseEvent;

public class CarPlayer {

	private JFrame frmCar;
	private JLabel lblCar;
	private JLabel lblMoto;
	private JLabel lblBicycle;

	private MulticastSocket socket;

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
		// Set IPv4 for Mac
		System.setProperty("java.net.preferIPv4Stack", "true");
		
		initialize();

		try {
			// Initialize the socket
			socket = new MulticastSocket(Constants.GROUP_PORT);

			// Join group
			// Set IPv4 for Mac
			System.setProperty("java.net.preferIPv4Stack", "true");
			InetAddress groupAddress = InetAddress.getByName(Constants.GROUP_ADDRESS);
			socket.joinGroup(groupAddress);

			// Start ReceiverThread
			ReceiverThread thread = new ReceiverThread();
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

				try {
					// Send XY to group. E.g.: bicycle#100#200
					String data = "car#" + e.getX() + "#" + e.getY();
					InetAddress groupAddress = InetAddress.getByName(Constants.GROUP_ADDRESS);
					DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), groupAddress,
							Constants.GROUP_PORT);
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

	private class ReceiverThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					// Receive dat from group
					DatagramPacket packet = new DatagramPacket(new byte[Constants.BUFFER_SIZE], Constants.BUFFER_SIZE);
					socket.receive(packet);

					// Extract data. E.g.: bicycle#100#200
					String data = new String(packet.getData(), 0, packet.getLength());
					String[] parts = data.split("#");
					int x = Integer.parseInt(parts[1]);
					int y = Integer.parseInt(parts[2]);
					String ip = packet.getAddress().toString();
					if (parts[0].equals("bicycle")) {
						lblBicycle.setText(ip);
						lblBicycle.setBounds(x, y, lblBicycle.getWidth(), lblBicycle.getHeight());
					} else if (parts[0].equals("moto")) {
						lblMoto.setText(ip);
						lblMoto.setBounds(x, y, lblMoto.getWidth(), lblMoto.getHeight());
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
