package io.meetme.restclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.message.BasicNameValuePair;

public class RestClient {

	public enum RequestMethod {
		GET, POST
	}

	public class Response {
		public int httpCode;
		public String httpCodeMessage;
		public String content;
	}

	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;
	private HashMap<String, ContentBody> bodyValues;

	private String url;
	private HttpUriRequest httpUriRequest;

	private Response response;
	private final RequestMethod requestMethod;

	public Response getResponse() {
		return response;
	}

	public RestClient(String url, RequestMethod requestMethod) {
		this.url = url;
		this.requestMethod = requestMethod;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
		bodyValues = new HashMap<String, ContentBody>();
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void AddParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public void addBodyValue(String name, ContentBody contentBody) {
		bodyValues.put(name, contentBody);
	}

	public void buildRequest() throws Exception {
		// add parameters
		String combinedParams = "";
		if (!params.isEmpty()) {
			combinedParams += "?";
			for (NameValuePair p : params) {
				String paramString = p.getName() + "="
						+ URLEncoder.encode(p.getValue(), "UTF-8");
				if (combinedParams.length() > 1) {
					combinedParams += "&" + paramString;
				} else {
					combinedParams += paramString;
				}
			}
		}

		switch (requestMethod) {
		case GET: {
			httpUriRequest = new HttpGet(url + combinedParams);
			break;
		}
		case POST: {
			HttpPost post = new HttpPost(url + combinedParams);

			if (bodyValues.size() > 0) {
				MultipartEntity reqEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE, null,
						Charset.forName("UTF-8"));

				for (Map.Entry<String, ContentBody> entry : bodyValues
						.entrySet()) {
					reqEntity.addPart(entry.getKey(), entry.getValue());
				}
				post.setEntity(reqEntity);
			}

			httpUriRequest = post;
			break;
		}
		}
		// add headers
		for (NameValuePair h : headers) {
			httpUriRequest.addHeader(h.getName(), h.getValue());
		}
	}

	public void executeRequest() {
		// HttpClient client = new DefaultHttpClient();
		HttpClient client = HttpClientFactory.getThreadSafeClient();

		HttpResponse httpResponse;
		response = new Response();

		try {
			httpResponse = client.execute(httpUriRequest);
			response.httpCode = httpResponse.getStatusLine().getStatusCode();
			response.httpCodeMessage = httpResponse.getStatusLine()
					.getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {

				InputStream instream = entity.getContent();
				response.content = convertStreamToString(instream);

				// Closing the input stream will trigger connection release
				instream.close();
			}

		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}