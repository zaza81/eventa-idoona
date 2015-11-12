package com.eventa.indoona.model;


import java.util.ArrayList;
import java.util.List;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;



@Entity
public class Conversation {
    @Id Long id;
    @Index String userId;
    String when;
    @Index String messageUser;
    @Index String messageBot;


    private Conversation(){

    }

    public Conversation(String userId, DateTime when, String messageUser, String messageBot ){
        this.userId = userId;
        this.when = when.toString();
        this.messageUser = messageUser;
        this.messageBot = messageBot;
    }


    public String getUserId() {
        return userId;
    }

    public String getWhen() {
        return when;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public String getMessageBot() {
        return messageBot;
    }




}