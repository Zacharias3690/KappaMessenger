package com.zbars.kappaMessenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class MessageList extends AppCompatActivity {

    ArrayList<MessageListItem> items = new ArrayList<MessageListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        MessageItemAdapter messageItemAdapter = new MessageItemAdapter(this, items);
        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(messageItemAdapter);

        messageItemAdapter.add(new MessageListItem(1, "Zach", 1));
        messageItemAdapter.add(new MessageListItem(2, "Spo", 2));
        messageItemAdapter.add(new MessageListItem(3, "Andy", 3));
        messageItemAdapter.add(new MessageListItem(4, "Christie", 4));
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

        return super.onOptionsItemSelected(item);
    }
}
