package com.zbars.kappaMessenger;

public class MessageListItem {
    int conversationId;
    String participants;

    public MessageListItem(int conversationId, String participants) {
        this.conversationId = conversationId;
        this.participants = participants;
    }

}
