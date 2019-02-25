package com.bell.weatherbroker;

import com.bell.weatherbroker.configuration.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletRegistration;

/**
 * Класс инициализации DispatcherServlet
 */
public class DispatcherInitializer implements WebApplicationInitializer {

    /**
     * Инициализациия DispatcherServlet.
     * Регистрация Spring AnnotationConfigWebApplicationContext
     * @param servletContext
     */
    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) {

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        context.refresh();

        ServletRegistration.Dynamic registration = servletContext.addServlet("DispatcherInitializer", new DispatcherServlet(context));
        registration.setLoadOnStartup(1);
        registration.addMapping("/*");
    }
}
