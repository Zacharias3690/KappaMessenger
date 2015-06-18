package com.zbars.kappaMessenger;

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
        Cursor c = db.rawQuery("SELECT PhoneNumber, count(Text) FROM Message GROUP BY PhoneNumber", null);

        ArrayList<Map<String, String>> messageList = new ArrayList<>();

        if(c.moveToFirst()) {
            do {
                Map<String, String> map = new HashMap<>();
                String phoneNumber = c.getString(0);
                int count = c.getInt(1);
                map.put("phoneNumber", phoneNumber);
                map.put("messageCount", Integer.toString(count));
                messageList.add(map);
            } while(c.moveToNext());
        }
        c.close();
        db.close();

        return messageList;
    }


}
