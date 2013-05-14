package io.meetme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends Activity implements OnClickListener {

	private EditText name;
	private Button confirm;

	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		if (isRegistered())
			startMainActivity();

		setContentView(R.layout.activity_welcome);
		name = (EditText) findViewById(R.id.name);
		confirm = (Button) findViewById(R.id.confirm_name);
		confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.confirm_name:
			if (!verifyInput())
				return;
			saveData();
			startMainActivity();
			break;

		default:
			break;
		}
	}

	private void startMainActivity() {
		Intent mainActivityIntent = new Intent(this, MainActivity.class);
		startActivity(mainActivityIntent);
	}

	private boolean isRegistered() {
		return sharedPreferences.getString(PreferenceKeys.USERNAME_STR, null) != null;
	}

	private void saveData() {
		sharedPreferences
				.edit()
				.putString(PreferenceKeys.USERNAME_STR,
						name.getText().toString()).commit();
	}

	private boolean verifyInput() {
		if (name.length() > 0)
			return true;
		else {
			Toast.makeText(this, R.string.must_provide_name, Toast.LENGTH_SHORT)
					.show();
			return false;
		}
	}
}
