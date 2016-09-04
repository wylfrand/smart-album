package com.mycompany.smartalbum.back.chat.messages;

import java.util.List;

/**
 * Created by kevinj.
 */
public class ChatMessagesMessage extends Message {
    public List<ChatMessage> messages;
// Comment added here
    public ChatMessagesMessage(List<ChatMessage> messages) {
        this.setType(6);
        this.messages = messages;
    }
}
