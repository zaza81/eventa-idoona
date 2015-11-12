package com.eventa.indoona.lib;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by andreazanda on 11/11/15.
 */
public class Eventa {


    public static String getEvents(String lat, String lon, String date) {

        String jsonTxt = "";
        String response = "";


        try {
            URL url = new URL("http://www.eventa.it/api/events_search_paging?lat="+lat+"&lon="+ lon+"&p=1&radius=20.0&d="+date);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                jsonTxt = jsonTxt + line;
            }
            reader.close();
            JSONArray jsonarray = JSONArray.fromObject(jsonTxt);

            for (int i=0; i<2; i++) {
                JSONObject jsobj = (JSONObject) jsonarray.get(i);
                String title = jsobj.getString("title");
                String link = jsobj.getString("url");

                response = response + title + " - " + link;
            }

        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
        }


        return response;
    }


}
