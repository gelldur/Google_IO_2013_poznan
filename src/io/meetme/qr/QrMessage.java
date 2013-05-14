package io.meetme.qr;

public class QrMessage {

	private String username;
	private String uniqueId;

	public QrMessage(String username, String uniqueId) {
		super();
		this.setUsername(username);
		this.setUniqueId(uniqueId);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

}
