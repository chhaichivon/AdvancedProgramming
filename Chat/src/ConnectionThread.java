import java.io.IOException;
import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ConnectionThread extends Thread {

	private ConnectionCallback callback;
	
	public ConnectionThread(ConnectionCallback callback) {
		super();
		this.callback = callback;
	}



	@Override
	public void run() {
		try {
			
			SSLContext context = SSLContext.getInstance("SSL");
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
			TrustManager[] trustAllCertificates = new TrustManager[] {trustManager};
			context.init(null, trustAllCertificates, null);
			SSLSocketFactory sslSocketFactory = context.getSocketFactory();
			SSLSocket connection = (SSLSocket) sslSocketFactory.createSocket("localhost", 1234);
			
			
			//Socket connection = new Socket("localhost", 1234);
			
			// Notify to Main thread
			callback.onConnected(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
