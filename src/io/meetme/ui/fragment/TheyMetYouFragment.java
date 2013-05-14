package io.meetme.ui.fragment;

import io.meetme.PreferenceKeys;
import io.meetme.R;
import io.meetme.qr.QrMessage;
import io.meetme.qr.QrParser;
import io.meetme.util.AndroidUtils;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.jwetherell.quick_response_code.data.Contents;
import com.jwetherell.quick_response_code.qrcode.QRCodeEncoder;

public class TheyMetYouFragment extends Fragment {
	
	private static final int QR_MAX_SIZE = 768;

	private SharedPreferences sharedPreferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity()
						.getApplicationContext());
		return inflateLayout(inflater, container);
	}

	private View inflateLayout(LayoutInflater inflater, ViewGroup container) {
		View layout = inflater.inflate(R.layout.fragment_they_met_you,
				container, false);

		((ImageView) layout.findViewById(R.id.qr)).setImageBitmap(generateQR());
		return layout;
	}

	private Bitmap generateQR() {
		int size = AndroidUtils.getScreenWidth(getActivity());
		size = size > QR_MAX_SIZE ? QR_MAX_SIZE : size; // Limit size
		
		String name = sharedPreferences.getString(PreferenceKeys.USERNAME_STR,
				"");
		String uuid = AndroidUtils.getUUID(getActivity()
				.getApplicationContext());
		
		String message = QrParser.encode(new QrMessage(name, uuid));

		QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(message, null,
				Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), size);
		try {
			return qrCodeEncoder.encodeAsBitmap();
		} catch (WriterException e) {
			// No time to handle exceptions...
			return null;
		}
	}
}
