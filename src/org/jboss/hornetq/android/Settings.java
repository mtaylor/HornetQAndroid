package org.jboss.hornetq.android;

public class Settings
{
	private long id;

	private String username;

	private String password;

	private String url;

	public Settings(long id, String username, String password, String url)
	{
		this.id = id;
		this.username = username;
		this.password = password;
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
