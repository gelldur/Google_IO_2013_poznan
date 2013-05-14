package io.meetme;

import io.meetme.database.DatabaseManager;
import io.meetme.database.User;
import android.app.Application;
import android.util.Log;

public class MeetMeApplication extends Application {

    @Override
    public void onCreate() {
	super.onCreate();

	DatabaseManager.init(this, R.xml.database);
	DatabaseManager databaseManager = DatabaseManager.getInstance();

	databaseManager.add(User.meetUser("123", "Test"));

	Log.d(this.toString(), "POINTS: " + databaseManager.getPoints());
    }
}
