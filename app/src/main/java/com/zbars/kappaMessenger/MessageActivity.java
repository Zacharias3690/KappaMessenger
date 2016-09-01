package com.zbars.kappaMessenger;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.zbars.kappaMessenger.R;

import java.util.ArrayList;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    ArrayList<MessageItem> messages = new ArrayList<>();
    MessageItemAdapter messageItemAdapter;
    MessageService messageService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Bundle extras = getIntent().getExtras();

        messageService = new MessageService(this);
        messageItemAdapter = new MessageItemAdapter(this, messages);
        messages = getMessages(extras.getInt("ConversationId"));
        ListView messageListView = (ListView) findViewById(R.id.detailListView);

        messageListView.setAdapter(messageItemAdapter);

        for(int i = 0; i < messages.size(); i++) {
            messageItemAdapter.add(messages.get(i));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<MessageItem> getMessages(int conversationId) {
        ArrayList<MessageItem> messages = new ArrayList<>();
        ArrayList<Map<String, String>> messageList = messageService.getMessages(conversationId);
        Log.d("MessageDetail", "Message List Size: " + Integer.toString(messageList.size()));


        for(int i = 0; i < messageList.size(); i++) {
            Map<String, String> listItem = messageList.get(i);
            Log.d("MessageDetail", "Sender: " + listItem.get("Sender")+ " Message: " + listItem.get("Sender"));
            MessageItem item = new MessageItem(conversationId, listItem.get("Message"), listItem.get("Sender"));
            messages.add(item);
        }

        Log.d("MessageDetail", "Converted Message List Size: " + messages.size());

        return messages;
    }
}
