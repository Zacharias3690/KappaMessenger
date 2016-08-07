package com.zbars.kappaMessenger;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.PreparedStatement;

public class DatabaseContext extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "KappaMessengerDatabase";
    private static final String MESSAGE_TABLE_NAME = "Message";
    private static final String PHONE_FIELD = "PhoneNumber";
    private static final String TEXT_FIELD = "Text";

    private static final String MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + MESSAGE_TABLE_NAME + " (" +
                    PHONE_FIELD + " TEXT, " +
                    TEXT_FIELD + " TEXT" +
            ")";

    DatabaseContext(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MESSAGE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + MESSAGE_TABLE_NAME);
        db.execSQL(MESSAGE_TABLE_CREATE);
    }
}
