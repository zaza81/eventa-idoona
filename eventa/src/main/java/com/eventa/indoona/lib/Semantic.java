package com.eventa.indoona.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by andreazanda on 11/11/15.
 */
public class Semantic {

    public static String extractSemantic(String usertext) {

        String jsonTxt = "";


        try {
            URL url = new URL("http://api.webofcode.org/wherenwhen?apikey=eventa.it_NkXQneY2azVPEdHK&lang=it&text=" + URLEncoder.encode(usertext, "UTF-8"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                jsonTxt = jsonTxt + line;
            }
            reader.close();


        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
        }


        return jsonTxt;
    }

    public static String containsKeywords(String usertext) {
        String result = "none";

        if (usertext.toLowerCase().contains("siri")){
            result = "Sei proprio spiritoso!";
        }
        else if (usertext.toLowerCase().contains("scemo")){
            result = "Specchio riflesso!";
        }
        else if (usertext.toLowerCase().contains("prova")){
            result = "Funziono, non preoccuparti!";
        }
        else if (usertext.toLowerCase().contains("coglione")){
            result = "Sono una persona educata!";
        }
        else if (usertext.toLowerCase().contains("cavallo")){
            result = "Ti coddidi!";
        }
        else if (usertext.toLowerCase().contains("eventa")){
            result = "Eccomi, cosa vorresti sapere?";
        }
        else if (usertext.toLowerCase().contains("google")){
            result = "ahahha, volevi dire istella?";
        }



        return result;
    }


}
