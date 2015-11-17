package com.eventa.indoona.api;

/**
 * Created by andreazanda on 16/11/15.
 */



import com.indoona.openplatform.sdk.provider.ProviderLocator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import com.indoona.openplatform.sdk.model.ConnectedUser;
import com.indoona.openplatform.sdk.model.Contact;
import com.indoona.openplatform.sdk.model.UserAccessToken;
import com.indoona.openplatform.sdk.model.message.Message;
import com.indoona.openplatform.sdk.model.message.MessageFactory;
import com.indoona.openplatform.sdk.provider.ProviderLocator;


public class Connect extends HttpServlet {


    private static final Logger log = Logger.getLogger(Connect.class.getName());


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        try {

            ProviderLocator locator = ProviderLocator.getInstance();

            String otp = request.getParameter("otp");

            // The application should check here whether the
            // currently logged user (if exists) is actually
            // *not connected* to indoona, otherwise prompt
            // users for confirming their identity or
            // logging in with another one

            String url = locator.getAuthorizationProvider()
                    .getConnectUrlWithOtp("my_state", otp);


            response.sendRedirect(url);

        }  catch (Exception e) {
                e.printStackTrace();
            }
    }

}