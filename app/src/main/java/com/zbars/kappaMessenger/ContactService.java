package com.zbars.kappaMessenger;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zach on 6/18/2015.
 */
public class ContactService {

    Context context;

    private static final String[] PHONE_FIELDS = new String[] {
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,

    };

    public ContactService(Context context) {
        this.context = context;
    }

    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> contactList = new ArrayList<>();

        Cursor phoneNumbers = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONE_FIELDS, null, null, null);
        while(phoneNumbers.moveToNext()) {
            String phoneNumber = phoneNumbers.getString(phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String numberType = phoneNumbers.getString(phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
            String contactName = phoneNumbers.getString(phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            String type = "";
            if(numberType.equals("0"))
                type = "Work";
            else if(numberType.equals("1"))
                type = "Home";
            else if(numberType.equals("2"))
                type = "Mobile";
            else
                type = "Other";

            Contact contactInfo = new Contact(phoneNumber, contactName, type);

            contactList.add(contactInfo);
        }

        phoneNumbers.close();

        Collections.sort(contactList, new Comparator<Contact>() {
            @Override
            public int compare(final Contact a, final Contact b) {
                return a.name.compareTo(b.name);
            }
        });

        return contactList;
    }
}
