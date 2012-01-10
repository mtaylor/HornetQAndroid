package org.jboss.hornetq.android;

public class Message
{
	private long id;

	private String username;

	private String content;

	private long timestamp;

	public Message(String username, String content, long timestamp)
	{
	}

	public Message(long id, String username, String content, long timestamp)
	{
		this.id = id;
		this.username = username;
		this.content = content;
		this.timestamp = timestamp;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
