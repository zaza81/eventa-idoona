package com.eventa.indoona.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.eventa.indoona.config.Config;
import com.eventa.indoona.lib.Eventa;
import com.eventa.indoona.lib.Semantic;
import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormat;
import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormatter;
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
import net.sf.json.JSONObject;


@Entity
public class User {
    @Id Long id;
    @Index String userId;
    @Index String token;
    String refreshToken;
    String name;
    String surname;
    String lat;
    String lon;
    String mobileNumber;
    String jsonUserAccessToken;


    public User(){

    }



    public User(String userId, String token, String refreshToken,String json,String name, String surname, String mobileNumber){
        this.userId = userId;
        this.token = token;
        this.refreshToken = refreshToken;
        this.name = name;
        this.surname = surname;
        this.lat = "0.0";
        this.lon = "0.0";
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
    public String getLat() {return lat;}
    public String getLon() {return lon;}
    public String getMobileNumber(){
        return mobileNumber;
    }
    public String getJsonUserAccessToken(){
        return jsonUserAccessToken;
    }
    public void setUserId(String userId) {this.userId = userId;}
    public void setToken(String token) {this.token = token;}
    public void setRefreshToken(String refreshToken) {this.refreshToken = refreshToken;}
    public void setName(String name) {this.name = name;}
    public void setSurname(String surname) {this.surname = surname;}
    public void setLat(String lat) {this.lat = lat;}
    public void setLon(String lon) {this.lon = lon;}
    public void setMobileNumber(String mobileNumber) {this.mobileNumber = mobileNumber;}
    public void setJsonUserAccessToken(String jsonUserAccessToken) {this.jsonUserAccessToken = jsonUserAccessToken;}



    public static User createUser (String code){

        UserAccessToken token = null;
        User usr = new User();
        try {


            token = ProviderLocator.getInstance()
                .getAuthorizationProvider()
                .getUserAccessToken(code);

            ConnectedUser cusr = ConnectedUser.fromJson(ProviderLocator.getInstance().getApiProvider().invokeMeApi(token));
            usr = new User(token.getUserId(), token.getToken(), token.getRefreshToken(), token.toJson(), cusr.getName(), cusr.getSurname(), cusr.getMsisdn());
            ObjectifyService.ofy().save().entity(usr).now();



        }
    catch (Exception e) {e.printStackTrace();}


        return usr;

    }

    public   void addChannel () {

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
            UserAccessToken uat = UserAccessToken.fromJson(this.getJsonUserAccessToken());
        ProviderLocator.getInstance().getApiProvider().invokeContactAddApi(uat, eventaChannel);



        }
    catch (Exception e) {
        e.printStackTrace();
    }

    }


    public  void sendMessage ( String message) {


        try{
            UserAccessToken uat =  UserAccessToken.fromJson(this.getJsonUserAccessToken());
               if(uat.isExpired()) {
               UserAccessToken newUat = ProviderLocator.getInstance()
                        .getAuthorizationProvider()
                        .refreshUserAccessToken(uat);

                User usr = ObjectifyService.ofy().load().type(User.class).filter("token", uat).first().now();
                usr.setJsonUserAccessToken(newUat.toJson());
                usr.setToken(newUat.getToken());
                ObjectifyService.ofy().save().entity(usr).now();

            }

            //send a welcome message to the user
            String sentMsgStr = ProviderLocator.getInstance()
                    .getApiProvider().invokeTextMessageSendApi(
                            uat,
                            Config.roomNumber,
                            this.getUserId(),
                            message,
                            false);
            Message sentMsg = MessageFactory.getInstance().buildMessage(sentMsgStr);




        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String buildResponse(String usertext){
        String response = "Hi my friend!";
        //TODO: 1) get eventa results 2) show them 3) interpret query

        JSONObject jsonobj = JSONObject.fromObject(Semantic.extractSemantic(usertext));
        String date = jsonobj.getJSONObject("when").getString("date");
        String lat = jsonobj.getJSONObject("where").getString("lat");
        String lon = jsonobj.getJSONObject("where").getString("long");
        String msg = jsonobj.getString("msg");

        if (msg =="null") {msg = "";}


        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");

        if(Semantic.containsKeywords(usertext) != "none") {

            response = Semantic.containsKeywords(usertext);

        }
        else if(lat=="null" && (getLat() == "0.0" || getLat() == null)) {

            response = this.getName()+",non ho capito il luogo, potresti specificarlo?";


        } else if (lat=="null" && getLat() != "0.0" ){
            lat= this.getLat();
            lon = this.getLon();
            ObjectifyService.ofy().save().entity(this).now();



            if (date == "null") {
                 date = fmt.print(DateTime.now());}
            else {
                   date =  fmt.print(DateTime.parse(date));
            }
            response =  this.getName()+", "+ msg +" ---> " + Eventa.getEvents(lat, lon, date);

        }
        else {
            System.out.println("tre");

            if (date == "null") {
                date = fmt.print(DateTime.now());}
            else {
                date =  fmt.print(DateTime.parse(date));
            }
            System.out.println(date);
            this.setLat(lat);
            this.setLon(lon);
            ObjectifyService.ofy().save().entity(this).now();
            response = this.getName()+", "+ msg+ " ---> " +Eventa.getEvents(lat, lon, date);

        }

        return (response);
    }


}