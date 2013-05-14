package io.meetme.util;

import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.Display;

public class AndroidUtils {

	public static String getUUID(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		return deviceUuid.toString();
	}
	
	public static int getScreenWidth(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		return display.getWidth(); // deprecated
	}
}
