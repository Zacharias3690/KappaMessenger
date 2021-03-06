package com.zbars.kappaMessenger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageService {
    DatabaseContext dbContext;

    public MessageService(Context context) {
        dbContext = new DatabaseContext(context);
    }

    public ArrayList<Map<String, String>> getAllMessages() {
        SQLiteDatabase db = dbContext.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Id, Participants FROM Conversation", null);

        ArrayList<Map<String, String>> messageList = new ArrayList<>();

        if(c.moveToFirst()) {
            do {
                Map<String, String> map = new HashMap<>();
                String id = c.getString(0);
                String participants = c.getString(1);
                map.put("Id", id);
                map.put("Participants", participants);
                messageList.add(map);
            } while(c.moveToNext());
        }
        c.close();
        db.close();

        return messageList;
    }

    public ArrayList<Map<String, String>> getMessages(int conversationId) {
        Log.d("MessageService", Integer.toString(conversationId));

        SQLiteDatabase db = dbContext.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ConversationId, Sender, Message FROM Message WHERE ConversationId = " + conversationId, null);

        ArrayList<Map<String, String>> messageList = new ArrayList<>();

        if(c.moveToFirst()) {
            do {
                Map<String, String> map = new HashMap<>();
                map.put("ConversationId", c.getString(0));
                map.put("Sender", c.getString(1));
                map.put("Message", c.getString(2));
                messageList.add(map);
            } while(c.moveToNext());
        }
        c.close();
        db.close();

        return messageList;
    }

    public void addMessage(String sender, String message) {
        addMessage(sender, message, -1);
    }

    public void addMessage(String sender, String message, int conversationId) {
        ContentValues messageItem;
        SQLiteDatabase db = dbContext.getReadableDatabase();
        long row = -1;

        if (conversationId == -1) {
            ContentValues conversationItem = new ContentValues();

            conversationItem.put("Participants", sender);
            row = db.insert("Conversation", null, conversationItem);
            //add new conversation or check for existing by participants
        }

        messageItem = new ContentValues();
        messageItem.put("Sender", sender);
        messageItem.put("Message", message);

        if (row != -1) {
            messageItem.put("ConversationId", row);
        }

        db.insert("Message", null, messageItem);
    }


}
