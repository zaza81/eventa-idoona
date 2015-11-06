package com.eventa.indoona.config;

import com.indoona.openplatform.sdk.provider.ProviderLocator;
import com.indoona.openplatform.sdk.provider.impl.*;
import com.indoona.openplatform.sdk.provider.exception.*;
import com.indoona.openplatform.sdk.provider.*;

import java.util.*;
import java.lang.*;



public class Config  {

//app settings
public static String redirect= System.getProperty("indoona.app.redirect");
public static String client_id = System.getProperty("indoona.app.clientid");
public static String client_secret = System.getProperty("indoona.app.clientsecret");

//room settings
public static String roomNumber =System.getProperty("indoona.room.roomnumber");
public static String roomName   =System.getProperty("indoona.room.roomname");
public static String roomImg    =System.getProperty("indoona.room.roomimg");
public static String roomFirstMessage = System.getProperty("indoona.room.roomfirstmessage");
public static String roomDesc = System.getProperty("indoona.room.roomdesc");


public static void  init() {	


// initialize a configuration provider with your app’s data
List<String> scope = new ArrayList();
scope.add("basic");
scope.add("user_phone");
ConfigurationProvider confProvider = new ConfigurationProvider();
confProvider.init(client_id, client_secret, 
	redirect, scope);


try {
	ProviderLocator.buildInstance(confProvider);

	ProviderLocator.getInstance().setProvider(
		ProviderLocator.TAG_MD5_SIGNATURE_PROVIDER, 
		new MD5SignatureProvider());
} 
catch (Exception e) {
	e.printStackTrace();
}
}
}