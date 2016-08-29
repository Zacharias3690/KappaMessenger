package com.zbars.kappaMessenger;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;

public class DatabaseContext extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;

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
        buildTestData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + MESSAGE_TABLE_NAME);
        db.execSQL("DROP TABLE " + CONVERSATION_TABLE_NAME);
        db.execSQL(MESSAGE_TABLE_CREATE);
        db.execSQL(CONVERSATION_TABLE_CREATE);
        buildTestData(db);
    }

    private void buildTestData(SQLiteDatabase db) {
        String query = "INSERT INTO Conversation (Id, Participants) VALUES (1, \"5125871596,5122805609\");\n" +
                "INSERT INTO Conversation (Id, Participants) VALUES (2, \"5125871596,5122805610\");\n" +
                "INSERT INTO Conversation (Id, Participants) VALUES (3, \"5125871596,5122805611\");\n" +

                "INSERT INTO Message (Id, Sender, Message, ConversationId) VALUES (1, \"5125871596\", \"Test Message 1\", 1);\n" +
                "INSERT INTO Message (Id, Sender, Message, ConversationId) VALUES (2, \"5122805609\", \"Test Message 2\", 1);\n" +
                "INSERT INTO Message (Id, Sender, Message, ConversationId) VALUES (3, \"5122805609\", \"Test Message 3\", 1);\n" +
                "INSERT INTO Message (Id, Sender, Message, ConversationId) VALUES (4, \"5125871596\", \"Test Message 4\", 1);\n" +

                "INSERT INTO Message (Id, Sender, Message, ConversationId) VALUES (5, \"5125871596\", \"Test Message 1\", 2);\n" +
                "INSERT INTO Message (Id, Sender, Message, ConversationId) VALUES (6, \"5125875610\", \"Test Message 2\", 2);\n" +
                "INSERT INTO Message (Id, Sender, Message, ConversationId) VALUES (7, \"5125871596\", \"Test Message 3\", 2);";

        String[] testData = query.split("\n");
        for (String testDataQuery : testData) {
            Log.d("Database", testDataQuery);
            db.execSQL(testDataQuery);
        }
    }
}
