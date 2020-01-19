import java.io.OutputStreamWriter;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Client2 {

	// Ignore server's certificate verifying
	public static void main(String[] args) {

		X509TrustManager trustManager = new X509TrustManager() {

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub

			}
		};

		TrustManager[] allTrustedManager = new TrustManager[] { trustManager };

		// Create context
		try {
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, allTrustedManager, null);
			SSLSocketFactory socketFactory = context.getSocketFactory();

			// Connect to server
			SSLSocket connection = (SSLSocket) socketFactory.createSocket("localhost", 1234);

			// Send request to server
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write("Hi server\n");
			streamWriter.flush();

			// Read response from server
			Scanner streamReader = new Scanner(connection.getInputStream());
			String response = streamReader.nextLine();
			System.out.println("Response: " + response);

			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
