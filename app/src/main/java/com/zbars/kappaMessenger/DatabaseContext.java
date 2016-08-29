package com.zbars.kappaMessenger;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.PreparedStatement;

public class DatabaseContext extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "KappaMessengerDatabase";

    //Message
    private static final String MESSAGE_TABLE_NAME = "Message";
    private static final String SENDER_FIELD = "Sender";
    private static final String MESSAGE_FIELD = "Message";
    private static final String CONVERSATION_ID_FIELD = "ConversationId";

    //Conversation
    private static final String CONVERSATION_TABLE_NAME = "Conversation";
    private static final String PARTICIPANTS_FIELD = "Participants";


    private static final String MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + MESSAGE_TABLE_NAME + " (" +
                    "Id INTEGER PRIMARY KEY, " +
                    SENDER_FIELD + " TEXT, " +
                    MESSAGE_FIELD + " TEXT, " +
                    CONVERSATION_ID_FIELD + " INTEGER, " +
                    "FOREIGN KEY(" + CONVERSATION_ID_FIELD + ") REFERENCES " + CONVERSATION_TABLE_NAME + "(Id)" +
            ")";

    private static final String CONVERSATION_TABLE_CREATE =
            "CREATE TABLE " +  CONVERSATION_TABLE_NAME + " (" +
                    "Id INTEGER PRIMARY KEY, " +
                    PARTICIPANTS_FIELD + " TEXT" +
            ")";

    DatabaseContext(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONVERSATION_TABLE_CREATE);
        db.execSQL(MESSAGE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + MESSAGE_TABLE_NAME);
        db.execSQL("DROP TABLE " + CONVERSATION_TABLE_NAME);
        db.execSQL(MESSAGE_TABLE_CREATE);
        db.execSQL(CONVERSATION_TABLE_CREATE);
    }
}
