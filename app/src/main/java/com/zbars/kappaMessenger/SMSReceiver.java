package com.zbars.kappaMessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        MessageService messageService = new MessageService(context);
        SmsMessage[] messages =  Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for (SmsMessage message : messages) {
            messageService.addMessage(message.getDisplayOriginatingAddress(), message.getDisplayMessageBody());
        }
    }
}
