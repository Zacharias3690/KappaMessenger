package com.zbars.kappaMessenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageListItemAdapter extends ArrayAdapter<MessageListItem> {
    ArrayList<MessageListItem> items;

    public MessageListItemAdapter(Context context, ArrayList<MessageListItem> messageListItems) {
        super(context, 0, messageListItems);
        this.items = messageListItems;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MessageListItem item = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_list_item, parent, false);
        }

        TextView tvUser = (TextView) convertView.findViewById(R.id.User);
//        TextView tvMessageCount = (TextView) convertView.findViewById(R.id.MessageCount);

        tvUser.setText(item.participants);
//        tvMessageCount.setText(Integer.toString(item.messageCount));

        return convertView;
    }
}
