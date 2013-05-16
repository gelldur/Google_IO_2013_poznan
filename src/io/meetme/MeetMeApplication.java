package io.meetme;

import io.meetme.database.DatabaseManager;
import io.meetme.database.DatabaseManager.OnUserAddedListener;
import io.meetme.database.User;
import io.meetme.util.AndroidUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

public class MeetMeApplication extends Application implements
	OnUserAddedListener {

    public static MeetMeApplication instance;

    @Override
    public void onCreate() {
	super.onCreate();

	instance = this;

	DatabaseManager.init(this, R.xml.database);
	DatabaseManager.setOnUserAddedListener(this);
	DatabaseManager databaseManager = DatabaseManager.getInstance();

	databaseManager.add(User.meetUser("123", "Test"));

	Log.d(this.toString(), "POINTS: " + databaseManager.getPoints());
    }

    @Override
    public void onAdded(User user) {
	// TODO test it when server will be up :D
	new Thread(new StatSender()).start();
    }

    public static class StatSender implements Runnable {

	private static String convertStreamToString(InputStream inputStream)
		throws IOException {
	    BufferedReader r = new BufferedReader(new InputStreamReader(
		    inputStream));
	    StringBuilder total = new StringBuilder();
	    String line;
	    while ((line = r.readLine()) != null) {
		total.append(line);
	    }
	    return total.toString();
	}

	@Override
	public void run() {

	    BasicHttpParams httpParameters = new BasicHttpParams();
	    HttpConnectionParams.setSoTimeout(httpParameters, 0);
	    HttpConnectionParams.setSocketBufferSize(httpParameters, 2048);

	    // Adding pair "code" -> encoded string in base64
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	    nameValuePairs.add(new BasicNameValuePair("id", AndroidUtils
		    .getUUID(MeetMeApplication.instance)));
	    nameValuePairs.add(new BasicNameValuePair("points", String
		    .valueOf(DatabaseManager.getInstance().getPoints())));

	    SharedPreferences sharedPreferences = PreferenceManager
		    .getDefaultSharedPreferences(MeetMeApplication.instance);

	    nameValuePairs.add(new BasicNameValuePair("username",
		    sharedPreferences.getString(PreferenceKeys.USERNAME_STR,
			    "NO_NAME_:)")));

	    // Setting POST method
	    HttpPost post;
	    try {
		post = new HttpPost(new URI(
			"http://googleio.vador.mydevil.net/saverank/"));

		// Setting entity with "code" -> encoded string to POST method
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		// Obtaining response form server
		HttpResponse httpResponse = new DefaultHttpClient(
			httpParameters).execute(post);

		HttpEntity responseEntity = httpResponse.getEntity();

		if (responseEntity == null) {
		    Log.e("LOG", "API returned an empty response!");
		    return;
		}

		InputStream is;
		String responseString = null;
		is = responseEntity.getContent();
		responseString = convertStreamToString(is);

		Log.d("Log", " responseString " + responseString);

	    } catch (Exception e) {
		e.printStackTrace();
		SystemClock.sleep(300000);
		// Pseudo recursive call :D
		StatSender statSender = new StatSender();
		statSender.run();
	    }
	}
    }
}
