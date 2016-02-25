package com.wguzgg.raspberry.web;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.wguzgg.raspberry.util.Constant;

public class WebContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        Constant.GLOBAL_CONFIGURE_PREFIX = event.getServletContext().getInitParameter("globalConfigurePrefix");
        super.contextInitialized(event);
    }
}
