package com.mycompany.smartalbum.back.chat.messages;

import java.util.List;

import com.mycompany.smartalbum.back.chat.User;

/**
 * Created by kevinj.
 */
public class UserListMessage extends Message {

    public UserListMessage(List<User> users){
        this.setType(4);
        this.users = users;
    }
    public List<User> users;
}
