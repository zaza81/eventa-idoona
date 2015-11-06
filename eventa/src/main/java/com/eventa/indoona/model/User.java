package com.eventa.indoona.model;

import java.util.ArrayList;
import java.util.List;

import com.eventa.indoona.config.Config;
import com.google.common.collect.ImmutableList;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.indoona.openplatform.sdk.model.ConnectedUser;
import com.indoona.openplatform.sdk.model.Contact;
import com.indoona.openplatform.sdk.model.UserAccessToken;
import com.indoona.openplatform.sdk.model.message.Message;
import com.indoona.openplatform.sdk.model.message.MessageFactory;
import com.indoona.openplatform.sdk.provider.ProviderLocator;


@Entity
public class User {
    @Id Long id;
    @Index String userId;
    String token;
    String refreshToken;
    String name;
    String surname;
    String mobileNumber;
    String jsonUserAccessToken;

    private User(){

    }


    public User(String userId, String token, String refreshToken,String json,String name, String surname, String mobileNumber){
        this.userId = userId;
        this.token = token;
        this.refreshToken = refreshToken;
        this.name = name;
        this.surname = surname;
        this.mobileNumber = mobileNumber;
        this.jsonUserAccessToken = json;
    }

    public String getUserId(){
        return userId;
    }
    public String getRefreshToken() {return refreshToken;}
    public String getToken(){ return token;}

    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getMobileNumber(){
        return mobileNumber;
    }
    public String getJsonUserAccessToken(){
        return jsonUserAccessToken;
    }



    public static UserAccessToken createUser (String code){

        UserAccessToken token = null;

        try {

        token = ProviderLocator.getInstance()
                .getAuthorizationProvider()
                .getUserAccessToken(code);


        ConnectedUser cusr = ConnectedUser.fromJson(ProviderLocator.getInstance().getApiProvider().invokeMeApi(token));

        User usr = new User(token.getUserId(), token.getToken(), token.getRefreshToken(), token.toJson(), cusr.getName(), cusr.getSurname(), cusr.getMsisdn());
        ObjectifyService.ofy().save().entity(usr).now();


    }
    catch (Exception e) {
        e.printStackTrace();
    }
        return token;

    }

    public  static void addChannel (UserAccessToken token) {

        try{
        //create a contact for the user
        List<String> caps = new ArrayList();
        caps.add("group_add");
        caps.add("interactive");

        Contact eventaChannel = new Contact(
                Config.roomNumber,
                Config.roomName,
                Config.roomImg,
                caps);

        ProviderLocator.getInstance().getApiProvider().invokeContactAddApi(token, eventaChannel);


        //send a welcome message to the user
        String sentMsgStr = ProviderLocator.getInstance()
                .getApiProvider().invokeTextMessageSendApi(
                        token,
                        Config.roomNumber,
                        "0",
                        Config.roomDesc,
                        false);
        Message sentMsg = MessageFactory.getInstance().buildMessage(sentMsgStr);

    }
    catch (Exception e) {
        e.printStackTrace();
    }

    }

}