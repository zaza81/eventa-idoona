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
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    try {

        String address = req.getParameter("address");
    //testing the rest service
     resp.setContentType("text/plain");
     resp.getWriter().println("testing rest: ok");
       // User usr = ObjectifyService.ofy().load().type(User.class).filter("userId", "1xgeeo2detmobnsiw727vbqjd").first().now();

     String json = "{\"access_token\":\"00a0a04b1f50c79b939cb8cc279535bd52633b7d\",\"token_type\":\"Bearer\",\"creation_date\":1447255231128,\"ttl\":360000,\"scope\":\"basic user_phone\",\"user_id\":\"1xgeeo2detmobnsiw727vbqjd\",\"refresh_token\":\"80e0c60038f1ae20e4d785e6400eadc065df5935\"}";
             String lat =    "40.5578";
        String lon =  "8.32194";
        String tel =         "+393482353703";
        String name =         "andrea";
        String refresh = "80e0c60038f1ae20e4d785e6400eadc065df5935";
        String surname =  "zanda";
        String token = "00a0a04b1f50c79b939cb8cc279535bd52633b7d";
        String userId =  "1xgeeo2detmobnsiw727vbqjd";
       // String userResponse = usr.buildResponse("eventi milano domani");

        User usr = new User();
        usr.setJsonUserAccessToken(json);
        usr.setLat(lat);
        usr.setLon(lon);
        usr.setMobileNumber(tel);
        usr.setName(name);
        usr.setRefreshToken(refresh);
        usr.setSurname(surname);
        usr.setToken(token);
        usr.setUserId(userId);
        ObjectifyService.ofy().save().entity(usr).now();


        //Conversation conversation = new Conversation("", DateTime.now(), "ciao", "");
        //ObjectifyService.ofy().save().entity(conversation).now();

        resp.getWriter().println("ciao");



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