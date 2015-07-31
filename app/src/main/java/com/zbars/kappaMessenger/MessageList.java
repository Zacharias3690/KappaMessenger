package com.zbars.kappaMessenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        messageService = new MessageService(this);

        MessageItemAdapter messageItemAdapter = new MessageItemAdapter(this, items);
        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(messageItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageListItem item = items.get(position);

                Intent intent = new Intent(MessageList.this, MessageActivity.class);
                intent.putExtra("phoneNumber", item.phoneNumber);
                startActivity(intent);
            }
        });

        ArrayList<MessageListItem> messages = getMessages();

        for(int i = 0; i < messages.size(); i++) {
            messageItemAdapter.add(messages.get(i));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String myPackageName = getPackageName();

        //TODO reactive when settings exist
        if(!Telephony.Sms.getDefaultSmsPackage(this).equals(myPackageName)) {
//            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
//            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, myPackageName);
//            startActivity(intent);
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
        for(int i = 0; i < messageList.size(); i++) {
            Map<String, String> listItem = messageList.get(i);
            MessageListItem item = new MessageListItem(listItem.get("phoneNumber"), Integer.parseInt(listItem.get("messageCount")));
            messages.add(item);
        }

        return messages;
    }
}
