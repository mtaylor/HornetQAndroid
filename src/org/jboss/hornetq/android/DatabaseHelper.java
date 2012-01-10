package org.jboss.hornetq.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper     
{
    private static final String DATABASE_NAME = "hornetq.db";
    private static final int DATABASE_VERSION = 1;

    DatabaseHelper(Context context) 
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// Create Settings Table
		String createSettingsTable = "CREATE TABLE " + DbAdapter.SETTINGS_TABLE + " ( " + 
			DbAdapter.SETTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			DbAdapter.SETTING_USERNAME + " TEXT, " +
			DbAdapter.SETTING_PASSWORD + " TEXT, " +
			DbAdapter.SETTING_URL + " TEXT );";
		db.execSQL(createSettingsTable);
		
		// Create Messages table
		String createMessagesTable = "CREATE TABLE " + DbAdapter.MESSAGES_TABLE + " ( " + 
		DbAdapter.MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		DbAdapter.MESSAGE_USERNAME + " TEXT, " +
		DbAdapter.MESSAGE_CONTENT + " TEXT, " +
		DbAdapter.MESSAGE_TIMESTAMP + " DATE );";
		db.execSQL(createMessagesTable);
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
    }
}
