package io.meetme;

import io.meetme.adapter.SwipePagerAdapter;
import io.meetme.database.DatabaseManager;
import io.meetme.database.User;
import io.meetme.qr.QrMessage;
import io.meetme.qr.QrParser;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private static final int REQUEST_CODE_SCAN = 0x1337;

	private SwipePagerAdapter swipePagerAdapter;
	private ViewPager viewPager;
	
	private DatabaseManager databaseManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		databaseManager = DatabaseManager.getInstance();
		setUpPager();
	}

	private void setUpPager() {
		swipePagerAdapter = new SwipePagerAdapter(this,
				getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(swipePagerAdapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_SCAN) {
			if (resultCode == RESULT_OK) {
				String text = data
						.getStringExtra(ScanActivity.EXTRA_RESULT_TEXT);
				handleQrScan(text);
			} else {
				// Scan was cancelled.
			}
		}
	}

	private void handleQrScan(String rawQrMessage) {
		QrMessage qrMessage = QrParser.decode(rawQrMessage);
		
		if(qrMessage == null) {
			Toast.makeText(this, R.string.invalid_qr_code, Toast.LENGTH_LONG).show();
			return;
		}
		
		User user = new User();
		user.setName(qrMessage.getUsername());
		user.setUserID(qrMessage.getUniqueId());
		user.setMeetDate(new Date());
		
		if(databaseManager.userExists(user)) {
			Toast.makeText(this, R.string.double_scan, Toast.LENGTH_LONG).show();
			return;
		}
		
		databaseManager.add(user);
	}

	public void startScanActivity() {
		Intent scanIntent = new Intent(this, ScanActivity.class);
		startActivityForResult(scanIntent, REQUEST_CODE_SCAN);
	}
}
