package io.meetme;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.jwetherell.quick_response_code.data.Contents;
import com.jwetherell.quick_response_code.qrcode.QRCodeEncoder;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		QRCodeEncoder qrCodeEncoder = new QRCodeEncoder("Hello", null,
				Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(),
				300);
		try {
			Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
			((ImageView)findViewById(R.id.qr)).setImageBitmap(bitmap);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
