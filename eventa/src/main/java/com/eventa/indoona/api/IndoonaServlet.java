package com.eventa.indoona.api;

import com.eventa.indoona.model.Conversation;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormat;
import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormatter;
import com.googlecode.objectify.ObjectifyService;
import com.google.code.geocoder.*;
import com.google.code.geocoder.model.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Properties;
import java.util.logging.Logger;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.indoona.openplatform.sdk.provider.ProviderLocator;
import com.indoona.openplatform.sdk.provider.exception.*;
import com.indoona.openplatform.sdk.model.AccessToken;
import com.indoona.openplatform.sdk.model.message.*;
import com.indoona.openplatform.sdk.model.UserAccessToken;
import com.indoona.openplatform.sdk.model.message.*;

import  com.eventa.indoona.model.User;
import com.eventa.indoona.config.Config;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class IndoonaServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(IndoonaServlet.class.getName());

    @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    try {

        String data = req.getParameter("text");

        User user = new User("bfxsokz84nzqpd0678yfa0h79", "9a9ed52c07e3695cfc046260b1c98a3be54830e5", "954df45c4c1b063496f3e56f9aec4a09fe3b6058", "{\"access_token\":\"9a9ed52c07e3695cfc046260b1c98a3be54830e5\",\"token_type\":\"Bearer\",\"creation_date\":1447683273458,\"ttl\":360000,\"scope\":\"basic\",\"user_id\":\"bfxsokz84nzqpd0678yfa0h79\",\"refresh_token\":\"954df45c4c1b063496f3e56f9aec4a09fe3b6058\"}",
                "andrea", "zanda", "+393482353703");

        ObjectifyService.ofy().save().entity(user).now();


        User usr = ObjectifyService.ofy().load().type(User.class).filter("userId", "bfxsokz84nzqpd0678yfa0h79").first().now();
        String userResponse = usr.buildResponse(data);

        //sending message
        usr.sendMessage(userResponse);



    } 

    catch (Exception e) {
      e.printStackTrace();
    }


  }


  //handling a user message
  @Override 
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
         try {

             log.severe("ok");
      //getting data parameter
       String data = req.getParameter("data");

       //parsing just the sender and user txt
      JSONObject jobj = JSONObject.fromObject(data);
       System.out.println(data);
      String sender = jobj.getString("sender");
      String userText = jobj.getJSONObject("data").getString("body");
      String[] parts = sender.split("@");
      sender = parts[0];

      
      //retrieving the user from persistence
      User usr = ObjectifyService.ofy().load().type(User.class).filter("userId", sender).first().now();
      String userResponse = usr.buildResponse(userText);

      //sending message
       usr.sendMessage(userResponse);

        //saving conversati
        Conversation conversation = new Conversation(usr.getUserId(), DateTime.now(), userText, userResponse);
        ObjectifyService.ofy().save().entity(conversation).now();


    } 

    catch (Exception e) {
      e.printStackTrace();
    }

    }









    }