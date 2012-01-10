/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.jboss.hornetq.android;

import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbAdapter
{
	public static final String SETTINGS_TABLE = "settings";
	public static final String SETTING_ID = "_id";
	public static final String SETTING_USERNAME = "username";
	public static final String SETTING_PASSWORD = "password";
	public static final String SETTING_URL = "url";

	public static final String MESSAGES_TABLE = "messages";
	public static final String MESSAGE_ID = "_id";
	public static final String MESSAGE_USERNAME = "username";
	public static final String MESSAGE_TIMESTAMP = "timestamp";
	public static final String MESSAGE_CONTENT = "content";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    public DbAdapter(Context ctx) 
    {
        this.mCtx = ctx;
    }

    public DbAdapter open() throws SQLException 
    {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() 
    {
        mDbHelper.close();
    }

    public Settings saveSettings(String username, String password, String url) 
    {
    	deleteSettings();
		ContentValues cv = new ContentValues();
		putCV(cv, SETTING_USERNAME, username);
		putCV(cv, SETTING_PASSWORD, password);
		putCV(cv, SETTING_URL, url);
        return new Settings(mDb.insert(SETTINGS_TABLE, null, cv), username, password, url); 
    }

    
    public Message createMessage(String username, String content, long timestamp) 
    {
		ContentValues cv = new ContentValues();
		putCV(cv, MESSAGE_USERNAME, username);
		putCV(cv, MESSAGE_TIMESTAMP, timestamp);
		putCV(cv, MESSAGE_CONTENT, content);
        return new Message(mDb.insert(MESSAGES_TABLE, null, cv), username, content, timestamp);
    }

    public boolean deleteMessage(long messageId) 
    {
        return mDb.delete(MESSAGES_TABLE, MESSAGE_ID + "=" + messageId, null) > 0;
    }

    public long deleteSettings()
    {
    	return mDb.delete(SETTINGS_TABLE, null, null);
    }

    public long deleteMessages()
    {
    	return mDb.delete(MESSAGES_TABLE, null, null);
    }

    public Settings getSettings() throws SQLException 
    {
        Cursor cursor = mDb.query(true, SETTINGS_TABLE, new String[] {SETTING_ID, SETTING_USERNAME, SETTING_PASSWORD, SETTING_URL},
             null, null, null, null, null, null);
        if(cursor != null && cursor.moveToNext())
        {        	
        	return new Settings(cursor.getLong(cursor.getColumnIndex(SETTING_ID)), cursor.getString(cursor.getColumnIndex(SETTING_USERNAME)),
        			cursor.getString(cursor.getColumnIndex(SETTING_PASSWORD)), cursor.getString(cursor.getColumnIndex(SETTING_URL)));
        }
        else
        {
        	return null;
        }
    }

    public ArrayList<Message> getMessages() throws SQLException 
    {
        Cursor cursor = mDb.query(true, MESSAGES_TABLE, new String[] {MESSAGE_ID, MESSAGE_USERNAME, MESSAGE_CONTENT, MESSAGE_TIMESTAMP},
             null, null, null, null, null, null);
        ArrayList<Message> messages = new ArrayList<Message>();
        if(cursor != null && cursor.moveToFirst())
        {
        	while(cursor.moveToNext())
        	{
        		messages.add(new Message(cursor.getLong(cursor.getColumnIndex(MESSAGE_ID)), cursor.getString(cursor.getColumnIndex(MESSAGE_USERNAME)),
        			cursor.getString(cursor.getColumnIndex(MESSAGE_CONTENT)), cursor.getLong(cursor.getColumnIndex(MESSAGE_TIMESTAMP))));
        	}
        }
        return messages;
    }

    private void putCV(ContentValues cv, String key, String value)
    {
    	if(value != null)
    	{
    		cv.put(key, value);
    	}
    	else
    	{
    		cv.put(key, "");
    	}
    }

    private void putCV(ContentValues cv, String key, long value)
    {
    	cv.put(key, value);
    }
}
