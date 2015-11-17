package com.eventa.indoona.api;

/**
 * Created by andreazanda on 16/11/15.
 */


 import com.eventa.indoona.model.Conversation;
 import com.eventa.indoona.model.User;
 import com.google.appengine.repackaged.org.joda.time.DateTime;
 import com.googlecode.objectify.ObjectifyService;
 import com.indoona.openplatform.sdk.provider.ProviderLocator;
 import com.indoona.openplatform.sdk.model.AppAccessToken;
 import com.indoona.openplatform.sdk.utils.TextUtils.*;
 import net.sf.json.JSONObject;

 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;


public class Management extends HttpServlet {
    User usr = null;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ProviderLocator locator = ProviderLocator.getInstance();

            String otp = request.getParameter("otp");

            AppAccessToken appToken = locator
                    .getAuthorizationProvider()
                    .getAppAccessToken();
            String jsonResult = locator.getApiProvider()
                    .invokeOtpVerifyApi(appToken, otp);
            String userId = JSONObject.fromObject(jsonResult).getString("user_id");

            usr = ObjectifyService.ofy().load().type(User.class).filter("userId", userId).first().now();


            response.sendRedirect("/management.jsp?user="+usr.getUserId());
            // here you should check whether the obtained
            // user_id matches the one locally stored for
            // the currently logged user (if exists);
            // if yes, provide the management page’s content,
            // otherwise you should raise an error
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    //handling a user message
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


        System.out.println("**************");
        resp.sendRedirect("/management.jsp?user="+usr.getUserId());




    }

}