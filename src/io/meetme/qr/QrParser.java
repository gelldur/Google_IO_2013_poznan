package io.meetme.qr;

public class QrParser {

	public static final String SEPERATOR = ">_>";
	public static final int PARAM_COUNT = 2;

	public static String encode(QrMessage qrMessage) {
		return qrMessage.getUsername() + SEPERATOR + qrMessage.getUniqueId();
	}

	public static QrMessage decode(String rawQrString) {
		if (!validateResponse(rawQrString))
			return null;

		String[] split = rawQrString.split(SEPERATOR);
		return new QrMessage(split[0], split[1]);
	}

	public static boolean validateResponse(String rawQrString) {
		if (rawQrString == null)
			return false;
		return rawQrString.split(SEPERATOR).length == PARAM_COUNT;
	}
}
