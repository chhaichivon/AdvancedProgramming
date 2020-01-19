import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.MouseMotionAdapter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class GameClient {

	private JFrame frmClient;
	private JLabel lblCar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameClient window = new GameClient();
					window.frmClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClient = new JFrame();
		frmClient.setTitle("Client");
		frmClient.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				
				System.out.println("Moving: x=" + e.getX() + ", y=" + e.getY() );
				lblCar.setBounds(e.getX(), e.getY(), lblCar.getWidth(), lblCar.getHeight());
				
				String data = e.getX() + "#" + e.getY();
				NetworkThread networkThread = new NetworkThread(data);
				networkThread.start();
			}
		});
		frmClient.setBounds(100, 100, 450, 300);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClient.getContentPane().setLayout(null);
		
		lblCar = new JLabel("");
		lblCar.setIcon(new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Udp/img/car.png"));
		lblCar.setBounds(80, 108, 71, 32);
		frmClient.getContentPane().add(lblCar);
	}
	
	class NetworkThread extends Thread {
		
		String data;
		
		public NetworkThread(String data) {
			super();
			this.data = data;
		}

		@Override
		public void run() {
		
			try {
				sleep(2000);
				DatagramSocket socket = new DatagramSocket(0);
				socket.setSoTimeout(3000);
				byte[] buffer = data.getBytes();
				
				InetAddress address = InetAddress.getByName("localhost");
				DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, 1234);
				
				System.out.println("Sending request with lenght: " + buffer.length);
				socket.send(request);
				System.out.println("Success");
				
				socket.close();
				
			} catch (Exception e) {
				System.out.println("Fail");
				e.printStackTrace();
			}
			
		}
		
	}
	
}




