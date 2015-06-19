package com.zbars.kappaMessenger;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.zbars.kappaMessenger.R;

import java.util.ArrayList;
import java.util.Map;

public class MessageAddActivity extends AppCompatActivity {

    private final static String TAG = "MessageAddActivity";

    ContactAdapter contactAdapter;
    MultiAutoCompleteTextView contactTextView;
    EditText messageTextView;
    ArrayList<Contact> contactList;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_add);

        ContactService contactService = new ContactService(this);
        contactList = contactService.getContacts();

        contactTextView = (MultiAutoCompleteTextView) findViewById(R.id.contactAutoComplete);
        messageTextView = (EditText) findViewById(R.id.messageText);
        sendButton = (Button) findViewById(R.id.sendButton);

        contactAdapter = new ContactAdapter(this, R.layout.contact_typeahead_view, new ArrayList<>(contactList));
        contactAdapter.addAll(contactList);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextMessage();
                messageTextView.setText("");
            }
        });

        contactTextView.setAdapter(contactAdapter);
        contactTextView.setThreshold(1);
        contactTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        contactTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        contactTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.getId() == R.id.contactAutoComplete && !hasFocus) {
                    ((AutoCompleteTextView) v).performValidation();
                }
            }
        });
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

    public void sendTextMessage() {
        if(messageTextView.length() == 0)
            return;

        SmsManager smsManager = SmsManager.getDefault();
        String contactString = contactTextView.getText().toString();
        String[] contacts = contactString.split(", ");

        String address;
        String messageBody = messageTextView.getText().toString();

        for (String c: contacts) {
            Contact contact = getContact(c.trim());
            if (contact != null) {
                address = contact.phone;
            } else {
                address = c;
            }

            Log.v(TAG, address);
            Log.v(TAG, messageBody);
            try {
                smsManager.sendTextMessage(address, null, messageBody, null, null);
            } catch (IllegalArgumentException e) {
                Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }

    private Contact getContact(String text) {
        for(int i = 0; i < contactList.size(); i++) {
            Contact c = contactList.get(i);
            if(c.name.equals(text)) {
                return c;
            }
        }

        return null;
    }
}
