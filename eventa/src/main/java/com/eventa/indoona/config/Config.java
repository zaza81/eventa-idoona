package com.eventa.indoona.config;

import com.indoona.openplatform.sdk.provider.ProviderLocator;
import com.indoona.openplatform.sdk.provider.impl.*;
import com.indoona.openplatform.sdk.provider.exception.*;
import com.indoona.openplatform.sdk.provider.*;

import java.util.*;
import java.lang.*;



public class Config  {

//app settings
public static String redirect= "https://eventa-indoona.appspot.com/redirect";
public static String client_id = "5645cb62846449063542a624Blt64";
public static String client_secret = "6C9JEI7OnsaYxgFLTHKf20EWMSx8lEcshlcW4jKrLUqjb654NPkQQZMDRkLdYYFg";

//room settings
public static String roomNumber = "44" ;
public static String roomName   = "Eventi attorno a te";
public static String roomImg    = "https://lh3.googleusercontent.com/-lBS8dnXoAiM/AAAAAAAAAAI/AAAAAAAABfM/9wjarOVJMVE/s120-c/photo.jpg";
public static String roomFirstMessage =  "Ciao, sono Eventa e ti aiuterò a trovare i migliori eventi attorno a te. Digita la città :-)" ;
public static String roomDesc = "Ciao, sono Eventa e ti aiuterò a trovare i migliori eventi attorno a te. Digita la città :-)";


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