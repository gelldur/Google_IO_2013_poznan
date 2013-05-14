package io.meetme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.zxing.Result;
import com.jwetherell.quick_response_code.DecoderActivity;

public class ScanActivity extends DecoderActivity {

	public static final String EXTRA_RESULT_TEXT = "result_text";

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setResult(RESULT_CANCELED);
	}

	@Override
	public void handleDecode(Result rawResult, Bitmap barcode) {
		super.handleDecode(rawResult, barcode);
		Intent data = new Intent();
		data.putExtra(EXTRA_RESULT_TEXT, rawResult.getText());
		setResult(RESULT_OK, data);
		finish();
	}
}
