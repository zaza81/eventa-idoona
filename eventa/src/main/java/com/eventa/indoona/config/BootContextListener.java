package com.eventa.indoona.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;
import java.util.logging.Logger;

import com.eventa.indoona.model.Conversation;
import com.googlecode.objectify.ObjectifyService;

import  com.eventa.indoona.model.User;




public class BootContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	       Config.init();
    	       Logger lg = Logger.getLogger(BootContextListener.class.getName());
              lg.info("Indoona configured!");

              ObjectifyService.register(User.class);
              ObjectifyService.register(Conversation.class);
              lg.info("Objectify configured!");


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // shutdown code here
    }

}