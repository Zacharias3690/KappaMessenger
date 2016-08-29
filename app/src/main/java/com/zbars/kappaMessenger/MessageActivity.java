package com.zbars.kappaMessenger;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.zbars.kappaMessenger.R;

import java.util.ArrayList;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    ArrayList<MessageItem> messages;
    MessageItemAdapter messageItemAdapter;
    MessageService messageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Bundle extras = getIntent().getExtras();

        messageService = new MessageService(this);
        messages = getMessages(extras.getInt("conversationId"));

        messageItemAdapter.clear();
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
        ArrayList<Map<String, String>> messageList = messageService.getMessages(conversationId);

        messageList = messageService.getMessages(conversationId);
        Log.d("MessageDetail", Integer.toString(messageList.size()));

        return null;
    }
}
