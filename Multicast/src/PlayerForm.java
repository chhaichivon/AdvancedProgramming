import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.awt.event.MouseEvent;

public class PlayerForm {

	private JFrame frmPlayer;
	
	private int vehicleType;
	private String name;
	private JLabel lblCurrentPlayer;
	
	private HashMap<String, JLabel> players;
	
	private MulticastSocket socket;

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
		// Initialize players HashMap
		players = new HashMap<String, JLabel>();
		try {
			// Initialize socket
			socket = new MulticastSocket(Constants.GROUP_PORT);
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
		frmPlayer = new JFrame();
		frmPlayer.setTitle("Player");
		frmPlayer.setBounds(100, 100, 700, 500);
		frmPlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPlayer.getContentPane().setLayout(null);
		frmPlayer.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				lblCurrentPlayer.setBounds(e.getX(), e.getY(), lblCurrentPlayer.getWidth(), lblCurrentPlayer.getHeight());
				try {
					// Send its XY to the group
					String data = name + "#" + vehicleType + "#" + e.getX() + "#" + e.getY();
					InetAddress groupAddress = InetAddress.getByName(Constants.GROUP_ADDRESS);
					DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), groupAddress, Constants.GROUP_PORT);
					socket.send(packet);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		lblCurrentPlayer = new JLabel("Player");
		lblCurrentPlayer.setIcon(new ImageIcon(System.getProperty("user.dir") + "/img/bicycle.png"));
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
	
	private ImageIcon getVehicleIcon(int vehicleType) {
		if(vehicleType ==0) {
			return new ImageIcon(System.getProperty("user.dir") + "/img/bicycle.png");
		} else if(vehicleType == 1) {
			return new ImageIcon(System.getProperty("user.dir") + "/img/motorcycle.png");
		} else {
			return new ImageIcon(System.getProperty("user.dir") + "/img/car.png");
		}
	}
	
	private class ReceiverThread extends Thread {
		@Override
		public void run() {
			try {
				// Join group
				InetAddress groupAddress = InetAddress.getByName(Constants.GROUP_ADDRESS);
				socket.joinGroup(groupAddress);
				
				while(true) {
					// Receive data from group
					DatagramPacket packet = new DatagramPacket(new byte[Constants.BUFFER_SIZE], Constants.BUFFER_SIZE);
					socket.receive(packet);
					
					// Extract data
					String data = new String(packet.getData(), 0, packet.getLength());
					processData(data);
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void processData(String data) {
			try {
			// E.g: data = abc#1#100#200
			String[] parts = data.split("#");
			if(parts.length != 4) {
				return;
			}
			
			String playerName = parts[0];
			int playerType = Integer.parseInt(parts[1]);
			int x = Integer.parseInt(parts[2]);
			int y = Integer.parseInt(parts[3]);
			
			// Check if player exists
			JLabel player = players.get(playerName);
			if(player == null) {
				player = new JLabel(playerName);
				player.setIcon(getVehicleIcon(playerType));
				frmPlayer.getContentPane().add(player);
				players.put(playerName, player);
			}
			
			// Update position
			player.setBounds(x, y, 150, 32);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}













