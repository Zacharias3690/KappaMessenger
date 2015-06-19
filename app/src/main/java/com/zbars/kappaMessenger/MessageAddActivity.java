package com.zbars.kappaMessenger;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SimpleAdapter;

import com.zbars.kappaMessenger.R;

import java.util.ArrayList;
import java.util.Map;

public class MessageAddActivity extends AppCompatActivity {

    private ArrayList<Contact> contactList;
    private ContactService contactService;
    private MultiAutoCompleteTextView contactTextView;
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_add);

        contactService = new ContactService(this);
        contactList = contactService.getContacts();

        contactTextView = (MultiAutoCompleteTextView) findViewById(R.id.contactAutoComplete);
        contactAdapter = new ContactAdapter(this, R.layout.contact_typeahead_view, contactList);
        contactTextView.setAdapter(contactAdapter);
        contactTextView.setThreshold(1);
        contactTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_add, menu);
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
