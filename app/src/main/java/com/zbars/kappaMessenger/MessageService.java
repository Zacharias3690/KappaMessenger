package com.zbars.kappaMessenger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        Cursor c = db.rawQuery("SELECT Participants FROM Conversation", null);

        ArrayList<Map<String, String>> messageList = new ArrayList<>();

        if(c.moveToFirst()) {
            do {
                Map<String, String> map = new HashMap<>();
                String participants = c.getString(0);
                map.put("participants", participants);
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

            conversationItem.put("participants", sender);
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
