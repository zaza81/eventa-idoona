package com.eventa.indoona.api;

import com.eventa.indoona.model.Conversation;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.googlecode.objectify.ObjectifyService;
import com.google.code.geocoder.*;
import com.google.code.geocoder.model.*;

import net.sf.json.*;


import java.io.IOException;
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




public class IndoonaServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    try {

        String address = req.getParameter("address");
    //testing the rest service
     resp.setContentType("text/plain");
     resp.getWriter().println("testing rest: ok");

        User usr = ObjectifyService.ofy().load().type(User.class).filter("userId", "1xgeeo2detmobnsiw727vbqjd").first().now();
        String  me=     ProviderLocator.getInstance().getApiProvider().invokeMeApi(UserAccessToken.fromJson(usr.getJsonUserAccessToken()));

        resp.getWriter().println(me);


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
      String sender = jobj.getString("sender");
      String userText = jobj.getJSONObject("data").getString("body");
      String[] parts = sender.split("@");
      sender = parts[0];

      
      //retrieving the user from persistence
      User usr = ObjectifyService.ofy().load().type(User.class).filter("userId", sender).first().now();
      String userResponse = userText; //buildResponse(userText);

             Conversation conversation = new Conversation(sender, DateTime.now(), userText, userResponse);
             ObjectifyService.ofy().save().entity(conversation).now();


      //sending message
      String sentMsgStr = ProviderLocator.getInstance().getApiProvider().invokeTextMessageSendApi(
      UserAccessToken.fromJson(usr.getJsonUserAccessToken()),
      Config.roomNumber,
      usr.getUserId(),
      userResponse,
      false);
      Message sentMsg = MessageFactory.getInstance().buildMessage(sentMsgStr);



 
    } 

    catch (Exception e) {
      e.printStackTrace();
    }

    }



    public String buildResponse(String usertext){
      String response = "Hi my friend!";
        String lat = "";
        String lgn = "";
      //TODO: 1) get eventa results 2) show them 3) interpret query

        try {
            final Geocoder geocoder = new Geocoder();
            GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(usertext).setLanguage("it").getGeocoderRequest();
            GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
            lat = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLat().toString();
            lgn = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLng().toString();

        }catch (Exception e) {
            e.printStackTrace();
        }


        return (lat +lgn);
    }
}