package org.jboss.hornetq.android;

import java.util.Calendar;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class MessageActivity  extends Activity implements OnClickListener
{
    private DbAdapter dbHelper;

    private Settings settings;

    private Button send;

    private EditText content;

    private LinearLayout messages;

    private ScrollView messagesScroll;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
    	dbHelper = new DbAdapter(this);

    	send = (Button) findViewById(R.id.send);
    	send.setOnClickListener(this);

    	content = (EditText) findViewById(R.id.message);
    	messages = (LinearLayout) findViewById(R.id.messages);
    	messagesScroll = (ScrollView) findViewById(R.id.messagesScroll);

    	setSettings();
    	setMessages();
    }

    private void setSettings()
    {
    	dbHelper.open();
    	settings = dbHelper.getSettings();
    	dbHelper.close();
    }

    private void setMessages()
    {
    	dbHelper.open();
    	for(Message message : dbHelper.getMessages())
    	{
    		addMessageToLayout(message);
    	}
    	dbHelper.close();
    	messagesScroll.fullScroll(ScrollView.FOCUS_DOWN);
    }

    private void addMessageToLayout(Message message)
    {
    	TextView user = new TextView(this);
    	TextView messageView = new TextView(this);

    	user.setTextColor(getResources().getColor(R.color.messageTitle));
    	user.setText("(" + DateFormat.format("hh:mm aa", message.getTimestamp()) + ") " + message.getUsername() + ":");
    	user.setTextSize(15);
    	user.setPadding(5, 15, 5, 0);
    	user.setTypeface(Typeface.DEFAULT_BOLD);
	
		messageView.setText(message.getContent());
		messageView.setTextColor(getResources().getColor(R.color.textColor));
		messageView.setTextSize(15);
		messageView.setPadding(5, 0, 5, 0);

		View line = new View(this);
		line.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		line.setBackgroundColor(getResources().getColor(R.color.messageLine));
		line.setPadding(0, 0, 0, 15);

		messages.addView(user);
		messages.addView(messageView);
		messages.addView(line);
    }

    private Message createMessage()
    {
    	dbHelper.open();
    	Message message = dbHelper.createMessage(settings.getUsername(), content.getText().toString(), Calendar.getInstance().getTimeInMillis());
    	dbHelper.close();
    	return message;
    }

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			case R.id.send:		Message message = createMessage();
								addMessageToLayout(message);
						    	messagesScroll.fullScroll(ScrollView.FOCUS_DOWN);
								break;
		}
	}
}
