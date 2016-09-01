package com.zbars.kappaMessenger;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Zach on 8/28/2016.
 */

public class MessageItemAdapter extends ArrayAdapter<MessageItem> {
    ArrayList<MessageItem> items;

    public MessageItemAdapter(Context context, ArrayList<MessageItem> messages) {
        super(context, 0, messages);
        this.items = messages;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MessageItem item = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_detail_item, parent, false);
        }

        TextView tvMessage = (TextView) convertView.findViewById(R.id.messageText);
        TextView tvSender = (TextView) convertView.findViewById(R.id.messageSender);
        LinearLayout wrapper = (LinearLayout) convertView.findViewById(R.id.messageWrapper);

        Log.d("MessageItemView", "Sender: " + item.sender + " Message: " + item.message);

        tvMessage.setText(item.message);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)wrapper.getLayoutParams();

        int messageGray = convertView.getResources().getColor(R.color.background_message_gray);
        int messageBlue = convertView.getResources().getColor(R.color.background_message_blue);

        if(item.sender.equals("self")) {
            params.gravity = Gravity.RIGHT;
            wrapper.getBackground().setColorFilter(messageGray, PorterDuff.Mode.SRC_ATOP);
            tvSender.setVisibility(View.GONE);
        } else {
            params.gravity = Gravity.LEFT;
            wrapper.getBackground().setColorFilter(messageBlue, PorterDuff.Mode.SRC_ATOP);
            tvSender.setText(item.sender);
        }

        wrapper.setLayoutParams(params);

        return convertView;
    }
}
