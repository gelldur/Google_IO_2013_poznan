package io.meetme;

import io.meetme.adapter.SwipePagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private static final int REQUEST_CODE_SCAN = 0x1337;

	private SwipePagerAdapter swipePagerAdapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
				Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
			} else {
				// Scan was cancelled.
			}
		}
	}

	public void startScanActivity() {
		Intent scanIntent = new Intent(this, ScanActivity.class);
		startActivityForResult(scanIntent, REQUEST_CODE_SCAN);
	}
}
