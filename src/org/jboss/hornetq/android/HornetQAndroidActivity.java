package org.jboss.hornetq.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class HornetQAndroidActivity extends Activity implements OnClickListener
{
    private DbAdapter dbHelper;

    private EditText username;

    private EditText password;

    private Button login;

    private EditText url;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        url = (EditText) findViewById(R.id.url);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(this);

    	dbHelper = new DbAdapter(this);
    	loadSettings();
    }

    public void loadSettings()
    {
    	dbHelper.open();
    	Settings settings = dbHelper.getSettings();
    	dbHelper.close();
    	if(settings != null)
    	{
    		username.setText(settings.getUsername());
    		password.setText(settings.getPassword());
    		url.setText(settings.getUrl());
    	}
    }

    public void saveSettings()
    {
    	dbHelper.open();
    	dbHelper.saveSettings(username.getText().toString(), password.getText().toString(), url.getText().toString()); 
    	dbHelper.close();
    }

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			case R.id.login:	saveSettings();
								Intent i = new Intent(this, MessageActivity.class);
								startActivityForResult(i, 0);
								break;
		}
	}
}