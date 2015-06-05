package com.zbars.kappaMessenger;

public class MessageListItem {
    int id;
    int messageCount;
    String user;

    public MessageListItem(int id, String user, int messageCount) {
        this.id = id;
        this.user = user;
        this.messageCount = messageCount;
    }

}
