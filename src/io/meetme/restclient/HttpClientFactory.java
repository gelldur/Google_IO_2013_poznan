package io.meetme.restclient;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpClientFactory {

	private static int TIMEOUT_CONNECTION = 15000;
	private static int TIMEOUT_DATA = 30000;

	private static DefaultHttpClient client;

	public synchronized static DefaultHttpClient getThreadSafeClient() {
		if (client != null)
			return client;
		client = new DefaultHttpClient();
		ClientConnectionManager mgr = client.getConnectionManager();
		HttpParams params = client.getParams();

		HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONNECTION);
		HttpConnectionParams.setSoTimeout(params, TIMEOUT_DATA);

		client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,
				mgr.getSchemeRegistry()), params);
		return client;

	}
}