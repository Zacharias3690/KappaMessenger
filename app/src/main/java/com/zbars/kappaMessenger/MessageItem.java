package com.zbars.kappaMessenger;

/**
 * Created by Zach on 8/28/2016.
 */

public class MessageItem {
    int conversationId;
    String message;
    String sender;

    public MessageItem(int conversationId, String message, String sender) {
        this.conversationId = conversationId;
        this.message = message;
        this.sender = sender;
    }
}
