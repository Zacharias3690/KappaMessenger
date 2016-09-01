package com.zbars.kappaMessenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;


public class MessageList extends AppCompatActivity {

    ArrayList<MessageListItem> items = new ArrayList<>();
    MessageService messageService;
    ArrayList<MessageListItem> messages;
    MessageListItemAdapter messageItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        messageService = new MessageService(this);
        messageItemAdapter = new MessageListItemAdapter(this, items);
        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(messageItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageListItem item = items.get(position);

                Intent intent = new Intent(MessageList.this, MessageActivity.class);
                Log.d("MessageList", "ConversationId = " + Integer.toString(item.conversationId));
                intent.putExtra("ConversationId", item.conversationId);
                startActivity(intent);
            }
        });

        messages = getMessages();

        for(int i = 0; i < messages.size(); i++) {
            messageItemAdapter.add(messages.get(i));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String myPackageName = getPackageName();
        final SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        Boolean showChangeSmsDialog = true;

        if(preferences.contains("CHANGE_SMS")) {
            showChangeSmsDialog = preferences.getBoolean("CHANGE_SMS", true);
        }

        if(!Telephony.Sms.getDefaultSmsPackage(this).equals(myPackageName) && showChangeSmsDialog) {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, myPackageName);
            startActivity(intent);
            preferences.edit().putBoolean("CHANGE_SMS", false).apply();
        }

        messages = getMessages();

        messageItemAdapter.clear();
        for(int i = 0; i < messages.size(); i++) {
            messageItemAdapter.add(messages.get(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_list, menu);
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

        if(id == R.id.action_add) {
            Intent intent = new Intent(MessageList.this, MessageAddActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<MessageListItem> getMessages() {
        ArrayList<MessageListItem> messages = new ArrayList<>();

        ArrayList<Map<String, String>> messageList = messageService.getAllMessages();
        Log.d("MessageList", Integer.toString(messageList.size()));
        for(int i = 0; i < messageList.size(); i++) {
            Map<String, String> listItem = messageList.get(i);
            MessageListItem item = new MessageListItem(Integer.parseInt(listItem.get("Id")), listItem.get("Participants"));
            messages.add(item);
        }

        return messages;
    }
}
