import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseWheelListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.awt.event.MouseWheelEvent;

public class GameServer {

	private JFrame frmServer;
	private JLabel lblCar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameServer window = new GameServer();
					window.frmServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameServer() {
		initialize();
		NetworkThread networkThread = new NetworkThread();
		networkThread.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServer = new JFrame();
		frmServer.setTitle("Server");
		frmServer.setBounds(100, 100, 450, 300);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServer.getContentPane().setLayout(null);
		
		lblCar = new JLabel("");
		lblCar.setIcon(new ImageIcon("/Users/leapkh/Developer/java/AdvancedProgramming/Udp/img/car.png"));
		lblCar.setBounds(80, 108, 71, 32);
		frmServer.getContentPane().add(lblCar);
	}
	
	
	class NetworkThread extends Thread {
		@Override
		public void run() {
			System.out.println("Game server start...");
			try {
				DatagramSocket socket = new DatagramSocket(1234);

				byte[] buffer;
				
				while(true) {
					buffer = new byte[20];
					
					DatagramPacket request = new DatagramPacket(buffer, buffer.length);
					
					System.out.println("Waiting for request...");
					socket.receive(request);
					
					String data = new String(request.getData());
		
					String[] parts = data.trim().split("#");
					System.out.println("Request: x=" + parts[0] + ", y=" + parts[1]);
					int x = Integer.parseInt(parts[0]);
					int y = Integer.parseInt(parts[1]);
					
					lblCar.setBounds(x, y, lblCar.getWidth(), lblCar.getHeight());
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}










